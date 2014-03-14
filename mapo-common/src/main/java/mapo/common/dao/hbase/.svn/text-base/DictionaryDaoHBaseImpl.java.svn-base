/**
 * 
 */
package mapo.common.dao.hbase;

import java.io.IOException;

import mapo.common.dao.DictionaryDao;
import mapo.common.table.DictionaryTable;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.io.BatchUpdate;
import org.apache.hadoop.hbase.io.Cell;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author jmayaalv
 *
 */
public class DictionaryDaoHBaseImpl implements DictionaryDao {

	private static Logger logger = LoggerFactory.getLogger(DictionaryDaoHBaseImpl.class);
	
	private HTable dictionaryTable;
	
	/**
	 * 
	 * @author jmayaalv
	 */
	public DictionaryDaoHBaseImpl(HBaseConfiguration hBaseConfiguration) {
		super();
		try {
			this.dictionaryTable = new HTable(hBaseConfiguration, DictionaryTable.TABLE_NAME);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
	}

	/* (non-Javadoc)
	 * @see mapo.common.dao.DictionaryDao#write(byte[], java.lang.String)
	 */
	@Override
	public void write(int key, String value) {
		
		BatchUpdate batchUpdate = new BatchUpdate(Bytes.toBytes(key)); //default key is the uniqueId
		batchUpdate.put(DictionaryTable.Columns.VALUE.getId(), Bytes.toBytes(value));
		
		try {
			this.dictionaryTable.commit(batchUpdate);
			if (logger.isDebugEnabled()){
				logger.debug(String.format("Adding row with key: %s, value:%s", key, value));
			}
		} catch (IOException e) {
			logger.error("error writing to dictionary table:", e);
		}

	}

	@Override
	public String get(int keyCode) {
		Cell cell = null;
		try {
			cell = this.dictionaryTable.get(Bytes.toBytes(keyCode), Bytes.toBytes(DictionaryTable.Columns.VALUE.getId()));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		if (cell == null){
			return null;
		}
		
		return new String(cell.getValue());
	}
	
}
