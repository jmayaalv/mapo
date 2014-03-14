package mapo.reasoner;


public interface Visitor {
	
	/**
	 * Visits a tableu tree applying tableaux completion rules.
	 * The visitor can modify the current tree, create new children
	 * It's responsible for changing the status of the tree
	 * @param tree
	 * @return
	 * @author jmayaalv
	 */
	public boolean visit(TableauTree tree);

}
