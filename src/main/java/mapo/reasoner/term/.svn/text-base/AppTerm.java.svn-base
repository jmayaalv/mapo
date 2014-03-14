/**
 * 
 */
package mapo.reasoner.term;


/**
 * @author jmayaalv
 * 
 */
public class AppTerm implements Term {

	private Term lhs;
	private Term rhs;
	private TermOperation operation;
	
	/**
	 * @param lhs
	 * @param rhs
	 * @param operation
	 * @author jmayaalv
	 */
	public AppTerm(Term lhs, Term rhs, TermOperation operation) {
		super();
		this.lhs = lhs;
		this.rhs = rhs;
		this.operation = operation;
	}



	/**
	 * @return the lhs
	 */
	public Term getLhs() {
		return lhs;
	}



	/**
	 * @param lhs the lhs to set
	 */
	public void setLhs(Term lhs) {
		this.lhs = lhs;
	}



	/**
	 * @return the rhs
	 */
	public Term getRhs() {
		return rhs;
	}



	/**
	 * @param rhs the rhs to set
	 */
	public void setRhs(Term rhs) {
		this.rhs = rhs;
	}



	/**
	 * @return the operation
	 */
	public TermOperation getOperation() {
		return operation;
	}



	/**
	 * @param operation the operation to set
	 */
	public void setOperation(TermOperation operation) {
		this.operation = operation;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("(%s %s %s)",lhs, operation.getName(), rhs);
	}
	
	public enum TermOperation{
		
		DISJUNCTION("U"), CONJUCTION("n");
		
		private final String name;

		/**
		 * @param name
		 * @author jmayaalv
		 */
		private TermOperation(String name) {
			this.name = name;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
	}

}
