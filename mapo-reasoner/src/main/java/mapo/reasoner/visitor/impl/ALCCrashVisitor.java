/**
 * 
 */
package mapo.reasoner.visitor.impl;

import java.util.List;

import mapo.common.dao.ABoxDao;
import mapo.reasoner.Node;
import mapo.reasoner.visitor.Visitor;

/**
 * 
 * @author jmayaalv
 * 
 */
public class ALCCrashVisitor implements Visitor<Node> {
	
	/**
	 * @param aBoxDao
	 * @param individual
	 * @author jmayaalv
	 */
	public ALCCrashVisitor() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mapo.common.reasoner.visitor.Visitor#visit(mapo.common.reasoner.visitor
	 * .Visitable)
	 */
	@Override
	public void visit(Node node) {

		if (Node.Status.UNEXPANDED.equals(node.getStatus())) {
			throw new RuntimeException("Model contains unexpanded nodes");
		}

		List<Node> children = node.getAllChildren();
		if (children != null && !children.isEmpty()) {
			for (Node child : children) {
				child.accept(this);
			}
		} else {
			//check for clashes
			if (node.isClashed()) {
				node.setStatus(Node.Status.UNSATISFIABLE);
			} else {
				node.setStatus(Node.Status.SATISFIABLE);
			}
		}

		Node parentNode = node.getParent();
		if (parentNode == null){
			return;
		}
		
		if (Node.Status.SATISFIABLE.equals(node.getStatus())) {
			if (parentNode.isOrNode()) { //or node
				parentNode.setStatus(Node.Status.SATISFIABLE);
			} else { //and node
				if (Node.Status.EXPANDED.equals(parentNode.getStatus())) {
					parentNode.setStatus(Node.Status.SATISFIABLE);
				}
			}
		} else if (Node.Status.UNSATISFIABLE.equals(node.getStatus())) {
			if (parentNode.isOrNode()) { //or node
				if (Node.Status.EXPANDED.equals(parentNode.getStatus())) {
					parentNode.setStatus(Node.Status.UNSATISFIABLE);
				}
			} else { //and node
				if (Node.Status.EXPANDED.equals(parentNode.getStatus()) || Node.Status.SATISFIABLE.equals(parentNode.getStatus())) {
					parentNode.setStatus(Node.Status.UNSATISFIABLE);
				}
			}

		}
	}
}
