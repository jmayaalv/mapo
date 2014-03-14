package mapo.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MultiFileSplit;
import org.apache.hadoop.mapred.RecordReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class ImportRecordReader<K, V> implements RecordReader<K, V> {

	private static Logger log = LoggerFactory.getLogger(ImportRecordReader.class);
	
	protected JobConf job = null;
	protected MultiFileSplit split = null;
	protected long currentPosition = 0;
	protected FSDataInputStream currentFileStream = null;

	protected BufferedReader in = null;
	protected int currentFile = 0;
	protected String fileName = null;
	
	protected boolean openNextFile() {
		if (currentFile < split.getNumPaths()) {
			do {
				Path path = null;
				try {
					if (in != null) {
						in.close();
						currentFileStream.close();
						currentPosition += split.getLength(currentFile - 1);
					}
					in = null;
					path = split.getPath(currentFile);
					currentFileStream = path.getFileSystem(job).open(path);
					GzipCodec codec = new GzipCodec();
					codec.setConf(job);
					CompressionInputStream cin = codec.createInputStream(currentFileStream); 
					in = new BufferedReader(new InputStreamReader(cin));
					fileName = path.getName();
				} catch (Exception e) {
					if (path != null)
						log.warn("Failed opening file: " + path, e);
					else
						log.warn("Failed closing file", e);
				}
				++currentFile;
			} while (in == null && currentFile < split.getNumPaths());
			if (in != null)
				return true;
		}
		
		return false;
	}	
	
	public ImportRecordReader(JobConf job, MultiFileSplit input) throws IOException {
		split = input;
		this.job = job;
		openNextFile();
	}

	@Override
	public void close() throws IOException {
		if (in != null)
			in.close();
		if (currentFileStream != null)
			currentFileStream.close();
	}

	@Override
	public long getPos() throws IOException {
		return currentPosition;
	}

	@Override
	public float getProgress() throws IOException {
		return (float)currentPosition / (float)split.getLength();
	}
	
	public String getCurrentFileName() {
		return fileName;
	}
}
