/**
 * 
 */
package mapo.common.table;

/**
 * @author jmayaalv
 * 
 */
public class IndividualPseudoModelTable implements Table {

	public static final String TABLE_NAME = "individual_pseudo_model";

	@Override
	public Column[] getColumns() {
		return Columns.values();
	}

	@Override
	public Column[] getIndexes() {
		return Indexes.values();
	}

	@Override
	public String getName() {
		return TABLE_NAME;
	}

	public static enum Columns implements Column {

		A("data:a"), NOT_A("data:not-a"), EXISTENCE_A("data:existence"), UNIVERSAL_A("data:universal");

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

	public static enum Indexes implements Column {

		;

		private final String id;

		/**
		 * @param id
		 * @author jmayaalv
		 */
		private Indexes(String id) {
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
