package mapo.reasoner.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mapo.common.PSeudoModel;
import mapo.common.concept.SimpleConcept;
import mapo.common.table.IndividualPseudoModelTable;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.io.Cell;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.io.RowResult;
import org.apache.hadoop.hbase.mapred.TableMap;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class CandidateSelectionMapper implements TableMap<IntWritable, NullWritable> {

	public static final String CONCEPT_PSEUDO_MODEL = "concept_pseudo_model";

	private PSeudoModel pseudoModel;

	@Override
	public void map(ImmutableBytesWritable immutableBytesWritable, RowResult rowResult, OutputCollector<IntWritable, NullWritable> output, Reporter reporter)
			throws IOException {

		int individual = Bytes.toInt(rowResult.firstKey());

		//get the pseudomodels of the individual
		List<PSeudoModel> individualPseudoModels = getInidividualPseudoModels(rowResult);

		if (individualPseudoModels != null) {

			boolean mergable = false;
			for (PSeudoModel pModel : individualPseudoModels) {//iterate over pmodels and execute for mergable test
				mergable = pseudoModel.isMergeable(pModel);
				if (mergable) {
					break;
				}
			}

			if (!mergable) { //Models are not mergable, therefore indiviudal is a candidate to be checked
				output.collect(new IntWritable(individual), NullWritable.get());
			}
		}

	}

	private List<PSeudoModel> getInidividualPseudoModels(RowResult rowResult) {
		List<PSeudoModel> pseudoModels = new ArrayList<PSeudoModel>();

		PSeudoModel pseudoModel = null;

		//a
		Cell cell = rowResult.get(Bytes.toBytes(IndividualPseudoModelTable.Columns.A.getId()));
		Iterator<Map.Entry<Long, byte[]>> iterator = cell.iterator();
		while (iterator.hasNext()) {
			pseudoModel = new PSeudoModel();
			String content = Bytes.toString(iterator.next().getValue());
			if (StringUtils.isNotBlank(content)) {
				for (String sc : content.split(",")) {
					pseudoModel.addNoA(SimpleConcept.parse(sc));
				}
			}
			pseudoModels.add(pseudoModel);
		}

		//no a
		cell = rowResult.get(Bytes.toBytes(IndividualPseudoModelTable.Columns.NOT_A.getId()));
		iterator = cell.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			pseudoModel = pseudoModels.get(i);
			String content = Bytes.toString(iterator.next().getValue());
			if (StringUtils.isNotBlank(content)) {
				for (String sc : content.split(",")) {
					pseudoModel.addNoA(SimpleConcept.parse(sc));
				}
			}
			i++;
		}

		//existentce a
		cell = rowResult.get(Bytes.toBytes(IndividualPseudoModelTable.Columns.EXISTENCE_A.getId()));
		iterator = cell.iterator();
		i = 0;
		while (iterator.hasNext()) {
			pseudoModel = pseudoModels.get(i);
			String content = Bytes.toString(iterator.next().getValue());
			if (StringUtils.isNotBlank(content)) {
				for (String sc : content.split(",")) {
					pseudoModel.addValueRestriction(Integer.parseInt(sc));
				}
			}
			i++;
		}

		//universial a
		cell = rowResult.get(Bytes.toBytes(IndividualPseudoModelTable.Columns.UNIVERSAL_A.getId()));
		iterator = cell.iterator();
		i = 0;
		while (iterator.hasNext()) {
			pseudoModel = pseudoModels.get(i);
			String content = Bytes.toString(iterator.next().getValue());
			if (StringUtils.isNotBlank(content)) {
				for (String sc : content.split(",")) {
					pseudoModel.addUniversalRestriction(Integer.parseInt(sc));
				}
			}
			i++;
		}

		return pseudoModels;
	}

	@Override
	public void configure(JobConf conf) {
		String pseudoModelString = conf.get(CONCEPT_PSEUDO_MODEL);
		if (pseudoModelString == null) {
			throw new RuntimeException("CandidateSelectionMapper requires a pseudomodel to be executed.");
		}

		pseudoModel = PSeudoModel.parse(pseudoModelString);
	}

	@Override
	public void close() throws IOException {
	}

}
