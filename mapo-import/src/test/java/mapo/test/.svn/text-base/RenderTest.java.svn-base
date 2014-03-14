package mapo.test;
import java.net.URI;
import java.util.Collections;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import org.testng.annotations.Test;

public class RenderTest {

	@Test
	public void renderTest() throws Exception {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		IRI iri = IRI.create("http://www.co-ode.org/ontologies/pizza/uni.owl");
		manager.addIRIMapper(new SimpleIRIMapper(iri, URI.create("file:/Users/jmayaalv/Documents/Master/Thesis/lumb/Owl/uba1.7_University0_0.owl")));

		OWLOntology ontology = manager.loadOntology(iri);

		OWLOntologyWalker walker = new OWLOntologyWalker(Collections.singleton(ontology));
		OWLOntologyWalkerVisitor<Object> visitor = new OWLOntologyWalkerVisitor<Object>(walker) {


//			/* (non-Javadoc)
//			 * @see org.semanticweb.owlapi.util.OWLObjectVisitorExAdapter#visit(org.semanticweb.owlapi.model.OWLClassAssertionAxiom)
//			 */
//			@Override
//			public Object visit(OWLClassAssertionAxiom axiom) {
//				System.out.println("*****");
//				System.out.println("OWLClassAssertionAxiom");
//				System.out.println(new ALCRenderer().render(axiom));
//				return null;
//			}
//
//			/* (non-Javadoc)
//			 * @see org.semanticweb.owlapi.util.OWLObjectVisitorExAdapter#visit(org.semanticweb.owlapi.model.OWLAnonymousIndividual)
//			 */
//			@Override
//			public Object visit(OWLAnonymousIndividual individual) {
//				System.out.println("*****");
//				System.out.println("OWLAnonymousIndividual");
//				System.out.println(individual);
//				System.out.println(getCurrentAxiom());
//				return null;
//			}
//
//			/* (non-Javadoc)
//			 * @see org.semanticweb.owlapi.util.OWLObjectVisitorExAdapter#visit(org.semanticweb.owlapi.model.OWLNamedIndividual)
//			 */
//			@Override
//			public Object visit(OWLNamedIndividual individual) {
//				System.out.println("*****");
//				System.out.println("OWLNamedIndividual");
//				System.out.println(new ALCRenderer().render(individual));
//				return null;
//			}
			

			/* (non-Javadoc)
			 * @see org.semanticweb.owlapi.util.OWLObjectVisitorExAdapter#visit(org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom)
			 */
//			@Override
//			public Object visit(OWLDataPropertyAssertionAxiom axiom) {
//				System.out.println("*****");
//				System.out.println("OWLDataPropertyAssertionAxiom");
//				System.out.println(axiom);
//				String key = new ALCRenderer().render(axiom.getSubject());
//				StringBuilder value = new StringBuilder(new ALCRenderer().render(axiom.getProperty()));
//				value.append("(");
//				value.append(new ALCRenderer().render(axiom.getObject()));
//				value.append(")");
//				
//				return null;
//			}
//
//			/* (non-Javadoc)
//			 * @see org.semanticweb.owlapi.util.OWLObjectVisitorExAdapter#visit(org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom)
//			 */
//			@Override
		public Object visit(OWLObjectPropertyAssertionAxiom axiom) {
			if (!(axiom.toString().contains("UndergraduateStudent0"))){
				return null;
			}
			System.out.println("*****");
			System.out.println("OWLObjectPropertyAssertionAxiom");
			System.out.println(axiom);
			System.out.println();
			return null;
		}
		
			

		};

		// Now ask the walker to walk over the ontology structure using our visitor instance.
		walker.walkStructure(visitor);
	}

}
