package mapo.reasoner.rule;

import java.util.ArrayList;
import java.util.List;

import mapo.common.concept.ComplexConcept;
import mapo.common.concept.Concept;
import mapo.reasoner.Node;

public class DisjunctionRule extends AbstractRule<ComplexConcept> {

	@Override
	protected boolean isApplicable(Concept concept) {
		if (concept instanceof ComplexConcept) {
			ComplexConcept cc = (ComplexConcept) concept;
			if (ComplexConcept.Operator.DISJUNCTION.equals(cc.getOperator())) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void applyRule(Node node, ComplexConcept concept) {

		//The rule changes to node, to a node of type or and adds to children to the node
		node.setOrNode(true);
		node.removeContent(concept);

		List<Concept> rhsList = node.getExpandedConcepts();
		if (rhsList == null) {
			rhsList = new ArrayList<Concept>();
		}
		List<Concept> lhsList = new ArrayList<Concept>(rhsList);
		for (Concept exConcept : rhsList){
			node.removeContent(exConcept);
		}

		lhsList.add(concept.getLhs());
		rhsList.add(concept.getRhs());

		Node child1 = new Node(node.getIndividual(), lhsList);
		Node child2 = new Node(node.getIndividual(), rhsList);
		node.addChild(child1);
		node.addChild(child2);
	}

}
