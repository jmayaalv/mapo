/**
 * 
 */
package mapo.reasoner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mapo.reasoner.term.IndividualTerm;
import mapo.reasoner.term.Term;

/**
 * Represents a tableu tree used to execute the Tableaux Algorithm
 * 
 * @author jmayaalv
 * 
 */
public class TableauTree implements Visitable {

	private final IndividualTerm key; //node
	private TableauModel model;
	private List<Term> concepts; //concept expression that represent the label of the tree
	private Status status; //status of the tree
	private TableauTree parent;
	private Map<String, TableauTree> children; //children trees connected by a given role name
	@SuppressWarnings("unchecked")
	private Map<Class, Boolean> appliedRules; //contains the rules that have been completely applied to the tree node

	/**
	 * @param key
	 * @param concepts
	 * @param status
	 * @param children
	 * @author jmayaalv
	 */
	public TableauTree(IndividualTerm key, Term concept) {
		super();
		this.key = key;
		this.concepts = new ArrayList<Term>();
		this.concepts.add(concept);
		this.status = Status.PROCESSING;
	}

	/**
	 * @param key
	 * @param concepts
	 * @param status
	 * @param children
	 * @author jmayaalv
	 */
	public TableauTree(IndividualTerm key, List<Term> concepts) {
		super();
		this.key = key;
		this.concepts = concepts;
		this.status = Status.PROCESSING;
	}

	/**
	 * @return the key
	 */
	public IndividualTerm getKey() {
		return key;
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
	 * @return the concepts
	 */
	public List<Term> getConcepts() {
		return concepts;
	}

	/**
	 * @param concepts
	 *            the concepts to set
	 */
	public void setConcepts(List<Term> concepts) {
		this.concepts = concepts;
	}
	
	/**
	 * @return the parent
	 */
	public TableauTree getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(TableauTree parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public Map<String, TableauTree> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void setChildren(Map<String, TableauTree> children) {
		this.children = children;
	}

	/**
	 * Adds a children to the tree creating a new individual
	 * 
	 * @param role
	 * @param child
	 * @author jmayaalv
	 */
	public void addChildren(String role, TableauTree child) {
		if (this.children == null) {
			this.children = new HashMap<String, TableauTree>();
		}

		child.setParent(this);
		this.children.put(role, child);
	}

	/**
	 * @return the model
	 */
	public TableauModel getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(TableauModel model) {
		this.model = model;
	}

	public boolean isClashed() {
		return Status.CLASH.equals(this.status);
	}

	public boolean isExpanded() {
		return Status.CLASH.equals(this.status);
	}

	public boolean hasChildren() {
		return this.children != null && !this.children.isEmpty();
	}

	/**
	 * @return the appliedRules
	 */
	@SuppressWarnings("unchecked")
	public Map<Class, Boolean> getAppliedRules() {
		return appliedRules;
	}

	@SuppressWarnings("unchecked")
	public void addAppliedRule(Class<? extends Rule> clazz) {
		if (this.appliedRules == null) {
			this.appliedRules = new HashMap<Class, Boolean>();
		}
		this.appliedRules.put(clazz, true);
	}

	public boolean isRuleApplied(Class<? extends Rule> clazz) {
		if (this.appliedRules == null) {
			return false;
		}
		return this.appliedRules.containsKey(clazz);
	}

	public int getTotalChildren() {
		if (this.children == null) {
			return 0;
		}
		return this.children.size();
	}

	public static enum Status {
		PROCESSING, EXPANDED, CLASH
	}

	@Override
	public boolean accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder(String.format("(%s)", this.getKey()));
		for (Term concept : this.concepts) {
			if (b.length() > 0) {
				b.append(";");
			}
			b.append(concept.toString());
		}

		if (this.children != null) {
			for (Map.Entry<String, TableauTree> child : this.children.entrySet()) {
				b.append(child.getKey()).append("-> ").append(child.getValue().toString());
			}
		}
		return b.toString();
	}

}
