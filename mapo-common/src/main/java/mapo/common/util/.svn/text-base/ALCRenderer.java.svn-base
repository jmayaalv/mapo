/**
 * 
 */
package mapo.common.util;

import java.util.Set;

import mapo.common.CommonUtils;
import mapo.common.concept.RestrictionConcept;
import mapo.common.concept.ComplexConcept.Operator;

import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLStringLiteral;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTypedLiteral;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.util.ShortFormProvider;

/**
 * Serializes {@link OWLAxiom} to an ALC format
 * 
 * @author jmayaalv
 * 
 */
public class ALCRenderer implements OWLObjectVisitor, OWLObjectRenderer {

	private StringBuilder sb;
	private ShortFormProvider shortFormProvider;
	private final boolean hash;

	public ALCRenderer() {
		this.sb = new StringBuilder();
		this.hash = false;
	}
	
	public ALCRenderer(boolean hash) {
		this.sb = new StringBuilder();
		this.hash = hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLObjectVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLOntology)
	 */
	@Override
	public void visit(OWLOntology ontology) {
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLDeclarationAxiom)
	 */
	@Override
	public void visit(OWLDeclarationAxiom arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLSubClassOfAxiom)
	 */
	@Override
	public void visit(OWLSubClassOfAxiom axiom) {
		axiom.getSubClass().accept(this);
		sb.append(" .SUB ");
		axiom.getSuperClass().accept(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLNegativeObjectPropertyAssertionAxiom)
	 */
	@Override
	public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
		sb.append(".NO (");
		axiom.accept(this);
		sb.append(")");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLAsymmetricObjectPropertyAxiom)
	 */
	@Override
	public void visit(OWLAsymmetricObjectPropertyAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLReflexiveObjectPropertyAxiom)
	 */
	@Override
	public void visit(OWLReflexiveObjectPropertyAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLDisjointClassesAxiom)
	 */
	@Override
	public void visit(OWLDisjointClassesAxiom axiom) {
		Set<OWLClassExpression> owlClassEspressions = axiom.getClassExpressions();
		if (owlClassEspressions != null) {
			int i = 1;
			for (OWLClassExpression exp : owlClassEspressions) {
				exp.accept(this);
				if (i < owlClassEspressions.size()) {
					sb.append(".OR");
				}
				i++;
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLDataPropertyDomainAxiom)
	 */
	@Override
	public void visit(OWLDataPropertyDomainAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLObjectPropertyDomainAxiom)
	 */
	@Override
	public void visit(OWLObjectPropertyDomainAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLEquivalentObjectPropertiesAxiom)
	 */
	@Override
	public void visit(OWLEquivalentObjectPropertiesAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLNegativeDataPropertyAssertionAxiom)
	 */
	@Override
	public void visit(OWLNegativeDataPropertyAssertionAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLDifferentIndividualsAxiom)
	 */
	@Override
	public void visit(OWLDifferentIndividualsAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLDisjointDataPropertiesAxiom)
	 */
	@Override
	public void visit(OWLDisjointDataPropertiesAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLDisjointObjectPropertiesAxiom)
	 */
	@Override
	public void visit(OWLDisjointObjectPropertiesAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLObjectPropertyRangeAxiom)
	 */
	@Override
	public void visit(OWLObjectPropertyRangeAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLObjectPropertyAssertionAxiom)
	 */
	@Override
	public void visit(OWLObjectPropertyAssertionAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLFunctionalObjectPropertyAxiom)
	 */
	@Override
	public void visit(OWLFunctionalObjectPropertyAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLSubObjectPropertyOfAxiom)
	 */
	@Override
	public void visit(OWLSubObjectPropertyOfAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLDisjointUnionAxiom)
	 */
	@Override
	public void visit(OWLDisjointUnionAxiom axiom) {
		Set<OWLClassExpression> owlClassEspressions = axiom.getClassExpressions();
		if (owlClassEspressions != null) {
			int i = 1;
			for (OWLClassExpression exp : owlClassEspressions) {
				exp.accept(this);
				if (i < owlClassEspressions.size()) {
					sb.append(" .OR ");
				}
				i++;
			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLSymmetricObjectPropertyAxiom)
	 */
	@Override
	public void visit(OWLSymmetricObjectPropertyAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLDataPropertyRangeAxiom)
	 */
	@Override
	public void visit(OWLDataPropertyRangeAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLFunctionalDataPropertyAxiom)
	 */
	@Override
	public void visit(OWLFunctionalDataPropertyAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLEquivalentDataPropertiesAxiom)
	 */
	@Override
	public void visit(OWLEquivalentDataPropertiesAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLClassAssertionAxiom)
	 */
	@Override
	public void visit(OWLClassAssertionAxiom axiom) {
		axiom.getClassExpression().accept(this);
		sb.append("(");
		axiom.getIndividual().accept(this);
		sb.append(")");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLEquivalentClassesAxiom)
	 */
	@Override
	public void visit(OWLEquivalentClassesAxiom axiom) {
		Set<OWLClassExpression> owlClassEspressions = axiom.getClassExpressions();
		
		if (owlClassEspressions != null) {
			int i = 1;
			for (OWLClassExpression exp : owlClassEspressions) {
				exp.accept(this);
				if (i < owlClassEspressions.size()) {
					sb.append(" .EQ ");
				}
				i++;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLDataPropertyAssertionAxiom)
	 */
	@Override
	public void visit(OWLDataPropertyAssertionAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLTransitiveObjectPropertyAxiom)
	 */
	@Override
	public void visit(OWLTransitiveObjectPropertyAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLIrreflexiveObjectPropertyAxiom)
	 */
	@Override
	public void visit(OWLIrreflexiveObjectPropertyAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLSubDataPropertyOfAxiom)
	 */
	@Override
	public void visit(OWLSubDataPropertyOfAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLInverseFunctionalObjectPropertyAxiom)
	 */
	@Override
	public void visit(OWLInverseFunctionalObjectPropertyAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLSameIndividualAxiom)
	 */
	@Override
	public void visit(OWLSameIndividualAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLSubPropertyChainOfAxiom)
	 */
	@Override
	public void visit(OWLSubPropertyChainOfAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLInverseObjectPropertiesAxiom)
	 */
	@Override
	public void visit(OWLInverseObjectPropertiesAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLHasKeyAxiom)
	 */
	@Override
	public void visit(OWLHasKeyAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLDatatypeDefinitionAxiom)
	 */
	@Override
	public void visit(OWLDatatypeDefinitionAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitor#visit(org.semanticweb.owlapi
	 * .model.SWRLRule)
	 */
	@Override
	public void visit(SWRLRule arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAnnotationAxiomVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLAnnotationAssertionAxiom)
	 */
	@Override
	public void visit(OWLAnnotationAssertionAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAnnotationAxiomVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLSubAnnotationPropertyOfAxiom)
	 */
	@Override
	public void visit(OWLSubAnnotationPropertyOfAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAnnotationAxiomVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLAnnotationPropertyDomainAxiom)
	 */
	@Override
	public void visit(OWLAnnotationPropertyDomainAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAnnotationAxiomVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLAnnotationPropertyRangeAxiom)
	 */
	@Override
	public void visit(OWLAnnotationPropertyRangeAxiom arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLClass)
	 */
	@Override
	public void visit(OWLClass axiom) {
		sb.append(print(axiom.toString()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLObjectIntersectionOf)
	 */
	@Override
	public void visit(OWLObjectIntersectionOf axiom) {
		Set<OWLClassExpression> owlClassEspressions = axiom.getOperands();
		if (owlClassEspressions != null) {
			int i = 1;
			for (OWLClassExpression exp : owlClassEspressions) {
				exp.accept(this);
				if (i < owlClassEspressions.size()) {
					sb.append(" ");
					sb.append(Operator.CONJUNCTION.getId());
					sb.append(" ");
				}
				i++;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLObjectUnionOf)
	 */
	@Override
	public void visit(OWLObjectUnionOf axiom) {
		Set<OWLClassExpression> owlClassEspressions = axiom.getOperands();
		if (owlClassEspressions != null) {
			int i = 1;
			for (OWLClassExpression exp : owlClassEspressions) {
				exp.accept(this);
				if (i < owlClassEspressions.size()) {
					sb.append(" ");
					sb.append(Operator.DISJUNCTION.getId());
					sb.append(" ");
				}
				i++;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLObjectComplementOf)
	 */
	@Override
	public void visit(OWLObjectComplementOf arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLObjectSomeValuesFrom)
	 */
	@Override
	public void visit(OWLObjectSomeValuesFrom axiom) {
		sb.append(" ");
		sb.append(RestrictionConcept.Restriction.VALUE_RESTRICTION.getId());
		sb.append(" ");
		axiom.getProperty().accept(this);
		sb.append(". (");
		axiom.getFiller().accept(this);
		sb.append(")");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLObjectAllValuesFrom)
	 */
	@Override
	public void visit(OWLObjectAllValuesFrom axiom) {
		sb.append(" ");
		sb.append(RestrictionConcept.Restriction.UNIVERSAL_RESTRICTION.getId());
		sb.append(" ");
		axiom.getProperty().accept(this);
		sb.append(". (");
		axiom.getFiller().accept(this);
		sb.append(")");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLObjectHasValue)
	 */
	@Override
	public void visit(OWLObjectHasValue arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLObjectMinCardinality)
	 */
	@Override
	public void visit(OWLObjectMinCardinality arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLObjectExactCardinality)
	 */
	@Override
	public void visit(OWLObjectExactCardinality arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLObjectMaxCardinality)
	 */
	@Override
	public void visit(OWLObjectMaxCardinality arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLObjectHasSelf)
	 */
	@Override
	public void visit(OWLObjectHasSelf arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLObjectOneOf)
	 */
	@Override
	public void visit(OWLObjectOneOf arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLDataSomeValuesFrom)
	 */
	@Override
	public void visit(OWLDataSomeValuesFrom arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLDataAllValuesFrom)
	 */
	@Override
	public void visit(OWLDataAllValuesFrom arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLDataHasValue)
	 */
	@Override
	public void visit(OWLDataHasValue arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLDataMinCardinality)
	 */
	@Override
	public void visit(OWLDataMinCardinality arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLDataExactCardinality)
	 */
	@Override
	public void visit(OWLDataExactCardinality arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLDataMaxCardinality)
	 */
	@Override
	public void visit(OWLDataMaxCardinality arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLDataVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLTypedLiteral)
	 */
	@Override
	public void visit(OWLTypedLiteral arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLDataVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLStringLiteral)
	 */
	@Override
	public void visit(OWLStringLiteral literal) {
		sb.append(print(literal.toString()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLDataVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLFacetRestriction)
	 */
	@Override
	public void visit(OWLFacetRestriction arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLDataRangeVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLDatatype)
	 */
	@Override
	public void visit(OWLDatatype arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLDataRangeVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLDataOneOf)
	 */
	@Override
	public void visit(OWLDataOneOf arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLDataRangeVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLDataComplementOf)
	 */
	@Override
	public void visit(OWLDataComplementOf arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLDataRangeVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLDataIntersectionOf)
	 */
	@Override
	public void visit(OWLDataIntersectionOf arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLDataRangeVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLDataUnionOf)
	 */
	@Override
	public void visit(OWLDataUnionOf arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLDataRangeVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLDatatypeRestriction)
	 */
	@Override
	public void visit(OWLDatatypeRestriction arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.semanticweb.owlapi.model.OWLPropertyExpressionVisitor#visit(org.
	 * semanticweb.owlapi.model.OWLObjectProperty)
	 */
	@Override
	public void visit(OWLObjectProperty axiom) {
		sb.append(print(axiom.toString()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.semanticweb.owlapi.model.OWLPropertyExpressionVisitor#visit(org.
	 * semanticweb.owlapi.model.OWLObjectInverseOf)
	 */
	@Override
	public void visit(OWLObjectInverseOf arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.semanticweb.owlapi.model.OWLPropertyExpressionVisitor#visit(org.
	 * semanticweb.owlapi.model.OWLDataProperty)
	 */
	@Override
	public void visit(OWLDataProperty data) {
		sb.append(print(data.toString()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLEntityVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLNamedIndividual)
	 */
	@Override
	public void visit(OWLNamedIndividual individual) {
		sb.append(print(individual.toString()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLEntityVisitor#visit(org.semanticweb.owlapi
	 * .model.OWLAnnotationProperty)
	 */
	@Override
	public void visit(OWLAnnotationProperty arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLIndividualVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLAnonymousIndividual)
	 */
	@Override
	public void visit(OWLAnonymousIndividual individual) {
		sb.append(print(individual.toString()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAnnotationValueVisitor#visit(org.semanticweb
	 * .owlapi.model.IRI)
	 */
	@Override
	public void visit(IRI arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAnnotationObjectVisitor#visit(org.semanticweb
	 * .owlapi.model.OWLAnnotation)
	 */
	@Override
	public void visit(OWLAnnotation arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.SWRLObjectVisitor#visit(org.semanticweb.
	 * owlapi.model.SWRLClassAtom)
	 */
	@Override
	public void visit(SWRLClassAtom arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.SWRLObjectVisitor#visit(org.semanticweb.
	 * owlapi.model.SWRLDataRangeAtom)
	 */
	@Override
	public void visit(SWRLDataRangeAtom arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.SWRLObjectVisitor#visit(org.semanticweb.
	 * owlapi.model.SWRLObjectPropertyAtom)
	 */
	@Override
	public void visit(SWRLObjectPropertyAtom arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.SWRLObjectVisitor#visit(org.semanticweb.
	 * owlapi.model.SWRLDataPropertyAtom)
	 */
	@Override
	public void visit(SWRLDataPropertyAtom arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.SWRLObjectVisitor#visit(org.semanticweb.
	 * owlapi.model.SWRLBuiltInAtom)
	 */
	@Override
	public void visit(SWRLBuiltInAtom arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.SWRLObjectVisitor#visit(org.semanticweb.
	 * owlapi.model.SWRLVariable)
	 */
	@Override
	public void visit(SWRLVariable arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.SWRLObjectVisitor#visit(org.semanticweb.
	 * owlapi.model.SWRLIndividualArgument)
	 */
	@Override
	public void visit(SWRLIndividualArgument arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.SWRLObjectVisitor#visit(org.semanticweb.
	 * owlapi.model.SWRLLiteralArgument)
	 */
	@Override
	public void visit(SWRLLiteralArgument arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.SWRLObjectVisitor#visit(org.semanticweb.
	 * owlapi.model.SWRLSameIndividualAtom)
	 */
	@Override
	public void visit(SWRLSameIndividualAtom arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.SWRLObjectVisitor#visit(org.semanticweb.
	 * owlapi.model.SWRLDifferentIndividualsAtom)
	 */
	@Override
	public void visit(SWRLDifferentIndividualsAtom arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.io.OWLObjectRenderer#render(org.semanticweb.owlapi
	 * .model.OWLObject)
	 */
	@Override
	public String render(OWLObject object) {
		  object.accept(this);
		  return sb.toString();
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.io.OWLObjectRenderer#setShortFormProvider(org.
	 * semanticweb.owlapi.util.ShortFormProvider)
	 */
	@Override
	public void setShortFormProvider(ShortFormProvider shortFormProvider) {
		this.shortFormProvider = shortFormProvider;
	}
	
	private Object print(String text){
		if (this.hash){
			return CommonUtils.cleanAxiomString(text).hashCode();
		}
		return CommonUtils.cleanAxiomString(text);
	}

}
