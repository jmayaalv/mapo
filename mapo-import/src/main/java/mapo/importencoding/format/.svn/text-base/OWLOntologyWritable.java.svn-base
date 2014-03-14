/**
 * 
 */
package mapo.importencoding.format;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author jmayaalv
 *
 */
public class OWLOntologyWritable implements WritableComparable<OWLOntologyWritable> {

	private OWLOntology owlOntology;
	
	/**
	 * @return the owlOntology
	 */
	public OWLOntology getOwlOntology() {
		return owlOntology;
	}

	/**
	 * @param owlOntology the owlOntology to set
	 */
	public void setOwlOntology(OWLOntology owlOntology) {
		this.owlOntology = owlOntology;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		throw new RuntimeException("use get");
	}

	@Override
	public void write(DataOutput out) throws IOException {
		throw new RuntimeException("use set");
	}

	@Override
	public int compareTo(OWLOntologyWritable o) {
		return 0;
	}
	
	

}
