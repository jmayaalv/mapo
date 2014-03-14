/**
 * 
 */
package mapo.common.concept;

/**
 * @author jmayaalv
 *
 */
public class Expression {
	
	private final SimpleConcept simpleConcept;
	private final Concept concept;
	private final boolean equivalent;
	
	/**
	 * @param simpleConcept
	 * @param concept
	 * @param equivalent
	 * @author jmayaalv
	 */
	public Expression(SimpleConcept simpleConcept, Concept concept, boolean equivalent) {
		super();
		this.simpleConcept = simpleConcept;
		this.concept = concept;
		this.equivalent = equivalent;
	}
	
	/**
	 * @param simpleConcept
	 * @param concept
	 * @param equivalent
	 * @author jmayaalv
	 */
	public Expression(SimpleConcept simpleConcept, Concept concept) {
		super();
		this.simpleConcept = simpleConcept;
		this.concept = concept;
		this.equivalent = false;
	}

	/**
	 * @return the simpleConcept
	 */
	public SimpleConcept getSimpleConcept() {
		return simpleConcept;
	}

	/**
	 * @return the concept
	 */
	public Concept getConcept() {
		return concept;
	}

	/**
	 * @return the equivalent
	 */
	public boolean isEquivalent() {
		return equivalent;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%s %s %s", this.simpleConcept.toString(), (this.equivalent)?"=":"<", this.concept.toString());
	}
	
	

}
