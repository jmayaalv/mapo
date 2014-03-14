/**
 * 
 */
package mapo.common.dao;

import java.util.List;

import mapo.common.PSeudoModel;

/**
 * @author jmayaalv
 *
 */
public interface PSeudoModelDao {
	
	/**
	 * Adds a pseduoModel for an individual
	 * @param individual
	 * @param pseudoModel
	 * @author jmayaalv
	 */
	public void add(int individual, PSeudoModel pseudoModel);
	
	
	/**
	 * Gets the PSeudoModel associated with an individual
	 * @param individual
	 * @return
	 * @author jmayaalv
	 */
	public List<PSeudoModel> getPseudoModels(int individual);

}
