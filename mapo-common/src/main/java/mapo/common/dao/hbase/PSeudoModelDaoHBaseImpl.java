/**
 * 
 */
package mapo.common.dao.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mapo.common.CommonUtils;
import mapo.common.PSeudoModel;
import mapo.common.concept.SimpleConcept;
import mapo.common.dao.PSeudoModelDao;
import mapo.common.table.IndividualPseudoModelTable;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.io.BatchUpdate;
import org.apache.hadoop.hbase.io.Cell;
import org.apache.hadoop.hbase.io.RowResult;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jmayaalv
 * 
 */
public class PSeudoModelDaoHBaseImpl implements PSeudoModelDao {

	private static Logger logger = LoggerFactory.getLogger(PSeudoModelDaoHBaseImpl.class);
	private HTable pseudoModalTable;

	public PSeudoModelDaoHBaseImpl(HBaseConfiguration hBaseConfiguration) {
		try {
			this.pseudoModalTable = new HTable(hBaseConfiguration, IndividualPseudoModelTable.TABLE_NAME);
		} catch (IOException e) {
			logger.error("error", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mapo.common.dao.PSeudoModelDao#add(int, mapo.common.PSeudoModel)
	 */
	@Override
	public void add(int individual, PSeudoModel pseudoModel) {
		BatchUpdate batchUpdate = new BatchUpdate(Bytes.toBytes(individual));

		if (pseudoModel.getA() != null) {
			batchUpdate.put(IndividualPseudoModelTable.Columns.A.getId(), Bytes.toBytes(CommonUtils.serializeSetToString(pseudoModel.getA(), ',')));
		} else {
			batchUpdate.put(IndividualPseudoModelTable.Columns.A.getId(), Bytes.toBytes(""));
		}

		if (pseudoModel.getNoA() != null) {
			batchUpdate.put(IndividualPseudoModelTable.Columns.NOT_A.getId(), Bytes.toBytes(CommonUtils.serializeSetToString(pseudoModel.getNoA(), ',')));
		} else {
			batchUpdate.put(IndividualPseudoModelTable.Columns.NOT_A.getId(), Bytes.toBytes(""));
		}

		if (pseudoModel.getUniversalRestriction() != null) {
			batchUpdate.put(IndividualPseudoModelTable.Columns.UNIVERSAL_A.getId(), Bytes.toBytes(CommonUtils.serializeSetToString(pseudoModel
					.getUniversalRestriction(), ',')));
		} else {
			batchUpdate.put(IndividualPseudoModelTable.Columns.UNIVERSAL_A.getId(), Bytes.toBytes(""));
		}

		if (pseudoModel.getValueRestriction() != null) {
			batchUpdate.put(IndividualPseudoModelTable.Columns.EXISTENCE_A.getId(), Bytes.toBytes(CommonUtils.serializeSetToString(pseudoModel
					.getValueRestriction(), ',')));
		} else {
			batchUpdate.put(IndividualPseudoModelTable.Columns.EXISTENCE_A.getId(), Bytes.toBytes(""));
		}

		try {
			this.pseudoModalTable.commit(batchUpdate);
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Adding pseudomdel data to individual with key: %s: %s", individual, pseudoModel));
			}
		} catch (IOException e) {
			logger.error("error", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mapo.common.dao.PSeudoModelDao#getPseudoModels(int)
	 */
	@Override
	public List<PSeudoModel> getPseudoModels(int individual) {
		RowResult rowResult = null;
		try {
			rowResult = this.pseudoModalTable.getRow(Bytes.toBytes(individual), new byte[][] { Bytes.toBytes(IndividualPseudoModelTable.Columns.A.getId()),
					Bytes.toBytes(IndividualPseudoModelTable.Columns.NOT_A.getId()), Bytes.toBytes(IndividualPseudoModelTable.Columns.UNIVERSAL_A.getId()),
					Bytes.toBytes(IndividualPseudoModelTable.Columns.EXISTENCE_A.getId()) });

		} catch (IOException e) {
			logger.error("error", e);
			return null;
		}

		if (rowResult == null || rowResult.isEmpty()) {
			return null;
		}

		List<PSeudoModel> pseudoModels = new ArrayList<PSeudoModel>();
		PSeudoModel pseudoModel = null;

		//a
		Cell cell = rowResult.get(Bytes.toBytes(IndividualPseudoModelTable.Columns.A.getId()));
		Iterator<Map.Entry<Long, byte[]>> iterator = cell.iterator();
		while (iterator.hasNext()) {
			pseudoModel = new PSeudoModel();
			String content = Bytes.toString(iterator.next().getValue());
			if (StringUtils.isNotBlank(content)) {
				for (String sc : content.split(",")) {
					pseudoModel.addNoA(SimpleConcept.parse(sc));
				}
			}
			pseudoModels.add(pseudoModel);
		}

		//no a
		cell = rowResult.get(Bytes.toBytes(IndividualPseudoModelTable.Columns.NOT_A.getId()));
		iterator = cell.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			pseudoModel = pseudoModels.get(i);
			String content = Bytes.toString(iterator.next().getValue());
			if (StringUtils.isNotBlank(content)) {
				for (String sc : content.split(",")) {
					pseudoModel.addNoA(SimpleConcept.parse(sc));
				}
			}
			i++;
		}

		//existentce a
		cell = rowResult.get(Bytes.toBytes(IndividualPseudoModelTable.Columns.EXISTENCE_A.getId()));
		iterator = cell.iterator();
		i = 0;
		while (iterator.hasNext()) {
			pseudoModel = pseudoModels.get(i);
			String content = Bytes.toString(iterator.next().getValue());
			if (StringUtils.isNotBlank(content)) {
				for (String sc : content.split(",")) {
					pseudoModel.addValueRestriction(Integer.parseInt(sc));
				}
			}
			i++;
		}

		//universial a
		cell = rowResult.get(Bytes.toBytes(IndividualPseudoModelTable.Columns.UNIVERSAL_A.getId()));
		iterator = cell.iterator();
		i = 0;
		while (iterator.hasNext()) {
			pseudoModel = pseudoModels.get(i);
			String content = Bytes.toString(iterator.next().getValue());
			if (StringUtils.isNotBlank(content)) {
				for (String sc : content.split(",")) {
					pseudoModel.addUniversalRestriction(Integer.parseInt(sc));
				}
			}
			i++;
		}

		return pseudoModels;
	}
}
