package mapo.reasoner.rule;

import mapo.common.concept.Concept;
import mapo.reasoner.Node;

/**
 * @author jmayaalv
 *
 */
public interface Rule<E extends Concept> {
	
	/**
	 * Applies a {@link Rule} to a {@link Node}
	 * returns true, if rule is applied
	 * @param node
	 * @return
	 * @author jmayaalv
	 */
	public boolean apply(Node node);
	
}
