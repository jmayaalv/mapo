/**
 * 
 */
package mapo.common.table;


/**
 * @author jmayaalv
 * 
 */
public class ModelTable implements Table {

	public static final String TABLE_NAME = "model_table";

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

		MODEL("data:model");

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
