package mapo.importencoding.format;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.RecordReader;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OWLFileRecordReader implements RecordReader<NullWritable, OWLOntologyWritable> {

	private FileSplit fileSplit;
	private Configuration conf;
	private boolean processed = false;

	/**
	 * @param fileSplit
	 * @param conf
	 * @author jmayaalv
	 */
	public OWLFileRecordReader(FileSplit fileSplit, Configuration conf) {
		super();
		this.fileSplit = fileSplit;
		this.conf = conf;
	}

	@Override
	public void close() throws IOException {
	}

	@Override
	public NullWritable createKey() {
		return NullWritable.get();
	}

	@Override
	public OWLOntologyWritable createValue() {
		return new OWLOntologyWritable();
	}

	@Override
	public long getPos() throws IOException {
		return processed ? fileSplit.getLength() : 0;
	}

	@Override
	public float getProgress() throws IOException {
		return processed ? 1.0f : 0.0f;
	}

	@Override
	public boolean next(NullWritable key, OWLOntologyWritable value) throws IOException {
		if (processed) {
			return false;
		}

		byte[] contents = new byte[(int) fileSplit.getLength()];
		Path file = fileSplit.getPath();
		FileSystem fs = file.getFileSystem(conf);
		FSDataInputStream in = null;

		try {
			in = fs.open(file);
			IOUtils.readFully(in, contents, 0, contents.length);

			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			OWLOntology ontology = manager.loadOntology(new ByteArrayInputSource(contents));
			value.setOwlOntology(ontology);

		}catch(Exception e){
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeStream(in);
		}
		processed = true;
		return true;
	}

}
