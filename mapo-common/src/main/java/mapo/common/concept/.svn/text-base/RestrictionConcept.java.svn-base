package mapo.common.concept;

import org.apache.commons.lang.StringUtils;

public class RestrictionConcept implements Concept {

	private static final long serialVersionUID = 4459541366152411107L;
	
	private final Restriction restriction;
	private final int role;
	private final Concept concept;

	public RestrictionConcept(Restriction restriction, int role, Concept concept) {
		this.restriction = restriction;
		this.role = role;
		this.concept = concept;
	}

	/**
	 * @return the restriction
	 */
	public Restriction getRestriction() {
		return restriction;
	}

	/**
	 * @return the role
	 */
	public int getRole() {
		return role;
	}

	/**
	 * @return the concept
	 */
	public Concept getConcept() {
		return concept;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%s %s. (%s)", getRestriction().getId(), getRole(), getConcept());
	}

	public static enum Restriction {
		VALUE_RESTRICTION(".EXIST"), UNIVERSAL_RESTRICTION(".FORALL");

		private final String id;

		private Restriction(String id) {
			this.id = id;
		}

		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((concept == null) ? 0 : concept.hashCode());
		result = prime * result + ((restriction == null) ? 0 : restriction.hashCode());
		result = prime * result + (int) (role ^ (role >>> 32));
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
		RestrictionConcept other = (RestrictionConcept) obj;
		if (concept == null) {
			if (other.concept != null) {
				return false;
			}
		} else if (!concept.equals(other.concept)) {
			return false;
		}
		if (restriction == null) {
			if (other.restriction != null) {
				return false;
			}
		} else if (!restriction.equals(other.restriction)) {
			return false;
		}
		if (role != other.role) {
			return false;
		}
		return true;
	}

	public static RestrictionConcept parse(String concept) {
		if (StringUtils.isBlank(concept)) {
			return null;
		}

		concept = StringUtils.trim(concept);
		if ((StringUtils.countMatches(concept, "(") == (StringUtils.countMatches(concept, ")"))) && StringUtils.startsWith(concept, "(") && StringUtils.endsWith(concept, ")") ) {
			concept = StringUtils.substring(concept, 1, concept.length() - 1);
		}
		
		Restriction restriction = null;
		if (StringUtils.startsWith(concept, Restriction.VALUE_RESTRICTION.getId())) {
			restriction = Restriction.VALUE_RESTRICTION;
			concept = StringUtils.remove(concept, Restriction.VALUE_RESTRICTION.getId());
		} else if (StringUtils.startsWith(concept, Restriction.UNIVERSAL_RESTRICTION.getId())) {
			restriction = Restriction.UNIVERSAL_RESTRICTION;
			concept = StringUtils.remove(concept, Restriction.UNIVERSAL_RESTRICTION.getId());
		}else{
			return null;
		}
		concept = StringUtils.trim(concept);
		
		int role = Integer.parseInt(StringUtils.substring(concept, 0, StringUtils.indexOf(concept, ".")));
		concept = StringUtils.remove(concept, role + ".");
		concept = StringUtils.remove(concept, "(");
		concept = StringUtils.remove(concept, ")");
		
		//parse concept inside
		Concept cn = SimpleConcept.parse(concept);
		if (cn == null){
			cn = parse(concept);
		}
		
		if (cn == null){
			cn = ComplexConcept.parse(concept);
		}
		if (cn== null){
			throw new RuntimeException("Error parsing: "  +concept); 
		}
		
		return new RestrictionConcept(restriction, role, cn);

	}

	/* (non-Javadoc)
	 * @see mapo.common.concept.Concept#negate()
	 */
	@Override
	public Concept negate() {
		Restriction newRestriction = null; 
		if (Restriction.UNIVERSAL_RESTRICTION.equals(this.getRestriction())){
			newRestriction = Restriction.VALUE_RESTRICTION;
		}else if (Restriction.VALUE_RESTRICTION.equals(this.getRestriction())){
			newRestriction = Restriction.UNIVERSAL_RESTRICTION;
		}else{
			throw new RuntimeException("Invalid restriction.");
		}
		
		return new RestrictionConcept(newRestriction, getRole(), getConcept().negate());
	}

}
