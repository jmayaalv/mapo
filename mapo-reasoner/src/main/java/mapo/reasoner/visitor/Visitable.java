/**
 * 
 */
package mapo.reasoner.visitor;

/**
 * @author jmayaalv
 *
 */
public interface Visitable {
	
	public void accept(Visitor<? extends Visitable> visitor);

}
