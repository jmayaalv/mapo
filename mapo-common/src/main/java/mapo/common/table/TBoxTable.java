/**
 * 
 */
package mapo.common.table;



/**
 * @author jmayaalv
 * 
 */
public class TBoxTable implements Table {

	public static final String TABLE_NAME = "tbox";

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

		LHS("data:lhs"), RHS("data:rhs"), EQUIVALENT("data:equivalent");

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
