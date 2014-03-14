/**
 * 
 */
package mapo.common.table;


/**
 * @author jmayaalv
 * 
 */
public class ABoxConceptAssertionTable implements Table {

	public static final String TABLE_NAME = "abox_concept_assertion";

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

	public static enum Columns implements Column {

		CONCEPT("data:concept");

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
