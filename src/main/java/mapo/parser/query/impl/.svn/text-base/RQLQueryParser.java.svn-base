/**
 * 
 */
package mapo.parser.query.impl;

import java.util.ArrayList;
import java.util.List;

import mapo.dom.Query;
import mapo.dom.Triple;
import mapo.parser.query.QueryParser;

import org.apache.commons.lang.StringUtils;

/**
 * Parses a query from RQL format
 * 
 * @author jmayaalv
 * 
 */

//SELECT X from
// ub:GraduateStudent {X}. ub:takesCourse {Y}
//where Y=http://www.Department0.University0.edu/GraduateCourse0
//using namespace ub=http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#
//TODO Better Error handling
public class RQLQueryParser implements QueryParser {

	private static final String SEPARATOR = System.getProperty("line.separator");
	private static final String CLASS_TYPE = "typeOf";

	/*
	 * (non-Javadoc)
	 * 
	 * @see mapo.parser.query.QueryParser#parse(java.lang.String)
	 */
	@Override
	public Query parse(String queryString) {

		Query query = new Query();
		queryString = queryString.replace(SEPARATOR, " ");

		query.setVars(parseVariables(queryString));

		query.setTriples(parseTriples(queryString));

		processWhere(query, queryString);

		return query;
	}

	private void processWhere(Query query, String queryString) {
		int start = StringUtils.indexOf(queryString.trim(), " where ") + 6;
		if (start <= 5) {
			return; //no where to process
		}
		int end = StringUtils.indexOf(queryString, " using ");
		String[] where = queryString.substring(start, end).trim().split(",");
		for (int i = 0; i < where.length; i++) {
			String[] whereVars = where[i].split("="); //0:Var, 1: value
			for (Triple<String> t : query.getTriples()) {
				if (t.getObject().equalsIgnoreCase(whereVars[0].trim())) {
					t.setObject(whereVars[1].trim());
				}else if (t.getSubject().equalsIgnoreCase(whereVars[0].trim())) {
					t.setSubject(whereVars[1].trim());
				}
			}
		}

	}

	private List<String> parseVariables(String queryString) {
		int start = StringUtils.indexOf(queryString.toLowerCase(), " select ") + 7;
		int end = StringUtils.indexOf(queryString.toLowerCase(), " from ");
		String[] vars = queryString.substring(start, end).split(",");
		List<String> varList = new ArrayList<String>();
		for (int i = 0; i < vars.length; i++) {
			if (StringUtils.isNotBlank(vars[i])) {
				varList.add(StringUtils.trim(vars[i]));
			}
		}
		return varList;
	}

	private List<Triple<String>> parseTriples(String queryString) {
		int fromStart = StringUtils.indexOf(queryString.toLowerCase(), " from ") + 6;
		int fromEnd = StringUtils.indexOf(queryString.toLowerCase(), " where ");
		if (fromEnd == -1) {
			fromEnd = StringUtils.indexOf(queryString.toLowerCase(), " using ");
			if (fromEnd == -1) {
				fromEnd = queryString.length();
			}
		}

		String[] fromTriples = queryString.substring(fromStart, fromEnd).split(",");
		String triple = null;
		String subject, predicate, object = null;
		List<Triple<String>> triples = new ArrayList<Triple<String>>();
		Triple<String> oTriple = null;
		for (int i = 0; i < fromTriples.length; i++) {
			triple = fromTriples[i].trim();
			if (triple.contains(".")) {
				//tree shape triples
				String[] leaves = triple.split("\\.");
				for (int j = 0; j < leaves.length; j++) {
					String leave = leaves[j];

					if (oTriple != null && CLASS_TYPE.equalsIgnoreCase(oTriple.getPredicate())) {
						subject = oTriple.getSubject();
						object = getObject(leave, false);
					} else if (oTriple != null) {
						subject = oTriple.getObject();
						object = getObject(leave, false);
					} else {
						subject = getSubject(leave);
						object = getObject(leave, true);
					}
					predicate = getPredicate(leave);

					if (predicate.equalsIgnoreCase(object)) {
						predicate = CLASS_TYPE;
					}

					oTriple = new Triple<String>(subject, predicate, object);
					triples.add(oTriple);
				}
			} else {
				//simple triple

				//subject
				subject = getSubject(triple);

				//predicate
				predicate = getPredicate(triple);

				//object
				object = getObject(triple, true);

				if (object.equalsIgnoreCase(predicate)) {
					predicate = CLASS_TYPE;
				}

				triples.add(new Triple<String>(subject, predicate, object));
			}
		}
		return triples;
	}

	private String getSubject(String triple) {
		triple = triple.trim();
		int index = triple.indexOf("{");
		return triple.substring(index + 1, triple.indexOf("}", index));
	}

	private String getPredicate(String triple) {
		triple = triple.trim();
		int index = triple.indexOf(":");
		index = StringUtils.lastIndexOf(triple, " ", index);
		if (index == -1) {
			index = 0;
		}
		return triple.substring(index, triple.indexOf(" ", index + 1));
	}

	private String getObject(String triple, boolean verifyPredicate) {
		triple = triple.trim();
		String object;
		if (verifyPredicate && triple.indexOf("{") == StringUtils.lastIndexOf(triple, "{")) {
			//only one var in the triple
			object = getPredicate(triple);
		} else {
			object = triple.substring(StringUtils.lastIndexOf(triple, "{") + 1, StringUtils.lastIndexOf(triple, "}"));
		}
		return object;

	}

	public static void main(String[] args) {
		RQLQueryParser parser = new RQLQueryParser();
		Query q = parser
				.parse("SELECT X from ub:GraduateStudent {X}. ub:takesCourse {Y} where Y=http://www.Department0.University0.edu/GraduateCourse0 using namespace ub=http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#");
		
		System.out.println(q);
	}

}
