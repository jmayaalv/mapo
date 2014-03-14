package mapo.reasoner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mapo.common.concept.ComplexConcept;
import mapo.common.concept.Concept;
import mapo.common.concept.RestrictionConcept;
import mapo.common.concept.SimpleConcept;
import mapo.reasoner.visitor.Visitable;
import mapo.reasoner.visitor.Visitor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class Node implements Visitable, Serializable {

	private static final long serialVersionUID = 8336445706040409288L;
	
	private String individual;
	private boolean orNode;
	private List<Concept> content;
	private List<Concept> label;
	private Status status;
	private Node parent;
	private Multimap<Integer, Map<String, Node>> children; //{role:{individual:node}}
	private List<Node> orChildren; //children created by the disjunction rule
	private int newIndividualCounter;
	private Boolean clashed;

	/**
	 * @param individual
	 * @param content
	 * @author jmayaalv
	 */
	public Node(Concept... content) {
		super();
		this.content = new ArrayList<Concept>();
		for (Concept c : content) {
			addContent(c);
		}
		this.status = Status.UNEXPANDED;
		this.individual = "n-" + this.newIndividualCounter++;
	}

	/**
	 * @param individual
	 * @param content
	 * @author jmayaalv
	 */
	public Node(String individual, Concept... content) {
		super();
		this.content = new ArrayList<Concept>();
		for (Concept c : content) {
			addContent(c);
		}
		this.individual = individual;
		this.status = Status.UNEXPANDED;
	}

	public Node(String individual, List<Concept> content) {
		super();
		this.content = new ArrayList<Concept>();
		for (Concept c : content) {
			addContent(c);
		}
		this.individual = individual;
		this.status = Status.UNEXPANDED;
	}

	/**
	 * @return the orNode
	 */
	public boolean isOrNode() {
		return orNode;
	}

	/**
	 * @param orNode
	 *            the orNode to set
	 */
	public void setOrNode(boolean orNode) {
		this.orNode = orNode;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the parent
	 */
	public Node getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(Node parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public Multimap<Integer, Map<String, Node>> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void setChildren(Multimap<Integer, Map<String, Node>> children) {
		this.children = children;
	}

	public void addChild(int role, Concept concept) {
		//obtain a new individual that doesn't exist in the tree
		addChild(role, role + "-" + this.newIndividualCounter, concept);
	}

	public void addChild(int role, String individual, Concept concept) {
		if (this.children == null) {
			this.children = new HashMultimap<Integer, Map<String, Node>>();
		}

		Collection<Map<String, Node>> roleChildren = this.children.get(role);

		if (roleChildren != null && !roleChildren.isEmpty()) {
			for (Map<String, Node> childrenMap : roleChildren) {
				Node child = childrenMap.get(individual);
				if (child == null) {
					child = new Node(individual, concept);
					child.setParent(this);
					childrenMap.put(individual, child);
				} else {
					child.addContent(concept);
				}
			}

		} else {
			Map<String, Node> childrenMap = new HashMap<String, Node>();
			Node node = new Node(individual, concept);
			node.setParent(this);
			childrenMap.put(individual, node);
			roleChildren.add(childrenMap);
			this.children.put(role, childrenMap);
		}

		//		this.children.put(arg0, arg1)
		this.newIndividualCounter++;
	}

	public void addChild(Node child) {
		if (this.orChildren == null) {
			this.orChildren = new ArrayList<Node>();
		}
		child.setParent(this);
		this.orChildren.add(child);
	}

	/**
	 * @return the content
	 */
	public List<Concept> getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(List<Concept> content) {
		this.content = content;
	}

	public List<Concept> getLabel() {
		return this.label;
	}

	public void addLabel(Concept concept) {
		if (this.label == null) {
			this.label = new ArrayList<Concept>();
		}
		this.label.add(concept);
	}

	public void addContent(Concept concept) {
		if (this.content == null) {
			this.content = new ArrayList<Concept>();
		}
		this.content.add(concept);
		this.setStatus(Status.UNEXPANDED);
		if (!(concept instanceof ComplexConcept)) {
			addLabel(concept);
		}
	}

	/**
	 * Removes a concept from the node. When a concept is removed, it's added to
	 * the node's label
	 * 
	 * @param concept
	 * @author jmayaalv
	 */
	public void removeContent(Concept concept) {
		if (this.content == null) {
			return;
		}

		this.content.remove(concept);		
	}

	/**
	 * @return the individual
	 */
	public String getIndividual() {
		return individual;
	}

	/**
	 * @return the newIndividualCounter
	 */
	public int getNewIndividualCounter() {
		return newIndividualCounter;
	}

	/**
	 * Gets the next unexpanded concept in the node.
	 * 
	 * @return
	 * @author jmayaalv
	 */
	public Concept getUnexpandedConcept() {
		if (!Status.UNEXPANDED.equals(this.status)) {
			return null;
		}

		if (this.content == null || this.content.isEmpty()) {
			return null;
		}

		Concept concept = null;
		for (Concept c : this.content) {
			if (c instanceof ComplexConcept) {
				concept = c;
				break;
			} else if (c instanceof RestrictionConcept) {
				concept = c;
				if (((RestrictionConcept) c).getRestriction().equals(RestrictionConcept.Restriction.VALUE_RESTRICTION)) {
					break;
				}
			}
		}
		return concept;
	}
	
	public Concept getNextUnfoldedConcept() {
		if (!Status.UNEXPANDED.equals(this.status)) {
			return null;
		}

		if (this.content == null || this.content.isEmpty()) {
			return null;
		}

		SimpleConcept concept = null;
		for (Concept c : this.content) {
			if (c instanceof SimpleConcept) {
				if (((SimpleConcept)c).isUnfolded()){
					concept = (SimpleConcept)c;
					break;
				}
			}
		}
		return concept;
	}

	public List<Concept> getExpandedConcepts() {
		if (this.content == null || this.content.isEmpty()) {
			return null;
		}
		List<Concept> expanded = new ArrayList<Concept>();
		for (Concept c : this.content) {
			if (c instanceof SimpleConcept) {
				expanded.add(c);
			}
		}
		return expanded;
	}
	
	public List<Node> getOrChildren(){
		return this.orChildren;
	}

	public List<Node> getAllChildren() {
		List<Node> children = new ArrayList<Node>();
		if (this.orChildren != null) {
			children.addAll(this.orChildren);
		}
		if (this.children != null) {
			for (Map<String, Node> nodeMap : this.children.values()) {
				children.addAll(nodeMap.values());
			}
		}
		return children;
	}

	/**
	 * Determines if there are clases with the
	 * 
	 * @return
	 * @author jmayaalv
	 */
	public boolean isClashed() {
		if (this.clashed == null) {
			this.clashed = false;
			Map<Integer, Boolean> conceptMap = new HashMap<Integer, Boolean>(); //{concept : negated}
			Boolean previousConceptValue = null;
			for (Concept c : getContent()) {
				SimpleConcept sc = (SimpleConcept) c;
				previousConceptValue = conceptMap.get(sc.getConcept());
				if (previousConceptValue != null) {
					//concept existed, check for clash
					if (previousConceptValue != sc.isNegate()) {
						this.clashed = true;
						break;
					}
				} else {
					//add concept
					conceptMap.put(sc.getConcept(), sc.isNegate());
				}
			}
		}
		return this.clashed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder concepts = new StringBuilder();
		if (this.label != null) {
			for (Concept concept : this.label) {
				if (concepts.length() > 0) {
					concepts.append(";");
				}
				concepts.append(concept);
			}
		}
		StringBuilder node = new StringBuilder(String.format("L(%s)={ %s }", this.individual, concepts.toString()));
		for (Node child : getAllChildren()) {
			node.append("\n").append("   ").append(child.toString());
		}
		return node.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public static enum Status {
		UNEXPANDED, EXPANDED, SATISFIABLE, UNSATISFIABLE;
	}

}
