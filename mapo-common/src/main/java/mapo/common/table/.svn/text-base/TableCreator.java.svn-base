package mapo.common.table;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.tableindexed.IndexSpecification;
import org.apache.hadoop.hbase.client.tableindexed.IndexedTableAdmin;
import org.apache.hadoop.hbase.util.Bytes;

public class TableCreator {

	public void createTables(String hBaseSitePath) throws IOException {
		HBaseConfiguration conf = new HBaseConfiguration();
		conf.addResource(new Path(hBaseSitePath));
	}

	public void createTables(HBaseConfiguration conf) throws IOException {
		//create dictionary table
		createTable(conf, new DictionaryTable());
		createTable(conf, new TBoxTable());
		createTable(conf, new ABoxConceptAssertionTable());
		createTable(conf, new IndividualTable());
	}

	/**
	 * Creates dictionary table and its indexes
	 * 
	 * @param conf
	 * @author jmayaalv
	 */
	private void createTable(HBaseConfiguration conf, Table table) throws IOException {
		String tableName = table.getName();

		HTableDescriptor desc = new HTableDescriptor(tableName);

		if (table.getColumns() != null) {
			for (Column column : table.getColumns()) {
				desc.addFamily(new HColumnDescriptor(column.getId()));
			}
		}

		if (table.getIndexes() == null || table.getIndexes().length == 0) {
			//create table without the indexes
			HBaseAdmin hbase = new HBaseAdmin(conf);
			if (!hbase.tableExists(tableName)) {
				hbase.createTable(desc); // crate the table if it doesnt' already exist
			}
			return;
		}else{
			IndexedTableAdmin admin = new IndexedTableAdmin(conf);
			if (admin.tableExists(Bytes.toBytes(tableName))) {
				return; //don't do anything if table exists
			}
			
			String indexName = null;
			String tableIndexName = null;
			for (Column index : table.getIndexes()) {
				desc.addFamily(new HColumnDescriptor(index.getId()));
				
				indexName = StringUtils.split(index.getId(), ':')[1]; //remove the column family from the name
				desc.addIndex(new IndexSpecification(indexName, Bytes.toBytes(index.getId())));
				tableIndexName = String.format("%s-%s", tableName, indexName);
				if (admin.tableExists(Bytes.toBytes(tableIndexName))) {
					if (admin.isTableEnabled(tableIndexName)) {
						admin.disableTable(Bytes.toBytes(tableIndexName));
					}
					admin.deleteTable(Bytes.toBytes(tableIndexName));
				}
			}

			admin.createTable(desc);
		}
	}

}
