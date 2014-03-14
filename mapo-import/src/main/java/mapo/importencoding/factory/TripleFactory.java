package mapo.importencoding.factory;

import mapo.common.Triple;

public final class TripleFactory {
	
	private TripleFactory(){}
	
	/**
	 * Parse a triple from a n-triple format. 
	 * @param triple
	 * @return
	 * @throws Exception
	 * @author jmayaalv
	 */
	@SuppressWarnings("unchecked")
	public static <E> Triple<E> parseNTriple(String triple){
		
		String subject, predicate, object;
		//Parse subject
		if (triple.startsWith("<")) {
			subject = triple.substring(0,triple.indexOf('>') + 1);
		} else { //Is a bnode
			subject = triple.substring(0,triple.indexOf(' '));
		}
		
		triple = triple.substring(subject.length() + 1);
		//Parse predicate. It can be only a URI
		predicate = triple.substring(0,triple.indexOf('>') + 1);
		
		//Parse object
		triple = triple.substring(predicate.length() + 1);
		if (triple.startsWith("<")) { //URI
			object = triple.substring(0,triple.indexOf('>') + 1);
		} else if (triple.charAt(0) == '"') { //Literal
			object = triple.substring(0,triple.substring(1).indexOf('"') + 2);
			triple = triple.substring(object.length(), triple.length());
			object += triple.substring(0,triple.indexOf(' '));
		} else { //Bnode
			object = triple.substring(0,triple.indexOf(' '));
		}
		
		
		return (Triple<E>)new Triple(subject, predicate, object);
	}

}
