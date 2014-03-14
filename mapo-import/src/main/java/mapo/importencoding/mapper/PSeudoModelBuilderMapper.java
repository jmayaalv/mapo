package mapo.importencoding.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mapo.common.concept.ComplexConcept;
import mapo.common.concept.Expression;
import mapo.common.concept.ComplexConcept.Operator;
import mapo.common.dao.TBoxDao;
import mapo.common.dao.hbase.TBoxDaoHBaseImpl;
import mapo.common.table.IndividualTable;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.io.Cell;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.io.RowResult;
import org.apache.hadoop.hbase.mapred.TableMap;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class PSeudoModelBuilderMapper implements TableMap<Text, IntWritable> {

	private TBoxDao tBoxDao;

	@Override
	public void map(ImmutableBytesWritable immutableBytesWritable, RowResult rowResult, OutputCollector<Text, IntWritable> output, Reporter report) throws IOException {

		List<Expression> exps = this.tBoxDao.getAll();
		Cell cell = rowResult.get(Bytes.toBytes(IndividualTable.Columns.INDIVIDUAL.getId()));
		int individual = Bytes.toInt(cell.getValue());
		for (Expression exp : exps) {
			for (ComplexConcept cc : getComplexConcepts(exp)){
				output.collect(new Text(cc.toString()), new IntWritable(individual));
			}
		}
	}
	
	/**
	 * Gets a {@link ComplexConcept} giving an {@link Expression}
	 * C .SUB D -> .NO (C) U D
	 * C .EQ D -> .NO (C) U D, .NO (D) U C
	 * 
	 * @param expression
	 * @return
	 * @author jmayaalv
	 */
	private List<ComplexConcept> getComplexConcepts(Expression expression){
		if (expression == null){
			return null;
		}
		
		List<ComplexConcept> concepts = new ArrayList<ComplexConcept>();
		
		concepts.add(new ComplexConcept(expression.getSimpleConcept().negate(), expression.getConcept(), Operator.DISJUNCTION)); //C .EQ D -> .NO (C) U D
		if (expression.isEquivalent()){
			concepts.add(new ComplexConcept(expression.getConcept().negate(), expression.getSimpleConcept(), Operator.DISJUNCTION)); //C .EQ D -> .NO (D) U C
		}
		return concepts;
	}

	@Override
	public void configure(JobConf jobConf) {
		this.tBoxDao = new TBoxDaoHBaseImpl(new HBaseConfiguration(jobConf), true);
	}

	@Override
	public void close() throws IOException {
	}

}
