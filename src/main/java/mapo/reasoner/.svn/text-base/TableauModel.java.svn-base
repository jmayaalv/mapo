/**
 * 
 */
package mapo.reasoner;

import java.util.ArrayList;
import java.util.List;

import mapo.reasoner.term.IndividualTerm;
import mapo.reasoner.term.Term;

/**
 * Represents a Tableu Model containing a list of trees. A model consist of a
 * list of trees
 * 
 * @author jmayaalv
 * 
 */
public class TableauModel {

	private List<TableauTree> trees;

	/**
	 * @param tree
	 * @author jmayaalv
	 */
	public TableauModel(TableauTree tree) {
		super();
		trees = new ArrayList<TableauTree>();
		tree.setModel(this);
		this.trees.add(tree);
	}

	public TableauModel(IndividualTerm key, Term concept) {
		this(new TableauTree(key, concept));
	}

	/**
	 * @return the tree
	 */
	public List<TableauTree> getTrees() {
		return trees;
	}

	/**
	 * @param tree
	 *            the tree to set
	 */
	public void setTree(List<TableauTree> trees) {
		this.trees = trees;
	}
	
	public void addTree(TableauTree tree){
		if (this.trees == null){
			this.trees = new ArrayList<TableauTree>();
		}
		tree.setModel(this);
		this.trees.add(tree);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder("Model:");
		for (TableauTree tree: trees){
			b.append("\n").append(" tree: " + tree);
		}
		return b.toString();
	}
	
	
}
