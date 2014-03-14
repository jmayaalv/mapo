package map.common.parser.impl;

import mapo.common.concept.ComplexConcept;
import mapo.common.concept.RestrictionConcept;
import mapo.common.concept.SimpleConcept;
import mapo.common.concept.ComplexConcept.Operator;
import mapo.common.concept.RestrictionConcept.Restriction;
import mapo.common.parser.Parser;
import mapo.common.parser.impl.ALCParser;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ALCParserTester {
	
	@Test
	public void simpleConceptTest(){
		int concept = 1;
		SimpleConcept sc = new SimpleConcept(concept);
		Assert.assertEquals(sc.getConcept(), concept);
		Assert.assertFalse(sc.isNegate());
	}
	
	@Test
	public void negatedSimpleConceptTest(){
		String concept = ".NO 2";
		SimpleConcept sc = SimpleConcept.parse(concept);
		Assert.assertEquals(sc.getConcept(), 2);
		Assert.assertTrue(sc.isNegate());
	}
	
	@Test
	public void universalRestrictonConceptTest(){
		String concept = ".FORALL 1.   (.NO 2)";
		RestrictionConcept sc = RestrictionConcept.parse(concept);
		Assert.assertEquals(sc.getConcept(), SimpleConcept.parse(".NO (2)"));
		Assert.assertEquals(sc.getRole(), 1);
		Assert.assertEquals(sc.getRestriction(), Restriction.UNIVERSAL_RESTRICTION);
	}
	
	@Test
	public void existentialRestrictonConceptTest(){
		String concept = ".EXIST 1. 2";
		RestrictionConcept sc = RestrictionConcept.parse(concept);
		Assert.assertEquals(sc.getConcept(), SimpleConcept.parse("2"));
		Assert.assertEquals(sc.getRole(), 1);
		Assert.assertEquals(sc.getRestriction(), Restriction.VALUE_RESTRICTION);
	}
	
	@Test
	public void complexConceptTest(){
		String concept = "1 .AND 2";
		ComplexConcept c = ComplexConcept.parse(concept);
		Assert.assertEquals(c.getLhs(), SimpleConcept.parse("1"));
		Assert.assertEquals(c.getRhs(), SimpleConcept.parse("2"));
		Assert.assertEquals(c.getOperator(), Operator.CONJUNCTION);
	}
	
	@Test
	public void complexConceptTest2(){
		String concept = "(1 .AND 2)";
		ComplexConcept c = ComplexConcept.parse(concept);
		Assert.assertEquals(c.getLhs(), SimpleConcept.parse("1"));
		Assert.assertEquals(c.getRhs(), SimpleConcept.parse("2"));
		Assert.assertEquals(c.getOperator(), Operator.CONJUNCTION);
	}
	
	@Test
	public void complexConceptTest3(){
		String concept = "(1 .AND .NO 2)";
		ComplexConcept c = ComplexConcept.parse(concept);
		Assert.assertEquals(c.getLhs(), SimpleConcept.parse("1"));
		Assert.assertEquals(c.getRhs(), SimpleConcept.parse(".NO(2)"));
		Assert.assertEquals(c.getOperator(), Operator.CONJUNCTION);
	}
	
	@Test
	public void complexConceptTest4(){
		String concept = "(1 .AND (2 .OR 3))";
		ComplexConcept c = ComplexConcept.parse(concept);
		Assert.assertEquals(c.getLhs(), SimpleConcept.parse("1"));
		Assert.assertEquals(c.getRhs(), ComplexConcept.parse("2 .OR 3"));
		Assert.assertEquals(Operator.CONJUNCTION, c.getOperator());
		
	}
	
	@Test
	public void complexConceptTest5(){
		
		String concept = "((1 .AND .NO 3) .AND (2 .OR 3))";
		ComplexConcept c = ComplexConcept.parse(concept);
		Assert.assertEquals(c.getLhs(), ComplexConcept.parse("1 .AND .NO 3"));
		Assert.assertEquals(c.getRhs(), ComplexConcept.parse("2 .OR 3"));
		Assert.assertEquals(Operator.CONJUNCTION, c.getOperator());
		
	}
	
	@Test
	public void complexConceptTest6(){
		String concept = "((2 .AND .NO 3) .OR (.EXIST 1. 2))";
		ComplexConcept c = ComplexConcept.parse(concept);
		Assert.assertEquals(c.getLhs(), ComplexConcept.parse("2 .AND .NO 3"));
		Assert.assertEquals(c.getRhs(), RestrictionConcept.parse(".EXIST 1. 2"));
		Assert.assertEquals(Operator.DISJUNCTION, c.getOperator());
		
	}
	
	@Test
	public void complexConceptTest7(){
		String concept = "((-1480020648) .AND .EXIST 1086932677. (-1842875874))";
		Parser parser = new ALCParser();
		ComplexConcept c = (ComplexConcept)parser.parse(concept); 
//		Assert.assertEquals(c.getLhs(), ComplexConcept.parse("C .AND .NO D"));
//		Assert.assertEquals(c.getRhs(), RestrictionConcept.parse(".EXIST 1. C"));
//		Assert.assertEquals(Operator.DISJUNCTION, c.getOperator());
		System.out.println(c);
		System.out.println(c.negate());
		
	}
	
	@Test
	public void complexConceptTest8(){
		String concept = ".NO (-1303080972)";
		Parser parser = new ALCParser();
		SimpleConcept c = (SimpleConcept)parser.parse(concept); 
		System.out.println(c);
	}
	
}
