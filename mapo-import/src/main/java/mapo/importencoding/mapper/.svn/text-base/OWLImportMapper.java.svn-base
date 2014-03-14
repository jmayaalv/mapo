package mapo.importencoding.mapper;

import java.io.IOException;
import java.util.Collections;

import mapo.importencoding.format.OWLOntologyWritable;
import mapo.importencoding.visitor.ALCOntolologyMapperWalkerVisitor;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extract the ALC equivalant constructs from the input ontology.
 * @author jmayaalv
 * 
 */
public class OWLImportMapper extends MapReduceBase implements Mapper<NullWritable, OWLOntologyWritable, Text, Text> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(OWLImportMapper.class);
	
	@SuppressWarnings("unused")
	private JobConf conf;

	@Override
	public void map(NullWritable key, OWLOntologyWritable value, final OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

		OWLOntology ontology = value.getOwlOntology();
		OWLOntologyWalker walker = new OWLOntologyWalker(Collections.singleton(ontology));
		OWLOntologyWalkerVisitor<Object> visitor = new ALCOntolologyMapperWalkerVisitor(walker, output, true);
		walker.walkStructure(visitor);

	}

	@Override
	public void configure(JobConf conf) {
		this.conf = conf;
	}
}
