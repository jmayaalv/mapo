/**
 * 
 */
package mapo.common.dao.hbase;

import java.io.IOException;

import mapo.common.dao.IndividualDao;
import mapo.common.table.IndividualTable;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.io.BatchUpdate;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jmayaalv
 * 
 */
public class IndividualDaoHBaseImpl implements IndividualDao {

	private static Logger logger = LoggerFactory.getLogger(IndividualDaoHBaseImpl.class);

	private HTable individualTable;

	public IndividualDaoHBaseImpl(HBaseConfiguration hBaseConfiguration) {
		try {
			this.individualTable = new HTable(hBaseConfiguration, IndividualTable.TABLE_NAME);
		} catch (IOException e) {
			logger.error("error:", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mapo.common.dao.IndividualDao#write(long)
	 */
	@Override
	public void write(int individual) {
		
		BatchUpdate batchUpdate = new BatchUpdate(Bytes.toBytes(individual));
		batchUpdate.put(IndividualTable.Columns.INDIVIDUAL.getId(),Bytes.toBytes(individual));
		
		try {
			this.individualTable.commit(batchUpdate);
		} catch (IOException e) {
			logger.error("error", e);
			throw new RuntimeException(e);
		}
	}

}
