/**
 * 
 */
package mapo.reasoner.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mapo.common.PSeudoModel;
import mapo.common.concept.Concept;
import mapo.common.concept.RestrictionConcept;
import mapo.common.concept.SimpleConcept;
import mapo.common.concurrent.BoundedCompletionService;
import mapo.common.dao.ABoxDao;
import mapo.common.dao.TBoxDao;
import mapo.common.dao.hbase.ABoxDaoHBaseImpl;
import mapo.common.dao.hbase.TBoxDaoHBaseImpl;
import mapo.reasoner.Node;
import mapo.reasoner.rule.ConjunctionRule;
import mapo.reasoner.rule.DisjunctionRule;
import mapo.reasoner.rule.LazyUnfoldRule;
import mapo.reasoner.rule.Rule;
import mapo.reasoner.rule.UniversalRestrictionRule;
import mapo.reasoner.rule.ValueRestrictionRule;
import mapo.reasoner.service.Reasoner;
import mapo.reasoner.visitor.Visitor;
import mapo.reasoner.visitor.impl.ALCCrashVisitor;

import org.apache.hadoop.hbase.HBaseConfiguration;

/**
 * Contains the algorithms required to run the Tableau algorithm for the DL ALC language
 * @author jmayaalv
 * 
 */
public class ALCTableauReasoner implements Reasoner {

	private final static int MAX_THREADS_PER_EXECUTION = 5;
	private static final int DEFAULT_THREAD_POOL_SIZE = 50;
	private final ExecutorService pool = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
	private final ABoxDao aboxDao;
	private final TBoxDao tboxDao;
	
	private final static List<Rule<? extends Concept>> rules = new ArrayList<Rule<? extends Concept>>();
	private int activeThreads;

	public ALCTableauReasoner(){
		this.aboxDao = null;
		this.tboxDao = null;
		rules.add(new ConjunctionRule());
		rules.add(new DisjunctionRule());
		rules.add(new ValueRestrictionRule());
		rules.add(new UniversalRestrictionRule());
	}
	
	public ALCTableauReasoner(HBaseConfiguration hBaseConfiguration) {
		this.aboxDao = new ABoxDaoHBaseImpl(hBaseConfiguration);
		this.tboxDao = new TBoxDaoHBaseImpl(hBaseConfiguration);
		rules.add(new LazyUnfoldRule(tboxDao));
		rules.add(new ConjunctionRule());
		rules.add(new DisjunctionRule());
		rules.add(new ValueRestrictionRule());
		rules.add(new UniversalRestrictionRule());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mapo.common.reasoner.service.Reasoner#buildAbstractModel(mapo.common.
	 * concept.Concept)
	 */
	@Override
	public Node buildAbstractModel(Concept concept) {
		Node node = new Node(concept);
		expand(node);
		return node;
	}

	/**
	 * Expands a node using the tableau completion rules
	 * 
	 * @param node
	 * @author jmayaalv
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	private void expand(Node node) {

		//expand node concepts
		while (Node.Status.UNEXPANDED.equals(node.getStatus())) {
			for (Rule<? extends Concept> rule : rules) {
				rule.apply(node);
			}
		}

		//expand node children in a different thread from the pool
		List<Node> children = node.getAllChildren();
		if (children != null && !children.isEmpty()) {
			CompletionService<Boolean> completionService = new BoundedCompletionService<Boolean>(pool, MAX_THREADS_PER_EXECUTION);

			for (final Node childNode : children) {
				activeThreads++;
				completionService.submit(new java.util.concurrent.Callable<Boolean>() {
					public Boolean call() throws Exception {
						expand(childNode);
						return true;
					}
				});

				if (activeThreads <= MAX_THREADS_PER_EXECUTION) {
					try {
						completionService.take().get();
						activeThreads--;
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					//No thread avialable, user the current one
					expand(childNode);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mapo.reasoner.service.Reasoner#buildConceptPSeudoModels(mapo.reasoner
	 * .Node, mapo.common.concept.SimpleConcept)
	 */
	@Override
	public List<PSeudoModel> buildConceptPSeudoModels(Node node, SimpleConcept baseConcept) {
		
		if (node == null){
			return null;
		}

		List<PSeudoModel> pseudoModels = new ArrayList<PSeudoModel>();
		PSeudoModel pseudoModel = null;
		if (node.getOrChildren() != null && !node.getOrChildren().isEmpty()) {
			for (Node orChild : node.getOrChildren()) {
				pseudoModels.addAll(buildConceptPSeudoModels(orChild, baseConcept));
			}
		} else {
			pseudoModel = new PSeudoModel();
			if (baseConcept != null) {
				if (baseConcept.isNegate()) {
					pseudoModel.addNoA(baseConcept);
				} else {
					pseudoModel.addA(baseConcept);
				}
			}

			//extract root node labels
			for (Concept concept : node.getLabel()) {
				if (concept instanceof SimpleConcept) {
					SimpleConcept sc = (SimpleConcept) concept;
					if (sc.isNegate()) {
						//add to noA
						pseudoModel.addNoA(sc.negate());
					} else {
						//add to A
						pseudoModel.addA(sc);
					}
				} else if (concept instanceof RestrictionConcept) {
					RestrictionConcept rc = (RestrictionConcept) concept;
					if (RestrictionConcept.Restriction.VALUE_RESTRICTION.equals(rc.getRestriction())) {
						//add to valueRestriction
						pseudoModel.addValueRestriction(rc.getRole());
					} else {
						//add to universalRestriction
						pseudoModel.addUniversalRestriction(rc.getRole());
					}
				}
			}
			pseudoModels.add(pseudoModel);
		}

		return pseudoModels;
	}

	/* (non-Javadoc)
	 * @see mapo.reasoner.service.Reasoner#getIndividualPSeudoModels(int, mapo.reasoner.Node, java.util.List)
	 */
	@Override
	public List<PSeudoModel> getIndividualPSeudoModels(int individual, Node node, List<PSeudoModel> conceptPModel) {

		//Check pseudo models agains abox and build the individual pseudomodel
		List<PSeudoModel> individualPSeudoModels = new ArrayList<PSeudoModel>();

		PSeudoModel iPSeudoModel = null;
		Visitor<Node> visitor = new ALCCrashVisitor();

		for (PSeudoModel pseudoModel : conceptPModel) {
			//check if model is open
			node.accept(visitor);

			if (!Node.Status.SATISFIABLE.equals(node.getStatus())) {
				continue; //continue with next model
			}

			iPSeudoModel = new PSeudoModel();
			if (pseudoModel.getA() != null) {
				for (SimpleConcept sc : pseudoModel.getA()) {
					if (aboxDao.hasConceptAssertion(individual, sc)) {
						iPSeudoModel.addA(sc);
					}
				}
			}

			if (pseudoModel.getNoA() != null) {
				for (SimpleConcept sc : pseudoModel.getNoA()) {
					if (aboxDao.hasConceptAssertion(individual, sc)) {
						iPSeudoModel.addNoA(sc);
					}
				}
			}

			if (pseudoModel.getValueRestriction() != null) {
				for (int r : pseudoModel.getValueRestriction()) {
					if (aboxDao.hasRoleAssertion(individual, r)) {
						iPSeudoModel.addValueRestriction(r);
					}
				}
			}

			if (pseudoModel.getUniversalRestriction() != null) {
				for (int r : pseudoModel.getUniversalRestriction()) {
					if (aboxDao.hasRoleAssertion(individual, r)) {
						iPSeudoModel.addUniversalRestriction(r);
					}
				}
			}

			if (iPSeudoModel.getA() != null || iPSeudoModel.getNoA() != null || iPSeudoModel.getValueRestriction() != null
					|| iPSeudoModel.getUniversalRestriction() != null) {

				individualPSeudoModels.add(iPSeudoModel);
			}
		}
		return individualPSeudoModels;
	}

	@Override
	public List<PSeudoModel> getIndividualPSeudoModels(int individual, Concept concept) {
		//build the abstract tableaux model for the concept
		Node node = buildAbstractModel(concept);
		List<PSeudoModel> conceptPModel = buildConceptPSeudoModels(node, null);
		return getIndividualPSeudoModels(individual, node, conceptPModel);

	}
}
