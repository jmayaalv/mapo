/**
 * 
 */
package mapo.reasoner.term;

/**
 * @author jmayaalv
 *
 */
public class RoleTerm implements Term{
	
	private final Term term;
	private final String name;
	private final RoleTermOperation operation;
	
	/**
	 * @param term
	 * @param role
	 * @param operation
	 * @author jmayaalv
	 */
	public RoleTerm(Term term, String name, RoleTermOperation operation) {
		super();
		this.term = term;
		this.name = name;
		this.operation = operation;
	}

	/**
	 * @return the term
	 */
	public Term getTerm() {
		return term;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return name;
	}

	/**
	 * @return the operation
	 */
	public RoleTermOperation getOperation() {
		return operation;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%s%s.%s", this.operation.getName(), this.name, this.getTerm());
	}



	public static enum RoleTermOperation{
		
		EXISTENTIAL_RESTRICTION("E"), VALUE_RESTRICTION("V");
		
		private final String name;

		/**
		 * @param name
		 * @author jmayaalv
		 */
		private RoleTermOperation(String name) {
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
