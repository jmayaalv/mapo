/**
 * 
 */
package mapo.common.writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import mapo.common.concept.Concept;

import org.apache.hadoop.io.WritableComparable;

/**
 * @author jmayaalv
 *
 */
public class TBoxAxiom implements WritableComparable<TBoxAxiom> {
	
	private Concept concept;
	private boolean isEquivalent; //if false is subsuption
	
	/**
	 * @return the concept
	 */
	public Concept getConcept() {
		return concept;
	}

	/**
	 * @param concept the concept to set
	 */
	public void setConcept(Concept concept) {
		this.concept = concept;
	}

	/**
	 * @return the isEquivalent
	 */
	public boolean isEquivalent() {
		return isEquivalent;
	}

	/**
	 * @param isEquivalent the isEquivalent to set
	 */
	public void setEquivalent(boolean isEquivalent) {
		this.isEquivalent = isEquivalent;
	}

	@Override
	public void readFields(DataInput input) throws IOException {
		throw new RuntimeException("use gets");
	}

	@Override
	public void write(DataOutput output) throws IOException {
		throw new RuntimeException("use sets");
	}

	@Override
	public int compareTo(TBoxAxiom o) {
		return 0;
	}

}
