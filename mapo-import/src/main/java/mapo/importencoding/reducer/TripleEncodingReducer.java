/**
 * 
 */
package mapo.importencoding.reducer;

import java.io.IOException;
import java.util.Iterator;

import mapo.common.dao.DictionaryDao;
import mapo.common.dao.hbase.DictionaryDaoHBaseImpl;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

/**
 * Generates an unique id for a term. Input: Key: Term Value: Term id.
 * (taskid-counterid-position(s|p|o)) Output: Key: Unique Term id
 * (reduceTaskId-counter) Value: id. (taskid-counterid-position(s|p|o))
 * 
 * @author jmayaalv
 * 
 */
public class TripleEncodingReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

	private long uniqueId;
	private long taskId;

	private DictionaryDao dictionaryDao;

	@Override
	public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter report) throws IOException {
		long uniqueId = getTermUniqueId();
		//this.dictionaryDao.write(Bytes.toBytes(uniqueId), key.toString());
		while (values.hasNext()) {
			Text value = values.next();
			output.collect(new Text(String.valueOf(uniqueId)), value);
		}
	}


	private Long getTermUniqueId() {
		return Long.parseLong(String.format("%s%s", this.taskId, uniqueId++));
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

		String taskId = job.get("mapred.task.id").substring(job.get("mapred.task.id").indexOf("_r_") + 3);
		taskId = taskId.replaceAll("_", "");
		this.taskId = Long.valueOf(taskId);

		//create dao
		this.dictionaryDao = new DictionaryDaoHBaseImpl(new HBaseConfiguration(job));
	}
}
