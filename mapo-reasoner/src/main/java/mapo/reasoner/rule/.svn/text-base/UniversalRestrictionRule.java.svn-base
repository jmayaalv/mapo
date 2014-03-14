/**
 * 
 */
package mapo.reasoner.rule;

import java.util.Collection;
import java.util.Map;

import mapo.common.concept.Concept;
import mapo.common.concept.RestrictionConcept;
import mapo.reasoner.Node;

import com.google.common.collect.Multimap;

/**
 * @author jmayaalv
 * 
 */
public class UniversalRestrictionRule extends AbstractRule<RestrictionConcept> {

	/**
	 * Removes the restrictionConcept from the node and creates a new child for
	 * the role
	 * 
	 * @param node
	 * @param restrictionConcept
	 */
	@Override
	protected void applyRule(Node node, RestrictionConcept restrictionConcept) {
		node.removeContent(restrictionConcept);

		//adds the concept to all the children nodes associated with the role
		Multimap<Integer, Map<String, Node>> children = node.getChildren();
		if (children != null) {
			Collection<Map<String, Node>> childrenSet = children.get(restrictionConcept.getRole());
			if (childrenSet != null && !childrenSet.isEmpty()) {
				for (Map<String, Node> childMap : childrenSet) {
					for (Node nodeEntry : childMap.values()){
						nodeEntry.addContent(restrictionConcept.getConcept());
					}
				}
			} else {
				//if no children for the role create a new one
				node.addChild(restrictionConcept.getRole(), restrictionConcept.getConcept());
			}
		} else {
			//if no children at all create a new one
			node.addChild(restrictionConcept.getRole(), restrictionConcept.getConcept());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mapo.common.reasoner.rule.AbstractRule#isApplicable(mapo.common.concept
	 * .Concept)
	 */
	@Override
	protected boolean isApplicable(Concept concept) {
		if (concept instanceof RestrictionConcept) {
			RestrictionConcept rc = (RestrictionConcept) concept;
			if (RestrictionConcept.Restriction.UNIVERSAL_RESTRICTION.equals(rc.getRestriction())) {
				return true;
			}
		}
		return false;
	}

}
