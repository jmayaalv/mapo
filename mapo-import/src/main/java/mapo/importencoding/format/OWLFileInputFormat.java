/**
 * 
 */
package mapo.importencoding.format;

import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;

/**
 * @author jmayaalv
 * 
 */
public class OWLFileInputFormat extends FileInputFormat<NullWritable, OWLOntologyWritable>{

	@Override
	protected boolean isSplitable(FileSystem fs, Path file) {
		return false;
	}

	@Override
	public RecordReader<NullWritable, OWLOntologyWritable> getRecordReader(InputSplit split, JobConf job, Reporter reporter) throws IOException {
		return new OWLFileRecordReader((FileSplit) split, job);
	}

}
