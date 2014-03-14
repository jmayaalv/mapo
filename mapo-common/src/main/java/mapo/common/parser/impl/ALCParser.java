/**
 * 
 */
package mapo.common.parser.impl;

import mapo.common.concept.ComplexConcept;
import mapo.common.concept.Concept;
import mapo.common.concept.RestrictionConcept;
import mapo.common.concept.SimpleConcept;
import mapo.common.parser.Parser;

import org.apache.commons.lang.StringUtils;

/**
 * @author jmayaalv
 * 
 */
public class ALCParser implements Parser {

	/*
	 * (non-Javadoc)
	 * 
	 * @see mapo.common.parser.Parser#parse(java.lang.String)
	 */
	@Override
	public Concept parse(String concept) {
		if (StringUtils.isBlank(concept)) {
			return null;
		}

		Concept c = SimpleConcept.parse(concept);
		if (c == null){
			c = RestrictionConcept.parse(concept);
			if (c == null){
				c = ComplexConcept.parse(concept);
			}
		}
		return c;
	}
	

}
