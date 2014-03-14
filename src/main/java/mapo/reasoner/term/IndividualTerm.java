package mapo.reasoner.term;

public class IndividualTerm implements Term {

	private final String name;
	private final boolean temp;

	/**
	 * @param name
	 * @author jmayaalv
	 */
	public IndividualTerm(String name) {
		super();
		this.name = name;
		this.temp = false;
	}
	
	/**
	 * @param name
	 * @author jmayaalv
	 */
	public IndividualTerm(String name, boolean temp) {
		super();
		this.name = name;
		this.temp = temp;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the temp
	 */
	public boolean isTemp() {
		return temp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("[%s]", name);
	}
}
