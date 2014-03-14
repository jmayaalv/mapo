package mapo.importencoding.reducer;

import java.io.IOException;
import java.util.Iterator;

import mapo.common.concept.Concept;
import mapo.common.dao.ABoxDao;
import mapo.common.dao.DictionaryDao;
import mapo.common.dao.IndividualDao;
import mapo.common.dao.TBoxDao;
import mapo.common.dao.hbase.ABoxDaoHBaseImpl;
import mapo.common.dao.hbase.DictionaryDaoHBaseImpl;
import mapo.common.dao.hbase.IndividualDaoHBaseImpl;
import mapo.common.dao.hbase.TBoxDaoHBaseImpl;
import mapo.common.parser.impl.ALCParser;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.HBaseConfiguration;
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
public class OWLImportReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

	@SuppressWarnings("unused")
	private JobConf conf;
	private DictionaryDao dictionaryDao;
	private IndividualDao individualDao;
	private ABoxDao aBoxDao;
	private TBoxDao tBoxDao;
	private final ALCParser alcParser = new ALCParser();

	@Override
	public void configure(JobConf conf) {
		this.conf = conf;
		this.dictionaryDao = new DictionaryDaoHBaseImpl(new HBaseConfiguration(conf));
		this.tBoxDao = new TBoxDaoHBaseImpl(new HBaseConfiguration(conf));
		this.aBoxDao = new ABoxDaoHBaseImpl(new HBaseConfiguration(conf));
		this.individualDao = new IndividualDaoHBaseImpl(new HBaseConfiguration(conf));
	}

	@Override
	public void reduce(Text key, Iterator<Text> value, OutputCollector<Text, Text> out, Reporter report) throws IOException {
		String iterValue = null;
		boolean hashed = false;
		boolean individualAdded = false;
		Concept concept = null;
		while (value.hasNext()) {
			iterValue = value.next().toString().trim();
			if (iterValue.startsWith(".EQ")) {
				iterValue = iterValue.substring(3, iterValue.length());
				concept = alcParser.parse(iterValue);
				this.tBoxDao.write(key.toString().hashCode(), concept, true);
			}else if (iterValue.startsWith(".SUB")) {
				iterValue = iterValue.substring(4, iterValue.length());
				concept = alcParser.parse(iterValue);
				this.tBoxDao.write(key.toString().hashCode(), concept, false);
			}else if (iterValue.contains("(")){//it's an abox role assertion of the type r(key,o). <key, r(o)>
				this.aBoxDao.addRoleAssertion(Integer.parseInt(key.toString()), parseRole(iterValue), parseObject(iterValue));
			}else if (isInteger(iterValue)){//it's a concept assert of the form concept(key). <key, concept>
				this.aBoxDao.addConceptAssertion(Integer.parseInt(key.toString()), Integer.parseInt(iterValue));
			}else if(StringUtils.isBlank(iterValue)){
				if (!individualAdded){
					this.individualDao.write(key.toString().hashCode());
					this.dictionaryDao.write(key.toString().hashCode(), key.toString());
					individualAdded = true;
					hashed=true;
				}
			} else if (!hashed) { //adds hashcode to dictionary for abox or tbox
				this.dictionaryDao.write(key.toString().hashCode(), iterValue);
				hashed = true;
			}
		}
	}

	private boolean isInteger(String iterValue) {
		try {
			Integer.parseInt(iterValue);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	private int parseRole(String roleAssertion){
		roleAssertion = roleAssertion.trim();
		return Integer.parseInt(roleAssertion.substring(0, roleAssertion.indexOf("(")));
	}
	
	private int parseObject(String roleAssertion){
		roleAssertion = roleAssertion.trim();
		return Integer.parseInt(roleAssertion.substring(roleAssertion.indexOf("(") + 1, StringUtils.lastIndexOf(roleAssertion, ")")).trim());
	}
}
