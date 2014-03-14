/**
 * 
 */
package mapo.common.dao.hbase;

import java.io.IOException;
import java.util.List;

import mapo.common.CommonUtils;
import mapo.common.PSeudoModel;
import mapo.common.dao.IndividualPseudoModelDao;
import mapo.common.table.IndividualPseudoModelTable;

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
public class IndividualPseudoModelDaoHBaseImpl implements IndividualPseudoModelDao {

	private static Logger logger = LoggerFactory.getLogger(IndividualPseudoModelDaoHBaseImpl.class);

	private HTable individualPseudoModelTable;

	public IndividualPseudoModelDaoHBaseImpl(HBaseConfiguration hBaseConfiguration) {
		try {
			this.individualPseudoModelTable = new HTable(hBaseConfiguration, IndividualPseudoModelTable.TABLE_NAME);
		} catch (IOException e) {
			logger.error("error:", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mapo.common.dao.IndividualPseudoModelDao#write(long, java.util.List,
	 * java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public void write(long individual, PSeudoModel pseudoModel) {

		BatchUpdate batchUpdate = new BatchUpdate(Bytes.toBytes(individual));
		if (pseudoModel.getA() != null && !pseudoModel.getA().isEmpty()) {
			batchUpdate.put(IndividualPseudoModelTable.Columns.A.getId(), Bytes.toBytes(CommonUtils.serializeSetToString(pseudoModel.getA(), ';')));
		}

		if (pseudoModel.getNoA() != null && !pseudoModel.getNoA().isEmpty()) {
			batchUpdate.put(IndividualPseudoModelTable.Columns.NOT_A.getId(), Bytes.toBytes(CommonUtils.serializeSetToString(pseudoModel.getNoA(), ';')));
		}

		if (pseudoModel.getValueRestriction() != null && !pseudoModel.getValueRestriction().isEmpty()) {
			batchUpdate.put(IndividualPseudoModelTable.Columns.EXISTENCE_A.getId(), Bytes.toBytes(CommonUtils.serializeSetToString(pseudoModel.getValueRestriction(), ';')));
		}

		if (pseudoModel.getUniversalRestriction() != null && !pseudoModel.getUniversalRestriction().isEmpty()) {
			batchUpdate.put(IndividualPseudoModelTable.Columns.UNIVERSAL_A.getId(), Bytes.toBytes(CommonUtils.serializeSetToString(pseudoModel.getUniversalRestriction(), ';')));
		}

		try {
			this.individualPseudoModelTable.commit(batchUpdate);
		} catch (IOException e) {
			logger.error("error", e);
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<PSeudoModel> get(long individual) {
		//TODO: DO IT
		return null;
	}

}
