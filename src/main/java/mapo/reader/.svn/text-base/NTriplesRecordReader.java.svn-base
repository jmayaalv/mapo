package mapo.reader;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MultiFileSplit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NTriplesRecordReader extends ImportRecordReader<Text,Text> {

	private static Logger log = LoggerFactory.getLogger(NTriplesRecordReader.class);
	
	public NTriplesRecordReader(JobConf job, MultiFileSplit input) throws IOException {
		super(job, input);
	}

	@Override
	public Text createKey() {
		return new Text();
	}

	@Override
	public Text createValue() {
		return new Text();
	}

	@Override
	public boolean next(Text key, Text value) throws IOException {
		String line = null;
		do {
			if (in != null) {
				try {
					line = in.readLine();
				} catch (Exception e) {
					log.warn("Failed reading a line. Go to the next file");
				}
				if (line != null) {
					key.set(getCurrentFileName());
					value.set(line);
					return true;
				}
			}
			
			if (!openNextFile())
				return false; 
		} while (line == null);
		
		return false;
	}

}
