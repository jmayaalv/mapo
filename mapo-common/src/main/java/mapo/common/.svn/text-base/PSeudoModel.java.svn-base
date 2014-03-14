/**
 * 
 */
package mapo.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mapo.common.concept.SimpleConcept;

/**
 * @author jmayaalv
 * 
 */
public class PSeudoModel {

	private Map<SimpleConcept, Boolean> a;
	private Map<SimpleConcept, Boolean> noA;
	private Map<Integer, Boolean> valueRestriction;
	private Map<Integer, Boolean> universalRestriction;

	/**
	 * @return the a
	 */
	public Set<SimpleConcept> getA() {
		if (a == null) {
			return null;
		}
		return a.keySet();
	}

	/**
	 * @param a
	 *            the a to set
	 */
	public void setA(Map<SimpleConcept, Boolean> a) {
		this.a = a;
	}

	public void addA(SimpleConcept a) {
		if (this.a == null) {
			this.a = new HashMap<SimpleConcept, Boolean>();
		}
		this.a.put(a, Boolean.TRUE);
	}

	/**
	 * @return the noA
	 */
	public Set<SimpleConcept> getNoA() {
		if (this.noA == null) {
			return null;
		}
		return noA.keySet();
	}

	/**
	 * @param noA
	 *            the noA to set
	 */
	public void setNoA(Map<SimpleConcept, Boolean> noA) {
		this.noA = noA;
	}

	public void addNoA(SimpleConcept noA) {
		if (this.noA == null) {
			this.noA = new HashMap<SimpleConcept, Boolean>();
		}
		this.noA.put(noA, Boolean.TRUE);
	}

	/**
	 * @return the valueRestriction
	 */
	public Set<Integer> getValueRestriction() {
		if (this.valueRestriction == null) {
			return null;
		}
		return valueRestriction.keySet();
	}

	/**
	 * @param valueRestriction
	 *            the valueRestriction to set
	 */
	public void setValueRestriction(Map<Integer, Boolean> valueRestriction) {
		this.valueRestriction = valueRestriction;
	}

	public void addValueRestriction(int restriction) {
		if (this.valueRestriction == null) {
			this.valueRestriction = new HashMap<Integer, Boolean>();
		}
		this.valueRestriction.put(restriction, Boolean.TRUE);
	}

	/**
	 * @return the universalRestriction
	 */
	public Set<Integer> getUniversalRestriction() {
		if (this.universalRestriction == null) {
			return null;
		}
		return universalRestriction.keySet();
	}

	/**
	 * @param universalRestriction
	 *            the universalRestriction to set
	 */
	public void setUniversalRestriction(Map<Integer, Boolean> universalRestriction) {
		this.universalRestriction = universalRestriction;
	}

	public void addUniversalRestriction(int universalRestriction) {
		if (this.universalRestriction == null) {
			this.universalRestriction = new HashMap<Integer, Boolean>();
		}
		this.universalRestriction.put(universalRestriction, Boolean.TRUE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder a = new StringBuilder();
		StringBuilder noA = new StringBuilder();
		StringBuilder valueRestriction = new StringBuilder();
		StringBuilder universalRestriction = new StringBuilder();

		if (this.a != null) {
			for (SimpleConcept sc : getA()) {
				if (a.length() > 0) {
					a.append(',');
				}
				a.append(sc.getConcept());
			}
		} else {
			a.append(0);
		}

		if (this.noA != null) {
			for (SimpleConcept sc : getNoA()) {
				if (noA.length() > 0) {
					noA.append(',');
				}
				noA.append(sc.getConcept());
			}
		} else {
			noA.append(0);
		}

		if (this.valueRestriction != null) {
			for (long rc : getValueRestriction()) {
				if (valueRestriction.length() > 0) {
					valueRestriction.append(',');
				}
				valueRestriction.append(rc);
			}
		} else {
			valueRestriction.append(0);
		}

		if (this.universalRestriction != null) {
			for (long rc : getUniversalRestriction()) {
				if (universalRestriction.length() > 0) {
					universalRestriction.append(',');
				}
				universalRestriction.append(rc);
			}
		} else {
			universalRestriction.append(0);
		}

		return String.format("<%s>;<%s>;<%s>;<%s>", a.toString(), noA.toString(), valueRestriction.toString(), universalRestriction.toString());

	}

	public static PSeudoModel parse(String pseudoModelString) {
		if (pseudoModelString == null) {
			return null;
		}

		String[] pModels = pseudoModelString.split(";");
		if (pModels == null || pModels.length != 4) {
			throw new RuntimeException("Invalid pseudomodel string representation.");
		}

		PSeudoModel pseudoModel = new PSeudoModel();

		// parse a 
		String[] values = pModels[0].split(",");
		for (String value : values) {
			pseudoModel.addA(SimpleConcept.parse(value));
		}

		// parse noA 
		values = pModels[1].split(",");
		for (String value : values) {
			pseudoModel.addNoA(SimpleConcept.parse(value));
		}

		// parse value restriction
		values = pModels[2].split(",");
		for (String value : values) {
			pseudoModel.addValueRestriction(Integer.parseInt(value.trim()));
		}

		// parse universal restriction
		values = pModels[3].split(",");
		for (String value : values) {
			pseudoModel.addUniversalRestriction(Integer.parseInt(value.trim()));
		}

		return pseudoModel;
	}

	/**
	 * Executes the mergeable test
	 * 
	 * @param other
	 * @return
	 * @author jmayaalv
	 */
	public boolean isMergeable(PSeudoModel other) {
		if (this.a != null && !this.a.isEmpty() && this.noA != null && !this.noA.isEmpty()) {
			for (SimpleConcept sc : this.a.keySet()) {
				if (this.noA.containsKey(sc)) {
					return true; //merge found between a and no-a
				}
			}
		}

		if (this.valueRestriction != null && !this.valueRestriction.isEmpty() && this.universalRestriction != null && !this.universalRestriction.isEmpty()) {
			for (int role : this.valueRestriction.keySet()) {
				if (this.universalRestriction.containsKey(role)) {
					return true; //merge found between universal and valueRestriction
				}
			}
		}

		return false;
	}
}
