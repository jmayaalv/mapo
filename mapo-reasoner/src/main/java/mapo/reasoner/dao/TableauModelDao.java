package mapo.reasoner.dao;

import mapo.reasoner.Node;

public interface TableauModelDao {
	
	/**
	 * Persist a model
	 * @param expressionHash
	 * @param model
	 * @author jmayaalv
	 */
	public void add(int expressionHash, Node model);
	
	/**
	 * gets a model
	 * @param expressionHash
	 * @return
	 * @author jmayaalv
	 */
	public Node get(int expressionHash);
	
	/**
	 * Removes the model associated with a 
	 * @param expressionHash
	 * @author jmayaalv
	 */
	public void remove(int expressionHash);

}
