/**
 * 
 */
package mapo.parser.query;

import mapo.dom.Query;

/**
 * @author jmayaalv
 *
 */
public interface QueryParser {
	
	/**
	 * Parses a query given a string
	 * @param queryString
	 * @return
	 * @author jmayaalv
	 */
	public Query parse(String queryString);

}
