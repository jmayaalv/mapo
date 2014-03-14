/**
 * 
 */
package mapo.dom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jmayaalv
 * 
 */
public class Query {

	private List<String> vars;
	private List<Triple<String>> triples;

	public Query() {
	}

	/**
	 * @return the vars
	 */
	public List<String> getVars() {
		return vars;
	}

	/**
	 * @param vars
	 *            the vars to set
	 */
	public void setVars(List<String> vars) {
		this.vars = vars;
	}

	public void addVar(String var) {
		if (this.vars == null) {
			this.vars = new ArrayList<String>();
		}
		this.vars.add(var);
	}

	/**
	 * @return the triples
	 */
	public List<Triple<String>> getTriples() {
		return triples;
	}

	/**
	 * @param triples
	 *            the triples to set
	 */
	public void setTriples(List<Triple<String>> triples) {
		this.triples = triples;
	}

	/**
	 * @param triple
	 * @author jmayaalv
	 */
	public void addTriple(Triple<String> triple) {
		if (this.triples == null) {
			this.triples = new ArrayList<Triple<String>>();
		}
		this.triples.add(triple);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("Vars: [");
		for (String v : vars) {
			if (builder.length() > 7) {
				builder.append(",");
			}
			builder.append(v);
		}
		builder.append("]");
		if (this.triples != null) {
			builder.append(System.getProperty("line.separator"));
			for (Triple<String> t : this.triples) {
				builder.append(System.getProperty("line.separator"));
				builder.append(t);
			}
		}
		return builder.toString();
	}

}
