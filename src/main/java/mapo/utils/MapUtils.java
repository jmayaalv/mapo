/**
 * 
 */
package mapo.utils;

import java.util.Map;

/**
 * @author jmayaalv
 * 
 */
public final class MapUtils {

	private MapUtils() {
	}

	public static <T> T get(Map<? extends Object, T> map, Object key, T defaultValue) {
		T t = map.get(key);
		return t == null ? defaultValue : t;
	}

}
