/**
 * 
 */
package mapo.common.dao.hbase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mapo.common.concept.SimpleConcept;
import mapo.common.dao.ABoxDao;
import mapo.common.table.ABoxConceptAssertionTable;
import mapo.common.table.ABoxRoleAssertionTable;

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
public class ABoxDaoHBaseImpl implements ABoxDao {

	private static Logger logger = LoggerFactory.getLogger(IndividualDaoHBaseImpl.class);
	
	private HTable aboxConceptAssertionTable;
	private HTable aboxRoleAssertionTable;

	public ABoxDaoHBaseImpl(HBaseConfiguration hBaseConfiguration) {
		try {
			this.aboxConceptAssertionTable = new HTable(hBaseConfiguration, ABoxConceptAssertionTable.TABLE_NAME);
			this.aboxRoleAssertionTable = new HTable(hBaseConfiguration, ABoxRoleAssertionTable.TABLE_NAME);
		} catch (IOException e) {
			logger.error("error", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mapo.common.dao.ABoxDao#getAssertedObject(int, int)
	 */
	@Override
	public Map<SimpleConcept, Integer> getAssertedObjects(int individual, int role) {
		String key = String.format("%s-%s", individual, role);
		Cell[] cells = null;
		try {
			cells = this.aboxConceptAssertionTable.get(Bytes.toBytes(key), Bytes.toBytes(ABoxRoleAssertionTable.Columns.OBJECT.getId()), 1000);
		} catch (IOException e) {
			logger.error("error", e);
			return null;
		}

		if (cells == null || cells.length == 0) {
			return null;
		}

		Map<SimpleConcept, Integer> concepts = new HashMap<SimpleConcept, Integer>(cells.length);
		Iterator<Map.Entry<Long, byte[]>> iterator = null;
		for (Cell cell : cells) { //iterate over all the versions of the cell
			iterator = cell.iterator();
			while (iterator.hasNext()) {
				concepts.put(SimpleConcept.parse(String.valueOf(Bytes.toInt((iterator.next().getValue())))), role);
			}
		}
		return concepts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mapo.common.dao.ABoxDao#getConcetAssertion(int)
	 */
	@Override
	public Map<SimpleConcept, Integer> getConceptAssertion(int individual) {
		Cell[] cells = null;
		try {
			cells = this.aboxConceptAssertionTable.get(Bytes.toBytes(individual), Bytes.toBytes(ABoxConceptAssertionTable.Columns.CONCEPT.getId()), 1000);
		} catch (IOException e) {
			logger.error("error", e);
			return null;
		}

		if (cells == null || cells.length == 0) {
			return null;
		}

		Map<SimpleConcept, Integer> concepts = new HashMap<SimpleConcept, Integer>(cells.length);
		Iterator<Map.Entry<Long, byte[]>> iterator = null;
		SimpleConcept sc = null;
		for (Cell cell : cells) { //iterate over all the versions of the cell
			iterator = cell.iterator();
			while (iterator.hasNext()) {
				int s = Bytes.toInt(iterator.next().getValue());
				sc = SimpleConcept.parse(String.valueOf(s));
				concepts.put(sc, individual);
			}
		}
		return concepts;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mapo.common.dao.ABoxDao#writeConceptAssertion(int, int)
	 */
	@Override
	public void addConceptAssertion(int individual, int concept) {
		BatchUpdate batchUpdate = new BatchUpdate(Bytes.toBytes(individual));
		batchUpdate.put(ABoxConceptAssertionTable.Columns.CONCEPT.getId(), Bytes.toBytes(concept));

		try {
			this.aboxConceptAssertionTable.commit(batchUpdate);
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Adding concept assertion %s(%s)", concept, individual));
			}
		} catch (IOException e) {
			logger.error("error", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mapo.common.dao.ABoxDao#writeRoleAssertion(int, int, int)
	 */
	@Override
	public void addRoleAssertion(int individual, int role, int object) {
		String key = String.format("%s-%s", individual, role);
		BatchUpdate batchUpdate = new BatchUpdate(Bytes.toBytes(key));
		batchUpdate.put(ABoxRoleAssertionTable.Columns.ROLE.getId(), Bytes.toBytes(role));
		batchUpdate.put(ABoxRoleAssertionTable.Columns.OBJECT.getId(), Bytes.toBytes(object));

		try {
			this.aboxRoleAssertionTable.commit(batchUpdate);
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Adding role assertion %s(%s, %s)", role, individual, object));
			}
		} catch (IOException e) {
			logger.error("error", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mapo.common.dao.ABoxDao#hasConceptAssertion(int,
	 * mapo.common.concept.SimpleConcept)
	 */
	@Override
	public boolean hasConceptAssertion(int individual, SimpleConcept concept) {
		Map<SimpleConcept, Integer> concepts = getConceptAssertion(individual);
		boolean hasConcept = false;
		if (concepts == null || concepts.isEmpty()) {
			return hasConcept;
		}

		return concepts.containsKey(concept);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mapo.common.dao.ABoxDao#hasRoleAssertion(int, int)
	 */
	@Override
	public boolean hasRoleAssertion(int individual, int role) {
		Map<SimpleConcept, Integer> concepts = getAssertedObjects(individual, role);
		return concepts != null && !concepts.isEmpty();
	}

}
