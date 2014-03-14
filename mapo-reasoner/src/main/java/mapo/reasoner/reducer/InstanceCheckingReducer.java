package mapo.reasoner.reducer;

import java.io.IOException;
import java.util.Iterator;

import mapo.reasoner.Node;
import mapo.reasoner.dao.TableauModelDao;
import mapo.reasoner.dao.hbase.TableauModelDaoHBaseImpl;
import mapo.reasoner.service.Reasoner;
import mapo.reasoner.service.impl.ALCTableauReasoner;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

/**
 * Generates the hash for the axioms and groups
 * 
 * @author jmayaalv
 * 
 */
public class InstanceCheckingReducer extends MapReduceBase implements Reducer<IntWritable, NullWritable, Text, NullWritable> {

	private Node node;
	private Reasoner reasoner;

	@Override
	public void reduce(IntWritable key, Iterator<NullWritable> values, OutputCollector<Text, NullWritable> arg2, Reporter arg3) throws IOException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.hadoop.mapred.MapReduceBase#configure(org.apache.hadoop.mapred
	 * .JobConf)
	 */
	@Override
	public void configure(JobConf jobConf) {
		super.configure(jobConf);
		HBaseConfiguration hBaseConfiguration = new HBaseConfiguration(jobConf);
		this.reasoner = new ALCTableauReasoner(hBaseConfiguration);
		TableauModelDao tableauModelDao = new TableauModelDaoHBaseImpl(hBaseConfiguration);
		int conceptHash = Integer.parseInt(jobConf.get("concept-hash"));
		this.node = tableauModelDao.get(conceptHash);
	}
}
