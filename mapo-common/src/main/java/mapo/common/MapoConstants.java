/**
 * 
 */
package mapo.common;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

/**
 * @author jmayaalv
 * 
 */
public final class MapoConstants {

	public static final String SUBJECT_INDICATOR = "s";
	public static final String PREDICATE_INDICATOR = "p";
	public static final String OBJECT_INDICATOR = "o";

	public final static String TRIPLES_DIR = "src/main/input"; //assumes user is standing in the pom dir
	public final static String TMP_DIR = "temp"; //assumes user is standing in the pom dir
	public final static String ENCODE_DIR = "encode"; //assumes user is standing in the pom dir

	public final static String RDF_SCHEMA_URL = "http://www.w3.org/2000/01/rdf-schema";
	public final static String OWL_SCHEMA_URL = "http://www.w3.org/2002/07/owl";
	public static final String TYPE_OWL = "OWL";

	private MapoConstants() {
	}

	public static final class FileConstants {

		private FileConstants() {
		}

		public static final PathFilter FILTER_ONLY_HIDDEN = new PathFilter() {
			public boolean accept(Path p) {
				String name = p.getName();
				return !name.startsWith("_") && !name.startsWith(".");
			}
		};
	}

	public static enum OWLPredicate {

		SAME_AS(OWL_SCHEMA_URL + "#" + "sameAs"), FUNCTIONAL_PROPERTY(OWL_SCHEMA_URL + "#" + "FunctionalProperty"), INVERSE_FUNCTIONAL_PROPERTY(OWL_SCHEMA_URL
				+ "#" + "InverseFunctionalProperty"), SYMMETRIC_PROPERTY(OWL_SCHEMA_URL + "#" + "SymmetricProperty"), TRANSITIVE_PROPERTY(OWL_SCHEMA_URL + "#"
				+ "TransitiveProperty"), INVERSE_OF(OWL_SCHEMA_URL + "#" + "inverseOf"), CLASS(OWL_SCHEMA_URL + "#" + "Class"), PROPERTY(OWL_SCHEMA_URL + "#"
				+ "Property"), EQUIVALENT_CLASS(OWL_SCHEMA_URL + "#" + "equivalentClass"), EQUIVALENT_PROPERTY(OWL_SCHEMA_URL + "#" + "equivalentProperty"), HAS_VALUE(
				OWL_SCHEMA_URL + "#" + "hasValue"), ON_PROPERTY(OWL_SCHEMA_URL + "#" + "onProperty"), SOME_VALUES_FROM(OWL_SCHEMA_URL + "#" + "someValueFrom"), ALL_VALUES_FROM(
				OWL_SCHEMA_URL + "#" + "allValuesFrom");

		private final String predicate;

		/**
		 * @param predicate
		 * @author jmayaalv
		 */
		private OWLPredicate(String predicate) {
			this.predicate = predicate;
		}

		/**
		 * @return the predicate
		 */
		public String getPredicate() {
			return predicate;
		}
	}

	public static enum Predicate {

		TYPE(RDF_SCHEMA_URL + "#" + "type"), SUB_CLASS(RDF_SCHEMA_URL + "#" + "subClassOf"), SUB_PROPERTY_OF(RDF_SCHEMA_URL + "#" + "subPropertyOf"), DOMAIN(
				RDF_SCHEMA_URL + "#" + "domain"), RANGE(RDF_SCHEMA_URL + "#" + "range");

		private final String predicate;

		/**
		 * @param predicate
		 * @author jmayaalv
		 */
		private Predicate(String predicate) {
			this.predicate = predicate;
		}

		/**
		 * @return the predicate
		 */
		public String getPredicate() {
			return predicate;
		}
	}

}
