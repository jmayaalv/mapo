/**
 * 
 */
package mapo.reasoner.persistence;


/**
 * @author jmayaalv
 *
 */
public class Role {
	
	private final Edge edge;
	private final String role;
	
	public Role(Edge edge, String role){
		this.edge = edge;
		this.role = role;
	}

	/**
	 * @return the edge
	 */
	public Edge getEdge() {
		return edge;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	
}
