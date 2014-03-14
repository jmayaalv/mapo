package mapo.importencoding.visitor;

import java.io.IOException;

import mapo.common.CommonUtils;
import mapo.common.util.ALCRenderer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;

public class ALCOntolologyMapperWalkerVisitor extends OWLOntologyWalkerVisitor<Object> {

	private final OutputCollector<Text, Text> output;
	private final boolean useHashes;

	/**
	 * @param walker
	 * @param output
	 * @author jmayaalv
	 */
	public ALCOntolologyMapperWalkerVisitor(OWLOntologyWalker walker, OutputCollector<Text, Text> output) {
		super(walker);
		this.output = output;
		this.useHashes = false;
	}
	
	/**
	 * @param walker
	 * @param output
	 * @author jmayaalv
	 */
	public ALCOntolologyMapperWalkerVisitor(OWLOntologyWalker walker, OutputCollector<Text, Text> output, boolean useHashes) {
		super(walker);
		this.output = output;
		this.useHashes = useHashes;
	}

	/****************** Tbox visits *****************/

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.util.OWLObjectVisitorExAdapter#visit(org.semanticweb
	 * .owlapi.model.OWLEquivalentClassesAxiom)
	 */
	@Override
	public Object visit(OWLEquivalentClassesAxiom axiom) {
		String key = CommonUtils.cleanAxiomString(axiom.getClassExpressions().toArray()[0].toString());
		String value = new ALCRenderer(this.useHashes).render(axiom);
		try {
			output.collect(new Text(CommonUtils.cleanAxiomString(key)), new Text(value.substring(value.indexOf(".EQ"))));
		} catch (IOException e) {
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.util.OWLObjectVisitorExAdapter#visit(org.semanticweb
	 * .owlapi.model.OWLClass)
	 */
	@Override
	public Object visit(OWLClass axiom) {
		try {
			output.collect(new Text(CommonUtils.cleanAxiomString(axiom.toString())), new Text(CommonUtils.cleanAxiomString(axiom.toString())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.util.OWLObjectVisitorExAdapter#visit(org.semanticweb
	 * .owlapi.model.OWLObjectProperty)
	 */
	@Override
	public Object visit(OWLObjectProperty property) {
		try {
			output.collect(new Text(CommonUtils.cleanAxiomString(property.toString())), new Text(CommonUtils.cleanAxiomString(property.toString())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.util.OWLObjectVisitorExAdapter#visit(org.semanticweb
	 * .owlapi.model.OWLSubClassOfAxiom)
	 */
	@Override
	public Object visit(OWLSubClassOfAxiom axiom) {
		String key = CommonUtils.cleanAxiomString(axiom.getSubClass().toString());
		String value = new ALCRenderer(this.useHashes).render(axiom);
		
		try {
			output.collect(new Text(key), new Text(value.substring(value.indexOf(".SUB"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/****************** Abox visits *****************/

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.util.OWLObjectVisitorExAdapter#visit(org.semanticweb
	 * .owlapi.model.OWLClassAssertionAxiom)
	 */
	@Override
	public Object visit(OWLClassAssertionAxiom axiom) {
		String key = new ALCRenderer(this.useHashes).render(axiom.getIndividual());
		String value = new ALCRenderer(this.useHashes).render(axiom.getClassExpression());
		try {
			this.output.collect(new Text(key), new Text(value));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.util.OWLObjectVisitorExAdapter#visit(org.semanticweb
	 * .owlapi.model.OWLAnonymousIndividual)
	 */
	@Override
	public Object visit(OWLAnonymousIndividual individual) {
		String value =  CommonUtils.cleanAxiomString(individual.toString());
		try {
			this.output.collect(new Text(value), new Text());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.util.OWLObjectVisitorExAdapter#visit(org.semanticweb
	 * .owlapi.model.OWLNamedIndividual)
	 */
	@Override
	public Object visit(OWLNamedIndividual individual) {
		String value =  CommonUtils.cleanAxiomString(individual.toString());
		try {
			this.output.collect(new Text(value), new Text());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object visit(OWLDataPropertyAssertionAxiom axiom) {
		StringBuilder sb = new StringBuilder(new ALCRenderer(this.useHashes).render(axiom.getProperty()));
		sb.append("(");
		sb.append(new ALCRenderer(this.useHashes).render(axiom.getObject()));
		sb.append(")");

		String key = new ALCRenderer(this.useHashes).render(axiom.getSubject());
		try {
			this.output.collect(new Text(key), new Text(sb.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.util.OWLObjectVisitorExAdapter#visit(org.semanticweb
	 * .owlapi.model.OWLObjectPropertyAssertionAxiom)
	 */
	@Override
	public Object visit(OWLObjectPropertyAssertionAxiom axiom) {
		StringBuilder sb = new StringBuilder(new ALCRenderer(this.useHashes).render(axiom.getProperty()));
		sb.append("(");
		sb.append(new ALCRenderer(this.useHashes).render(axiom.getObject()));
		sb.append(")");

		String key = new ALCRenderer(this.useHashes).render(axiom.getSubject());
		try {
			this.output.collect(new Text(key), new Text(sb.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.semanticweb.owlapi.util.OWLObjectVisitorExAdapter#visit(org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom)
	 */
	@Override
	public Object visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
		System.out.println(String.format("OWLNegativeDataPropertyAssertionAxiom Key"));
		System.out.println(axiom);
		System.out.println(getCurrentAxiom());
		return null;
	}

	/* (non-Javadoc)
	 * @see org.semanticweb.owlapi.util.OWLObjectVisitorExAdapter#visit(org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom)
	 */
	@Override
	public Object visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
		System.out.println(String.format("OWLObjectAllValuesFrom Key"));
		System.out.println(axiom);
		System.out.println(getCurrentAxiom());
		return null;
	}

	
}
