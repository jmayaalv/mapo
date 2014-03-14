/**
 * 
 */
package mapo.common.concept;

import org.apache.commons.lang.StringUtils;

/**
 * @author jmayaalv
 *
 */
public class SimpleConcept implements Concept {
	
	private static final long serialVersionUID = -3405691563059158334L;
	
	private final Integer concept; //conceptid
	private final boolean negate;
	private boolean unfolded;
	
	/**
	 * @param concept
	 * @author jmayaalv
	 */
	public SimpleConcept(int concept) {
		super();
		this.concept = concept;
		this.negate = false;
	}
	
	public SimpleConcept(int concept, boolean negate) {
		super();
		this.concept = concept;
		this.negate = negate;
	}

	/**
	 * @return the concept
	 */
	public int getConcept() {
		return concept;
	}
	
	public SimpleConcept negate(){
		return new SimpleConcept(this.getConcept(), !this.isNegate());
	}
	
	/**
	 * @return the negate
	 */
	public boolean isNegate() {
		return negate;
	}
	
	/**
	 * @return the unfolded
	 */
	public boolean isUnfolded() {
		return unfolded;
	}

	/**
	 * @param unfolded the unfolded to set
	 */
	public void setUnfolded(boolean unfolded) {
		this.unfolded = unfolded;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (negate?".NO (":"") + concept + (negate?")":"");
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((concept == null) ? 0 : concept.hashCode());
		result = prime * result + (negate ? 1231 : 1237);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SimpleConcept other = (SimpleConcept) obj;
		if (concept == null) {
			if (other.concept != null) {
				return false;
			}
		} else if (!concept.equals(other.concept)) {
			return false;
		}
		if (negate != other.negate) {
			return false;
		}
		return true;
	}

	/**
	 * Parses a {@link SimpleConcept} from a String
	 * @param concept
	 * @return
	 * @author jmayaalv
	 */
	public static SimpleConcept parse(String concept){
		if (StringUtils.isBlank(concept)){
			return null;
		}

		concept = StringUtils.trim(concept);
		if ((StringUtils.countMatches(concept, "(") == (StringUtils.countMatches(concept, ")"))) && StringUtils.startsWith(concept, "(") && StringUtils.endsWith(concept, ")") ) {
			concept = StringUtils.substring(concept, 1, concept.length() - 1);
		}
		
		boolean negated = false;
		if (concept.startsWith(".NO")){
			negated = true;
			concept = StringUtils.remove(concept, ".NO");
			concept = StringUtils.trim(concept);
		}
		
		if (StringUtils.isBlank(concept)){
			throw new RuntimeException("Concept can't be parsed: " + concept);
		}
		
		if ((StringUtils.countMatches(concept, "(") == (StringUtils.countMatches(concept, ")"))) && StringUtils.startsWith(concept, "(") && StringUtils.endsWith(concept, ")") ) {
			concept = StringUtils.substring(concept, 1, concept.length() - 1);
		}
		
		if (StringUtils.contains(concept, " ")){
			return null; //not a SimpleConcep
		}
		
		return new SimpleConcept(Integer.parseInt(concept), negated);
	}
}
