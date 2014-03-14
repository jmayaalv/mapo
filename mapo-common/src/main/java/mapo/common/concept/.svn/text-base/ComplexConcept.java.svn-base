package mapo.common.concept;

import mapo.common.CommonUtils;

import org.apache.commons.lang.StringUtils;

public class ComplexConcept implements Concept {

	private static final long serialVersionUID = 6432682057983682691L;
	
	private final Concept lhs;
	private final Concept rhs;
	private final Operator operator;

	/**
	 * @param lhs
	 * @param rhs
	 * @param operator
	 * @author jmayaalv
	 */
	public ComplexConcept(Concept lhs, Concept rhs, Operator operator) {
		super();
		this.lhs = lhs;
		this.rhs = rhs;
		this.operator = operator;
	}

	/**
	 * @return the lhs
	 */
	public Concept getLhs() {
		return lhs;
	}

	/**
	 * @return the rhs
	 */
	public Concept getRhs() {
		return rhs;
	}

	/**
	 * @return the operator
	 */
	public Operator getOperator() {
		return operator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("(%s %s %s)", getLhs(), getOperator().getId(), getRhs());
	}

	public static enum Operator {
		CONJUNCTION(".AND"), DISJUNCTION(".OR");

		private final String id;

		/**
		 * @param id
		 * @author jmayaalv
		 */
		private Operator(String id) {
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
		result = prime * result + ((lhs == null) ? 0 : lhs.hashCode());
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((rhs == null) ? 0 : rhs.hashCode());
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
		ComplexConcept other = (ComplexConcept) obj;
		if (lhs == null) {
			if (other.lhs != null) {
				return false;
			}
		} else if (!lhs.equals(other.lhs)) {
			return false;
		}
		if (operator == null) {
			if (other.operator != null) {
				return false;
			}
		} else if (!operator.equals(other.operator)) {
			return false;
		}
		if (rhs == null) {
			if (other.rhs != null) {
				return false;
			}
		} else if (!rhs.equals(other.rhs)) {
			return false;
		}
		return true;
	}

	/**
	 * Parse a complex concept.
	 * 
	 * @return
	 * @author jmayaalv
	 */
	public static ComplexConcept parse(String concept) {
		if (StringUtils.isBlank(concept)) {
			return null;
		}

		concept = StringUtils.trim(concept);
		if ((StringUtils.countMatches(concept, "(") == (StringUtils.countMatches(concept, ")"))) && StringUtils.startsWith(concept, "(") && StringUtils.endsWith(concept, ")") ) {
			concept = StringUtils.substring(concept, 1, concept.length() - 1);
		}

		int indexOfOp = CommonUtils.indexOpenOperator(concept);
		if (indexOfOp == -1){
			throw new RuntimeException("error parsing : " + concept);
		}
		Operator op = null;
		if (StringUtils.substring(concept, indexOfOp + 1).startsWith("OR")){
			op = Operator.DISJUNCTION;
		}else if (StringUtils.substring(concept, indexOfOp + 1).startsWith("AND")){
			op = Operator.CONJUNCTION;
		}
		
		String lhsString = StringUtils.substring(concept, 0, indexOfOp - 1);
		Concept lhs = SimpleConcept.parse(lhsString);
		if (lhs == null){
			lhs = RestrictionConcept.parse(lhsString);
			if (lhs == null){
				lhs = parse(lhsString);
			}
		}
		
		String rhsString = StringUtils.substring(concept,indexOfOp + 4);
		Concept rhs = SimpleConcept.parse(rhsString);
		if (rhs == null){
			rhs = RestrictionConcept.parse(rhsString);
			if (rhs == null){
				rhs = parse(rhsString);
			}
		}
		return new ComplexConcept(lhs, rhs, op);
	}

	/* (non-Javadoc)
	 * @see mapo.common.concept.Concept#negate()
	 */
	@Override
	public Concept negate() {
		Operator newOperator = null;
		if (Operator.CONJUNCTION.equals(getOperator())){
			newOperator = Operator.DISJUNCTION;
		}else if (Operator.DISJUNCTION.equals(getOperator())){
			newOperator = Operator.CONJUNCTION;
		}else{
			throw new RuntimeException("Invalid operator");
		}
		
		return new ComplexConcept(lhs.negate(), rhs.negate(), newOperator);
	}

}
