/**
 * 
 */
package mapo.reasoner.rule;

import mapo.common.concept.Concept;
import mapo.reasoner.Node;

/**
 * @author jmayaalv
 *
 */
public abstract class AbstractRule<E extends Concept> implements Rule<E> {

	@SuppressWarnings("unchecked")
	public boolean apply(Node node) {
		if (!node.getStatus().equals(Node.Status.UNEXPANDED)){
			return false;
		}
		
		//gets the first concept that could be expanded for the node. If no concept to expand mark the concept as expanded
		Concept toExpand = node.getUnexpandedConcept();
		if (toExpand == null){
			
			//check for unexpanded nodes
			toExpand = node.getNextUnfoldedConcept();
			if (toExpand == null){
				node.setStatus(Node.Status.EXPANDED);
				return false;
			}
		}
		
		//check if concept can be applied
		if (!isApplicable(toExpand)){
			return false;
		}
		
		//finally the rule can be applier to a concept in the node!!! :)
		applyRule(node, (E)toExpand);
		return true;
	}
	
	protected abstract void applyRule(Node node, E e);

	protected abstract boolean isApplicable(Concept concept);

}
