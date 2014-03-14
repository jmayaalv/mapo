/**
 * 
 */
package mapo.importencoding.job;

import java.io.File;
import java.io.IOException;

import mapo.common.MapoConstants;
import mapo.common.table.IndividualTable;
import mapo.importencoding.format.OWLFileInputFormat;
import mapo.importencoding.mapper.OWLImportMapper;
import mapo.importencoding.mapper.PSeudoModelBuilderMapper;
import mapo.importencoding.reducer.OWLImportReducer;
import mapo.importencoding.reducer.PSeudoModelBuilderReducer;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.mapred.TableMapReduceUtil;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.SequenceFileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jmayaalv
 * 
 */
public class OwlImportJob extends Configured implements Tool {

	private static final String OUTPUT_DIR = "output/temp";

	private static Logger logger = LoggerFactory.getLogger(OwlImportJob.class);

	private int numMapTasks = 1;
	private int numReduceTasks = 1;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {

		if (args.length < 1) {
			System.err.println("Usage: OwpImportJob [input dir]");
			System.exit(0);
		}

		try {
			int res = ToolRunner.run(new Configuration(), new OwlImportJob(), args);
			System.exit(res);
		} catch (Exception e) {
		}
	}

	private void parseArgs(String[] args) throws IOException {

		for (int i = 0; i < args.length; ++i) {
			if (args[i].equalsIgnoreCase("--maptasks")) {
				numMapTasks = Integer.valueOf(args[++i]);
			}

			if (args[i].equalsIgnoreCase("--reducetasks")) {
				numReduceTasks = Integer.valueOf(args[++i]);
			}
		}
	}

	/**
	 * @throws IOException
	 * @author jmayaalv
	 */
	private void deleteDirectory(String directoryPath) throws IOException {
		File dir = new File(directoryPath);
		if (dir.exists()) {
			FileUtils.deleteDirectory(dir);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.hadoop.util.Tool#run(java.lang.String[])
	 */
	@Override
	public int run(String[] args) throws Exception {

		try {
			parseArgs(args);

			String tboxInputPath = args[0];
			String tboxOutputPath = OUTPUT_DIR;

//			deleteDirectory(tboxOutputPath);

			//import data
		//	importKnowledgeBase(tboxInputPath, tboxOutputPath);
			deleteDirectory(tboxOutputPath);
			createAboxPSeudoModels(tboxOutputPath);

		} catch (Exception e) {
			logger.error("Error in the execution: " + e);
			e.printStackTrace();
		}
		return 0;
	}

	private void importKnowledgeBase(String inputPath, String outputPath) throws IOException {
		JobConf conf = new JobConf(getConf(), OwlImportJob.class);
		conf.setJobName("OWLImport");

		System.out.println("out: " + outputPath);
		System.out.println("in: " + inputPath);
		FileOutputFormat.setOutputPath(conf, new Path(outputPath, MapoConstants.TMP_DIR));
		FileInputFormat.addInputPath(conf, new Path(inputPath));

		FileOutputFormat.setOutputPath(conf, new Path(outputPath, MapoConstants.TMP_DIR));

		conf.setMapperClass(OWLImportMapper.class);
		conf.setReducerClass(OWLImportReducer.class);

		conf.setInputFormat(OWLFileInputFormat.class);
		conf.setOutputFormat(SequenceFileOutputFormat.class);

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);

		if (numMapTasks != -1) {
			logger.info("Set # map tasks to: " + numMapTasks);
			conf.setNumMapTasks(numMapTasks);
		}

		if (numReduceTasks != -1) {
			logger.info("Set # reduce tasks to: " + numReduceTasks);
			conf.setNumReduceTasks(numReduceTasks);
		}

		//Launch
		long time = System.currentTimeMillis();
		JobClient.runJob(conf);
		logger.info("Impor Job finished in " + (System.currentTimeMillis() - time));
	}

	private void createAboxPSeudoModels(String outputPath) throws IOException {
		JobConf conf = new JobConf(getConf(), OwlImportJob.class);
		conf.setJobName("ABoxPSeudoModels");
		
		FileOutputFormat.setOutputPath(conf, new Path(outputPath));
		if (numMapTasks != -1) {
			logger.info("Set # map tasks to: " + numMapTasks);
			conf.setNumMapTasks(numMapTasks);
		}

		if (numReduceTasks != -1) {
			logger.info("Set # reduce tasks to: " + numReduceTasks);
			conf.setNumReduceTasks(numReduceTasks);
		}
		
		TableMapReduceUtil.initTableMapJob(IndividualTable.TABLE_NAME, IndividualTable.Columns.INDIVIDUAL.getId(), PSeudoModelBuilderMapper.class,
				Text.class, IntWritable.class, conf);
		
		conf.setReducerClass(PSeudoModelBuilderReducer.class);
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);
		
		//Launch
		long time = System.currentTimeMillis();
		JobClient.runJob(conf);
		logger.info("PseudoModelJob finished in " + (System.currentTimeMillis() - time));
	}

}
