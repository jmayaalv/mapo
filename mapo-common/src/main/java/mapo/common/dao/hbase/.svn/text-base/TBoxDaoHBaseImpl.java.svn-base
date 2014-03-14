package mapo.common.dao.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mapo.common.concept.Concept;
import mapo.common.concept.Expression;
import mapo.common.concept.SimpleConcept;
import mapo.common.dao.TBoxDao;
import mapo.common.parser.impl.ALCParser;
import mapo.common.table.TBoxTable;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Scanner;
import org.apache.hadoop.hbase.io.BatchUpdate;
import org.apache.hadoop.hbase.io.Cell;
import org.apache.hadoop.hbase.io.RowResult;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TBoxDaoHBaseImpl implements TBoxDao {

	private static Logger logger = LoggerFactory.getLogger(IndividualDaoHBaseImpl.class);

	private final HTable tboxTable;
	private final boolean cashed;

	private static Map<Integer, List<Expression>> cache;

	public TBoxDaoHBaseImpl(HBaseConfiguration hBaseConfiguration) {
		this.cashed = false;
		try {
			this.tboxTable = new HTable(hBaseConfiguration, TBoxTable.TABLE_NAME);
		} catch (IOException e) {
			logger.error("error:", e);
			throw new RuntimeException(e);
		}
	}

	public TBoxDaoHBaseImpl(HBaseConfiguration hBaseConfiguration, boolean cash) {
		try {
			this.tboxTable = new HTable(hBaseConfiguration, TBoxTable.TABLE_NAME);
		} catch (IOException e) {
			logger.error("error:", e);
			throw new RuntimeException(e);
		}
		this.cashed = cash;
	}

	@Override
	public void write(int lhs, Concept rhs, boolean equivalent) {
		String key = String.format("%s-%s", lhs, equivalent);
		BatchUpdate batchUpdate = new BatchUpdate(Bytes.toBytes(key));
		batchUpdate.put(TBoxTable.Columns.LHS.getId(), Bytes.toBytes(lhs));
		batchUpdate.put(TBoxTable.Columns.RHS.getId(), Bytes.toBytes(rhs.toString()));
		batchUpdate.put(TBoxTable.Columns.EQUIVALENT.getId(), Bytes.toBytes(String.valueOf(equivalent)));

		try {
			this.tboxTable.commit(batchUpdate);
			if (this.cashed) {
				cache = null;
			}
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Adding row with key: %s, value:%s", key, rhs));
			}
		} catch (IOException e) {
			logger.error("error", e);
		}
	}

	@Override
	public List<Concept> get(int lhs, boolean equivalent) {
		String key = String.format("%s-%s", lhs, equivalent);
		List<Concept> concepts = getCachedConcepts(lhs, equivalent);

		//obtain concept from hbase
		Cell[] cells = null;
		try {
			cells = this.tboxTable.get(Bytes.toBytes(key), Bytes.toBytes(TBoxTable.Columns.RHS.getId()), 10);
		} catch (IOException e) {
			logger.error("error", e);
			return null;
		}

		if (cells == null || cells.length == 0) {
			return null;
		}

		ALCParser parser = new ALCParser();
		Iterator<Map.Entry<Long, byte[]>> iterator = null;
		concepts = new ArrayList<Concept>(cells.length);
		Concept concept = null;
		List<Expression> expressions = null;
		for (Cell cell : cells) { //iterate over all the versions of the cell
			iterator = cell.iterator();
			while (iterator.hasNext()) {
				concept = parser.parse(new String(iterator.next().getValue()));
				concepts.add(concept);
				if (this.cashed) {
					expressions = cache.get(lhs);
					if (expressions == null) {
						expressions = new ArrayList<Expression>();
					}
					expressions.add(new Expression(SimpleConcept.parse(String.valueOf(lhs)), concept, equivalent));
					if (cache == null){
						cache = new HashMap<Integer, List<Expression>>();
					}
					cache.put(lhs, expressions);
				}
			}
		}
		return concepts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mapo.common.dao.TBoxDao#getAll()
	 */
	@Override
	public List<Expression> getAll() {
		List<Expression> expressions = null;
		if (this.cashed && cache != null && !cache.isEmpty()) {
			expressions = getExpressionsFromCache();
		}

		if (expressions != null) {
			return expressions;
		}

		Scanner scanner = null;
		try {
			scanner = this.tboxTable.getScanner(new byte[][] { Bytes.toBytes(TBoxTable.Columns.RHS.getId()), Bytes.toBytes(TBoxTable.Columns.LHS.getId()), Bytes.toBytes(TBoxTable.Columns.EQUIVALENT.getId())});
		} catch (IOException e) {
			logger.error("error", e);
			throw new RuntimeException(e);
		}

		if (scanner == null) {
			return null;
		}

		Iterator<RowResult> iter = scanner.iterator();
		RowResult rowResult = null;
		Cell cell = null;
		Iterator<Map.Entry<Long, byte[]>> cellIterator = null;
		Concept concept = null;
		ALCParser parser = new ALCParser();
		SimpleConcept lhs = null;
		boolean equivalent = false;
		List<Expression> cachedExpressions = null;
		expressions = new ArrayList<Expression>();
		Expression exp = null;
		while (iter.hasNext()) {
			rowResult = iter.next();
			cell = rowResult.get(Bytes.toBytes(TBoxTable.Columns.RHS.getId()));
			lhs = SimpleConcept.parse(String.valueOf(Bytes.toInt(rowResult.get(Bytes.toBytes(TBoxTable.Columns.LHS.getId())).getValue())));
			equivalent = Boolean.parseBoolean(Bytes.toString(rowResult.get(Bytes.toBytes(TBoxTable.Columns.EQUIVALENT.getId())).getValue()));
			cachedExpressions = new ArrayList<Expression>();
			if (cell != null) {
				cellIterator = cell.iterator();
				while (cellIterator.hasNext()) {
					concept = parser.parse(new String(cellIterator.next().getValue()));
					exp = new Expression(SimpleConcept.parse(String.valueOf(lhs)), concept, equivalent);
					expressions.add(exp);
					if (this.cashed){
						cachedExpressions.add(exp);
					}
				}
				
				if(this.cashed){
					if (cache == null){
						cache = new HashMap<Integer, List<Expression>>();
					}
					List<Expression> eCached = cache.get(Integer.parseInt(lhs.toString()));
					if(eCached==null){
						cache.put(Integer.parseInt(lhs.toString()), cachedExpressions);
					}else{
						eCached.addAll(cachedExpressions);
					}
				}
			}

		}
		return expressions;

	}

	private List<Expression> getExpressionsFromCache() {
		if (!this.cashed || cache == null || cache.isEmpty()) {
			return null;
		}
		

		List<Expression> expresions = new ArrayList<Expression>();
		for (List<Expression> exp : cache.values()) {
			expresions.addAll(exp);
		}
		return expresions;
	}
	
	private List<Concept> getCachedConcepts(int lhs, boolean equivalent) {
		if (!this.cashed || cache == null || cache.isEmpty()) {
			return null;
		}

		List<Expression> cachedExpressions = cache.get(lhs);
		if (cachedExpressions == null) {
			return null;
		}

		List<Concept> concepts = new ArrayList<Concept>();
		Iterator<Expression> iter = cachedExpressions.iterator();
		Expression expression = null;
		while (iter.hasNext()) {
			expression = iter.next();
			if (expression.isEquivalent() == equivalent) {
				concepts.add(expression.getConcept());
			}
		}
		return concepts;
	}


}
