/**
 * 
 */
package mapo.reasoner.dao.hbase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import mapo.common.CommonUtils;
import mapo.common.table.ModelTable;
import mapo.reasoner.Node;
import mapo.reasoner.dao.TableauModelDao;

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
public class TableauModelDaoHBaseImpl implements TableauModelDao {

	private static Logger logger = LoggerFactory.getLogger(TableauModelDaoHBaseImpl.class);
	private HTable tableauModelTable;
	private Map<Integer, Node> cache;

	public TableauModelDaoHBaseImpl(HBaseConfiguration hBaseConfiguration) {
		try {
			this.tableauModelTable = new HTable(hBaseConfiguration, ModelTable.TABLE_NAME);
		} catch (IOException e) {
			logger.error("error", e);
		}
	}

	/**
	 * Adds a node to cache
	 * 
	 * @param key
	 * @param node
	 * @author jmayaalv
	 */
	private void addToCache(int key, Node node) {
		if (this.cache == null) {
			this.cache = new HashMap<Integer, Node>();
		}
		this.cache.put(key, node);
	}

	private Node getFromCache(int key) {
		if (cache == null || cache.isEmpty()) {
			return null;
		}

		return cache.get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mapo.reasoner.dao.TableauModelDao#add(int, mapo.reasoner.Node)
	 */
	@Override
	public void add(int expressionHash, Node model) {
		BatchUpdate batchUpdate = new BatchUpdate(Bytes.toBytes(expressionHash));
		try {
			batchUpdate.put(ModelTable.Columns.MODEL.getId(), CommonUtils.toBytes(model));

			this.tableauModelTable.commit(batchUpdate);
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Adding model for concept: %s", expressionHash));
			}
		} catch (IOException e) {
			logger.error("error", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mapo.reasoner.dao.TableauModelDao#get(int)
	 */
	@Override
	public Node get(int expressionHash) {
		Node node = getFromCache(expressionHash);
		if (node != null) {
			return node;
		}

		try {
			Cell cell = this.tableauModelTable.get(Bytes.toBytes(expressionHash), Bytes.toBytes(ModelTable.Columns.MODEL.getId()));
			if (cell == null || cell.getValue() == null) {
				return null;
			}

			node = (Node) CommonUtils.toObject(cell.getValue());
			addToCache(expressionHash, node);
			
		} catch (ClassNotFoundException e) {
			logger.error("error", e);
		} catch (IOException e) {
			logger.error("error", e);
		}
		return node;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mapo.reasoner.dao.TableauModelDao#remove(int)
	 */
	@Override
	public void remove(int expressionHash) {
		try {
			this.tableauModelTable.deleteAll(Bytes.toBytes(expressionHash));
		} catch (IOException e) {
			logger.error("error", e);
		}
	}
}
