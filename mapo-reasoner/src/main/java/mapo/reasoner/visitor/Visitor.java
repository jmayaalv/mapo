/**
 * 
 */
package mapo.reasoner.visitor;

/**
 * @author jmayaalv
 *
 */
public interface Visitor<E extends Visitable> {
	
	/**
	 * @param node
	 * @author jmayaalv
	 */
	public void visit(E visitable);

}
