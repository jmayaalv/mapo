/**
 * 
 */
package mapo.reasoner.impl;

import java.io.IOException;
import java.util.List;

import mapo.common.CommonUtils;
import mapo.common.PSeudoModel;
import mapo.common.concept.ComplexConcept;
import mapo.common.concept.Concept;
import mapo.common.concept.RestrictionConcept;
import mapo.common.concept.SimpleConcept;
import mapo.common.concept.ComplexConcept.Operator;
import mapo.common.concept.RestrictionConcept.Restriction;
import mapo.common.parser.impl.ALCParser;
import mapo.reasoner.Node;
import mapo.reasoner.service.Reasoner;
import mapo.reasoner.service.impl.ALCTableauReasoner;
import mapo.reasoner.visitor.impl.ALCCrashVisitor;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author jmayaalv
 * 
 */
public class ALCTableauReasonerTester {

	private final Reasoner reasoner = new ALCTableauReasoner();

	@Test
	public void alcConjunctionExpansionTest() throws IOException, ClassNotFoundException {
		Concept concept = new ComplexConcept(new SimpleConcept(1), new SimpleConcept(2), ComplexConcept.Operator.CONJUNCTION);

		Node node = reasoner.buildAbstractModel(concept);

		Assert.assertEquals(node.getContent().size(), 2);
		Assert.assertTrue(node.getAllChildren() == null || node.getAllChildren().isEmpty());
		Assert.assertEquals(node.getNewIndividualCounter(), 1);

		node.accept(new ALCCrashVisitor());

		Assert.assertEquals(node.getStatus(), Node.Status.SATISFIABLE);

		byte[] data = CommonUtils.toBytes(node);
		System.out.println(data);
		Node node2 = (Node) CommonUtils.toObject(data);
		System.out.println(node2);
		Assert.assertEquals(node2.getStatus(), Node.Status.SATISFIABLE);
	}

	//@Test
	public void alcDisnjunctionExpansionTest() {
		Concept concept = new ComplexConcept(new SimpleConcept(1), new SimpleConcept(2), ComplexConcept.Operator.DISJUNCTION);
		Concept concept2 = new ComplexConcept(concept, new SimpleConcept(3), ComplexConcept.Operator.DISJUNCTION);

		Node node = reasoner.buildAbstractModel(concept2);

		Assert.assertTrue(node.getContent().isEmpty());
		Assert.assertTrue(node.getAllChildren().size() == 2);
		Assert.assertEquals(node.getNewIndividualCounter(), 1);

		node.accept(new ALCCrashVisitor());
		Assert.assertEquals(node.getStatus(), Node.Status.SATISFIABLE);
	}

	//@Test
	public void alcValueRestrictionExpansionTest() {
		Concept concept = new RestrictionConcept(Restriction.VALUE_RESTRICTION, 1, new SimpleConcept(1));
		Node node = reasoner.buildAbstractModel(concept);

		Assert.assertTrue(node.getContent().isEmpty());
		Assert.assertTrue(node.getAllChildren().size() == 1);
		Assert.assertEquals(node.getNewIndividualCounter(), 2);

		node.accept(new ALCCrashVisitor());
		Assert.assertEquals(node.getStatus(), Node.Status.SATISFIABLE);
	}

	//@Test
	public void alcUniveralRestrictionExpansionTest() {
		Concept concept = new RestrictionConcept(Restriction.UNIVERSAL_RESTRICTION, 1, new SimpleConcept(1));
		Node node = reasoner.buildAbstractModel(concept);

		Assert.assertTrue(node.getContent().isEmpty());
		Assert.assertTrue(node.getAllChildren().size() == 1);
		Assert.assertEquals(node.getNewIndividualCounter(), 2);

		node.accept(new ALCCrashVisitor());
		Assert.assertEquals(node.getStatus(), Node.Status.SATISFIABLE);
	}

	//@Test
	public void alcComplexExpansionTest1() {
		Concept concept = new ComplexConcept(new SimpleConcept(3), new RestrictionConcept(Restriction.VALUE_RESTRICTION, 1, new SimpleConcept(1)),
				Operator.DISJUNCTION);
		Node node = reasoner.buildAbstractModel(concept);

		node.accept(new ALCCrashVisitor());
		Assert.assertEquals(node.getStatus(), Node.Status.SATISFIABLE);
	}

	//@Test
	public void alcComplexExpansionTest2() {
		SimpleConcept a = new SimpleConcept(1);
		SimpleConcept b = new SimpleConcept(2);

		Concept concept = new ComplexConcept(new RestrictionConcept(Restriction.VALUE_RESTRICTION, 1, a), new RestrictionConcept(Restriction.VALUE_RESTRICTION,
				1, b), Operator.CONJUNCTION);

		Concept unionConcept = new ComplexConcept(a.negate(), b.negate(), Operator.DISJUNCTION);

		Concept expression = new ComplexConcept(concept, new RestrictionConcept(Restriction.UNIVERSAL_RESTRICTION, 1, unionConcept), Operator.CONJUNCTION);

		Node node = reasoner.buildAbstractModel(expression);

		node.accept(new ALCCrashVisitor());
		System.out.println(node);
		Assert.assertEquals(node.getStatus(), Node.Status.SATISFIABLE);
	}

	//@Test
	public void alcComplexCrashTest() {
		SimpleConcept a = new SimpleConcept(1);
		SimpleConcept b = new SimpleConcept(2);

		Concept concept = new ComplexConcept(new RestrictionConcept(Restriction.VALUE_RESTRICTION, 1, a), new RestrictionConcept(Restriction.VALUE_RESTRICTION,
				1, b), Operator.CONJUNCTION);
		Concept unionConcept = new ComplexConcept(a.negate(), b.negate(), Operator.DISJUNCTION);
		Concept expression = new ComplexConcept(concept, new RestrictionConcept(Restriction.UNIVERSAL_RESTRICTION, 1, unionConcept), Operator.CONJUNCTION);

		Node node = reasoner.buildAbstractModel(expression);
		node.accept(new ALCCrashVisitor());

		Assert.assertEquals(node.getStatus(), Node.Status.SATISFIABLE);
	}

	//@Test
	public void alcPseudoModelTest1() {
		Concept concept = new ALCParser().parse("((.NO (-1480020648) .OR .FORALL -1137743094. (.NO (2017931510))) .OR 84640753)");

		Node node = reasoner.buildAbstractModel(concept);
		List<PSeudoModel> pseudoModels = reasoner.buildConceptPSeudoModels(node, null);
		for (PSeudoModel pm : pseudoModels) {
			System.out.println(pm);
		}

	}

	//@Test
	public void alcPseudoModelTest2() {
		Concept concept = new RestrictionConcept(Restriction.UNIVERSAL_RESTRICTION, 1, new SimpleConcept(1, true));
		Node node = reasoner.buildAbstractModel(concept);
		List<PSeudoModel> pseudoModels = reasoner.buildConceptPSeudoModels(node, new SimpleConcept(2, true));
		Assert.assertEquals(pseudoModels.size(), 1);
	}

	//	@Test
	public void alcPseudoModelTest3() {
		Concept concept = ComplexConcept.parse(".NO(1) .OR 2");
		Node node = reasoner.buildAbstractModel(concept);
		List<PSeudoModel> pseudoModels = reasoner.buildConceptPSeudoModels(node, null);
		Assert.assertEquals(pseudoModels.size(), 1);
	}
}
