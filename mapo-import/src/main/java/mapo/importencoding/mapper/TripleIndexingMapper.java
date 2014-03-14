package mapo.importencoding.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

/**
 * Mapper used to group by triple ids. Input: key: uniqueid value: tripleid +
 * position Output: key: Triple id value: UniqueId + Position
 * 
 * @author jmayaalv
 * 
 */
public class TripleIndexingMapper extends MapReduceBase implements Mapper<Text, Text, LongWritable, Text> {

	@Override
	public void map(Text key, Text value, OutputCollector<LongWritable, Text> output, Reporter reporter) throws IOException {
		
		String tripleInfo  = value.toString();
		output.collect(new LongWritable(extractTripleId(tripleInfo)), new Text(String.format("%s-%s", key.toString(), extractPosition(tripleInfo))));
		
	}

	/**
	 * Extracts the id of the triple
	 * @param tripleInfo
	 * @return
	 * @author jmayaalv
	 */
	private long extractTripleId(String tripleInfo) {
		return Long.parseLong(tripleInfo.substring(0, tripleInfo.lastIndexOf('-')));
	}

	/**
	 * Extracts the position from the value
	 * 
	 * @param string
	 * @return
	 * @author jmayaalv
	 */
	private String extractPosition(String tripleInfo) {
		return tripleInfo.substring(tripleInfo.indexOf('-') + 1, tripleInfo.length());
	}

}
