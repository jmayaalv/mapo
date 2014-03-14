/**
 * 
 */
package mapo.reasoner.persistence;

/**
 * @author jmayaalv
 *
 */
public class Edge {
	
	private final Individual lhs;
	private final Individual rhs;
	
	/**
	 * @param lhs
	 * @param rhs
	 * @author jmayaalv
	 */
	public Edge(Individual lhs, Individual rhs) {
		super();
		this.lhs = lhs;
		this.rhs = rhs;
	}

	/**
	 * @return the lhs
	 */
	public Individual getLhs() {
		return lhs;
	}

	/**
	 * @return the rhs
	 */
	public Individual getRhs() {
		return rhs;
	}
}
