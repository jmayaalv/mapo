/**
 * 
 */
package mapo.reasoner.rule;

import mapo.common.concept.Concept;
import mapo.common.concept.RestrictionConcept;
import mapo.reasoner.Node;

/**
 * @author jmayaalv
 *
 */
public class ValueRestrictionRule extends AbstractRule<RestrictionConcept> {

	/**
	 * Removes the restrictionConcept from the node and creates a new child for the role
	 * @param node
	 * @param restrictionConcept
	 */
	@Override
	protected void applyRule(Node node, RestrictionConcept restrictionConcept) {
		node.removeContent(restrictionConcept);
		node.addChild(restrictionConcept.getRole(), restrictionConcept.getConcept());
	}

	/* (non-Javadoc)
	 * @see mapo.common.reasoner.rule.AbstractRule#isApplicable(mapo.common.concept.Concept)
	 */
	@Override
	protected boolean isApplicable(Concept concept) {
		if (concept instanceof RestrictionConcept) {
			RestrictionConcept rc = (RestrictionConcept)concept;
			if (RestrictionConcept.Restriction.VALUE_RESTRICTION.equals(rc.getRestriction())){
				return true;
			}
		}
		return false;
	}

}
