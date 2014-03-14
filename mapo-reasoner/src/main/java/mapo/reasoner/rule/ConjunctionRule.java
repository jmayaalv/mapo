package mapo.reasoner.rule;

import mapo.common.concept.ComplexConcept;
import mapo.common.concept.Concept;
import mapo.reasoner.Node;

public class ConjunctionRule extends AbstractRule<ComplexConcept>{

	

	@Override
	protected boolean isApplicable(Concept concept) {
		if (concept instanceof ComplexConcept) {
			ComplexConcept cc = (ComplexConcept)concept;
			if (ComplexConcept.Operator.CONJUNCTION.equals(cc.getOperator())){
				return true;
			}
		}
		return false;
	}

	@Override
	protected void applyRule(Node node, ComplexConcept concept) {
		
		//The rule simply adds to new concepts to the content and removes the original one
		node.addContent(concept.getLhs());
		node.addContent(concept.getRhs());
		node.removeContent(concept);
	}

}
