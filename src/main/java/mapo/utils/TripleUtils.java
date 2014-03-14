/**
 * 
 */
package mapo.utils;

import org.apache.commons.lang.StringUtils;

/**
 * @author jmayaalv
 *
 */
public final class TripleUtils {

	private TripleUtils(){}
	
	public static boolean isVariable(String val){
		return (StringUtils.isNotBlank(val) && val.trim().startsWith("?"));
	}
	
}
