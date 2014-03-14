package mapo.reasoner.term;

public class NegationTerm implements Term{
	
	private final String name;

	/**
	 * @param name
	 * @author jmayaalv
	 */
	public NegationTerm(String name) {
		super();
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("no(%s)", this.name);
	}
	

}
