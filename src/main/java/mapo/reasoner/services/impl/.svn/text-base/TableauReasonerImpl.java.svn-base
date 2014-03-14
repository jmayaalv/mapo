/**
 * 
 */
package mapo.reasoner.services.impl;

import java.util.ArrayList;
import java.util.List;

import mapo.reasoner.CompletionRulesVisitor;
import mapo.reasoner.TableauModel;
import mapo.reasoner.TableauTree;
import mapo.reasoner.Visitor;
import mapo.reasoner.services.Reasoner;
import mapo.reasoner.term.IndividualTerm;
import mapo.reasoner.term.Term;

/**
 * @author jmayaalv
 * 
 */
public class TableauReasonerImpl implements Reasoner {
	
	private final Visitor completionRuleVisitor;
	
	/**
	 * @param completionRuleVisitor
	 * @author jmayaalv
	 */
	public TableauReasonerImpl() {
		super();
		this.completionRuleVisitor = new CompletionRulesVisitor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mapo.services.Reasoner#isSatisfiable(aterm.ATermAppl, aterm.ATerm)
	 */
	@Override
	public boolean isSatisfiable(IndividualTerm individual, Term concept) {
		//apply rules to the axiom till no more rules can be applied
		// When no more rules can be applied check for clashes using individual Abox.
		TableauModel model = new TableauModel(individual, concept);
		boolean visit = model.getTrees().get(0).accept(this.completionRuleVisitor); 
		while (visit){
			List<TableauTree> copy = new ArrayList<TableauTree>(model.getTrees());
			for (TableauTree tree : copy){
				visit = tree.accept(this.completionRuleVisitor);
			}
		}
		System.out.println(model);
		return false;
	}

//	/**
//	 * Applies the completion rules
//	 * 
//	 * @param tree
//	 * @author jmayaalv
//	 */
//	private void applyRules(TableauModel model) {
//		List<TableauTree> resultTrees = null;
//		TableauTree discardTree = null;
//		boolean ruleApplied = false;
//		for (TableauTree tree : model.getTrees()) {
//			for (Rule rule : this.rules) {
//				if (rule.isApplicable(tree)) {
//					resultTrees = rule.apply(tree);
//					if (resultTrees != null) {
//						//new trees must be added to the model (disjunction rule), old trees are erased
//						discardTree = tree;
//						for (TableauTree newTree : resultTrees) {
//							model.addTree(newTree);
//						}
//					}
//					ruleApplied = true;
//				}
//
//			}
//		}
//
//		if (discardTree != null) {
//			model.remove(discardTree);
//		}
//
//		if (ruleApplied) {
//			//how to stop? when no rules to apply
//			applyRules(model);
//		}
//	}
}
