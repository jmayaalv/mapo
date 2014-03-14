package mapo.common;

import java.io.IOException;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public final class CommonUtils {

	public static String serializeSetToString(Set<? extends Object> list, char token) {
		if (list == null || list.isEmpty()) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		for (Object o : list) {
			if (sb.length() > 0) {
				sb.append(token);
			}
			sb.append(o);
		}
		return sb.toString();
	}

	/**
	 * Returns the index of an open operator in the concept. The open operator
	 * is the next operator to process.
	 * 
	 * @param concept
	 * @return
	 * @author jmayaalv
	 */
	public static int indexOpenOperator(String concept) {
		int index = -1;
		if (StringUtils.isBlank(concept)) {
			return index;
		}

		concept = StringUtils.trim(concept);
		short open = 0;
		char[] op = new char[3];
		String operator = null;
		for (int i = 0; i < concept.length(); i++) {
			if (concept.charAt(i) == '(') {
				open++;
			} else if (concept.charAt(i) == ')') {
				open--;
			} else if (concept.charAt(i) == '.' && open == 0) {
				op[0] = concept.charAt(i + 1);
				op[1] = concept.charAt(i + 2);
				op[2] = concept.charAt(i + 3);
				operator = String.valueOf(op);
				if ((StringUtils.trim(operator)).equalsIgnoreCase("AND")) {
					index = i;
					break;
				} else if ((StringUtils.trim(operator)).equalsIgnoreCase("OR")) {
					index = i;
					break;
				}
			}
		}
		return index;
	}

	public static String cleanAxiomString(String string) {
		return string.trim().replace("<", "").replace(">", "").trim();
	}

	/**
	 * Converts an object to an array of bytes .
	 * 
	 * @param object
	 *            the object to convert.
	 * @return the associated byte array.
	 * @throws IOException
	 */
	public static byte[] toBytes(Object object) throws IOException {
		java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
		java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(baos);
		oos.writeObject(object);
		return baos.toByteArray();
	}

	/**
	 * Converts an array of bytes back to its constituent object. The input
	 * array is assumed to have been created from the original object.
	 * 
	 * @param bytes
	 *            the byte array to convert.
	 * @return the associated object.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
		Object object = null;
		object = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(bytes)).readObject();
		return object;
	}
}
