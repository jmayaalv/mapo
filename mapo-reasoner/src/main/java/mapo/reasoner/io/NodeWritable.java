/**
 * 
 */
package mapo.reasoner.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import mapo.reasoner.Node;

import org.apache.hadoop.io.WritableComparable;

/**
 * @author jmayaalv
 *
 */
public class NodeWritable implements WritableComparable<Node> {
	
	private final Node node;
	
	/**
	 * @param node
	 * @author jmayaalv
	 */
	public NodeWritable(Node node) {
		super();
		this.node = node;
	}

	/**
	 * @return the node
	 */
	public Node getNode() {
		return node;
	}

	@Override
	public void readFields(DataInput dataInput) throws IOException {
		
	}

	@Override
	public void write(DataOutput dataOutput) throws IOException {
		
	}

	@Override
	public int compareTo(Node o) {
		return 0;
	}

}
