/**
 * 
 */
package mapo.dom.writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import mapo.dom.Triple;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.WritableComparable;

/**
 * @author jmayaalv
 * 
 */
public class EncodedTripleWritable implements WritableComparable<EncodedTripleWritable> {

	private LongWritable subject;
	private LongWritable predicate;
	private LongWritable object;
	
	private Triple<Long> triple;

	/**
	 * 
	 * @author jmayaalv
	 */
	public EncodedTripleWritable() {
		super();
		
		this.subject = new LongWritable();
		this.predicate = new LongWritable();
		this.object = new LongWritable();
		
	}

	/**
	 * @param triple
	 * @author jmayaalv
	 */
	public EncodedTripleWritable(Triple<Long> triple) {
		super();
		this.triple = triple;
		set(triple.getSubject(), triple.getPredicate(), triple.getObject());
	}

	public EncodedTripleWritable(long subject, long predicate, long object) {
		super();
		this.triple = new Triple<Long>(subject, predicate, object);
		set(subject, predicate, object);
	}
	
	private void set(long subject, long predicate, long object){
		this.subject = new LongWritable(subject);
		this.predicate = new LongWritable(predicate);
		this.object = new LongWritable(object);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.hadoop.io.Writable#readFields(java.io.DataInput)
	 */
	@Override
	public void readFields(DataInput in) throws IOException {
		this.subject.readFields(in);
		this.subject.readFields(in);
		this.object.readFields(in);
		
		this.triple = new Triple<Long>(this.subject.get(), this.predicate.get(), this.object.get());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.hadoop.io.Writable#write(java.io.DataOutput)
	 */
	@Override
	public void write(DataOutput out) throws IOException {
		this.subject.write(out);
		this.subject.write(out);
		this.object.write(out);
	}
	
	/**
	 * @return
	 * @author jmayaalv
	 */
	public Triple<Long> getTriple() {
		return this.triple;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(EncodedTripleWritable o) {
		
		if (o.triple == null){
			return 1;
		}
		
		int compare = this.triple.getSubject().compareTo(o.triple.getSubject());
		if (compare == 0) {
			compare = this.triple.getPredicate().compareTo(o.triple.getPredicate());
		}
		if (compare == 0) {
			compare = this.triple.getObject().compareTo(o.triple.getObject());
		}
		return compare;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((triple == null) ? 0 : triple.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		EncodedTripleWritable other = (EncodedTripleWritable) obj;
		if (triple == null) {
			if (other.triple != null) {
				return false;
			}
		} else if (!triple.equals(other.triple)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TripleWritable [triple=" + triple + "]";
	}

}
