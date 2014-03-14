package mapo.reasoner.service;

import java.util.List;

import mapo.common.PSeudoModel;
import mapo.common.concept.Concept;
import mapo.common.concept.SimpleConcept;
import mapo.reasoner.Node;

public interface Reasoner {
	
	/**
	 * Builds a tableaux model for a given concept 
	 * @param concept
	 * @return
	 * @author jmayaalv
	 */
	public Node buildAbstractModel(Concept concept);
	
	/**
	 * Builds the list{@link PSeudoModel} associated with a node
	 * @param node
	 * @param baseConcept
	 * @return
	 * @author jmayaalv
	 */
	public List<PSeudoModel> buildConceptPSeudoModels(Node node, SimpleConcept baseConcept);
	
	/**
	 * Gets the PseudoModels associated with an individual for a specific concept
	 *  
	 * @param individual
	 * @param concept
	 * @return
	 * @author jmayaalv
	 */
	public List<PSeudoModel> getIndividualPSeudoModels(int individual, Concept concept);
	
	/**
	 * Gets the PseudoModels associated with an individual given
	 *  
	 * @param individual
	 * @param concept
	 * @return
	 * @author jmayaalv
	 */
	public List<PSeudoModel> getIndividualPSeudoModels(int individual, Node node, List<PSeudoModel> conceptPModel);

}
