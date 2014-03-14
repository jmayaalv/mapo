/**
 * 
 */
package mapo.reasoner.persistence;

/**
 * @author jmayaalv
 *
 */
public class Individual {
	
	private final String name;
	private final boolean temp;
	
	/**
	 * @param name
	 * @param temp
	 * @author jmayaalv
	 */
	public Individual(String name, boolean temp) {
		super();
		this.name = name;
		this.temp = temp;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the temp
	 */
	public boolean isTemp() {
		return temp;
	}
}
