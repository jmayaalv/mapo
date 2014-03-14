package mapo.importencoding.mapper;

import java.io.IOException;

import mapo.common.MapoConstants;
import mapo.common.Triple;
import mapo.importencoding.factory.TripleFactory;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Read input and assigns a unique ID. output: Key: Resource Value: Id
 * (tripleid(taskid + counter) + position)
 * 
 * @author jmayaalv
 * 
 */
public class TripleEncodingMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

	private static Logger logger = LoggerFactory.getLogger(TripleEncodingMapper.class);

	private long counter, taskId;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.hadoop.mapred.Mapper#map(java.lang.Object,
	 * java.lang.Object, org.apache.hadoop.mapred.OutputCollector,
	 * org.apache.hadoop.mapred.Reporter)
	 */
	@Override
	public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

		Triple<String> triple = TripleFactory.parseNTriple(value.toString());

		output.collect(new Text(triple.getSubject()), new Text(getValue(MapoConstants.SUBJECT_INDICATOR)));
		output.collect(new Text(triple.getPredicate()), new Text(getValue(MapoConstants.PREDICATE_INDICATOR)));
		output.collect(new Text(triple.getObject()), new Text(getValue(MapoConstants.OBJECT_INDICATOR)));
		
		counter++;
	}

	private String getValue(String positionIndicator) {
		String val = null;
		if (this.taskId == 0) {
			val = String.format("%s-%s", this.counter, positionIndicator);
		} else {
			val = String.format("%s%s-%s", this.taskId, this.counter, positionIndicator);
		}
		return val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.hadoop.mapred.MapReduceBase#configure(org.apache.hadoop.mapred
	 * .JobConf)
	 */
	@Override
	public void configure(JobConf job) {
		super.configure(job);

		String taskId = job.get("mapred.task.id");
		taskId = taskId.substring(taskId.indexOf("_m_") + 3);
		taskId = taskId.replaceAll("_", "");
		this.taskId = Long.parseLong(taskId);

		logger.debug("Assign triple counter to: " + this.counter);
	}
}
