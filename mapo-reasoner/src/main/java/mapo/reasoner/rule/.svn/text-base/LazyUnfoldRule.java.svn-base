/**
 * 
 */
package mapo.reasoner.rule;

import java.util.ArrayList;
import java.util.List;

import mapo.common.concept.Concept;
import mapo.common.concept.SimpleConcept;
import mapo.common.dao.TBoxDao;
import mapo.reasoner.Node;

/**
 * @author jmayaalv
 * 
 */
public class LazyUnfoldRule extends AbstractRule<SimpleConcept> {

	private final TBoxDao tBoxDao;

	/**
	 * @param tBoxDao
	 * @author jmayaalv
	 */
	public LazyUnfoldRule(TBoxDao tBoxDao) {
		super();
		this.tBoxDao = tBoxDao;
	}

	/**
	 * Obtains the value of the unfolded concept from the tbox and adds the
	 * result to the label
	 * 
	 * @param node
	 * @parm sc
	 */
	@Override
	protected void applyRule(Node node, SimpleConcept sc) {

		List<Concept> concepts = this.tBoxDao.get(sc.getConcept(), true);
		List<Concept> subsConcepts = this.tBoxDao.get(sc.getConcept(), false);

		if (concepts == null) {
			concepts = new ArrayList<Concept>();
		}

		if (subsConcepts != null && !subsConcepts.isEmpty()) {
			concepts.addAll(subsConcepts);
		}

		for (Concept concept : subsConcepts) {
			if (sc.isNegate()) {
				node.addContent(concept.negate());
			} else {
				node.addContent(concept);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mapo.reasoner.rule.AbstractRule#isApplicable(mapo.common.concept.Concept)
	 */
	@Override
	protected boolean isApplicable(Concept concept) {
		if (concept instanceof SimpleConcept) {
			if (((SimpleConcept) concept).isUnfolded()) {
				return true;
			}
		}
		return false;
	}
}
