package mapo.importencoding.reducer;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import mapo.common.PSeudoModel;
import mapo.common.concept.Concept;
import mapo.common.dao.PSeudoModelDao;
import mapo.common.dao.hbase.PSeudoModelDaoHBaseImpl;
import mapo.common.parser.impl.ALCParser;
import mapo.reasoner.Node;
import mapo.reasoner.service.Reasoner;
import mapo.reasoner.service.impl.ALCTableauReasoner;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.io.IntWritable;
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
public class PSeudoModelBuilderReducer extends MapReduceBase implements Reducer<Text, IntWritable, Text, Text> {

	@SuppressWarnings("unused")
	private JobConf conf;
	private Reasoner reasoner;
	private PSeudoModelDao pseudoModelDao;
	
	private final ALCParser alcParser;

	/**
	 * 
	 * @author jmayaalv
	 */
	private PSeudoModelBuilderReducer() {
		super();
		this.alcParser = new ALCParser();
	}

	@Override
	public void configure(JobConf conf) {
		this.conf = conf;
		HBaseConfiguration hBaseConfiguration = new HBaseConfiguration(conf);
		this.reasoner = new ALCTableauReasoner(hBaseConfiguration);
		this.pseudoModelDao = new PSeudoModelDaoHBaseImpl(hBaseConfiguration);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.hadoop.mapred.Reducer#reduce(java.lang.Object,
	 * java.util.Iterator, org.apache.hadoop.mapred.OutputCollector,
	 * org.apache.hadoop.mapred.Reporter)
	 */
	@Override
	public void reduce(Text text, Iterator<IntWritable> valuesIterator, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

		Concept concept = this.alcParser.parse(text.toString());
		Node conceptModel = this.reasoner.buildAbstractModel(concept);
		List<PSeudoModel> conceptPSeudoModels = this.reasoner.buildConceptPSeudoModels(conceptModel, null);
		System.out.println("Models for:" + text.toString());
		System.out.println("     " + conceptPSeudoModels.toString());

		//use the node to build the pseudo model for all the individuals
		List<PSeudoModel> individualPSeudoModels = null;
		int individual = 0;
		while (valuesIterator.hasNext()) {
			individual = valuesIterator.next().get();
			individualPSeudoModels = this.reasoner.getIndividualPSeudoModels(individual, conceptModel, conceptPSeudoModels);
			if (individualPSeudoModels != null && !individualPSeudoModels.isEmpty()) {
				//add to pseudo model table
				System.out.println("adding to pmodel table: " + individualPSeudoModels + " for individual: " + individual);
				for (PSeudoModel pm : individualPSeudoModels){
					this.pseudoModelDao.add(individual, pm);
				}
			}
		}

	}

}
