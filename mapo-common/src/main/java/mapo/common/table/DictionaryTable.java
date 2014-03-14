/**
 * 
 */
package mapo.common.table;

/**
 * @author jmayaalv
 *
 */
public  class DictionaryTable implements Table{
	
	public static final String TABLE_NAME = "dictionary";

	@Override
	public Column[] getColumns() {
		return Columns.values();
	}

	@Override
	public Column[] getIndexes() {
		return null;
	}

	@Override
	public String getName() {
		return TABLE_NAME;
	}
	
	public static enum Columns implements Column{

		VALUE("data:value");

		private final String id;

		/**
		 * @param id
		 * @author jmayaalv
		 */
		private Columns(String id) {
			this.id = id;
		}

		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}
	}
}
