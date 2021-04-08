package es.urjc.sbc.ReasonerTutorial;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;

/*
 * Razonamiento con ELK
 */
public class ReasonerTutorial {
	private OWLReasonerFactory factory;
	private OWLReasoner reasoner;
	private InferredOntologyGenerator generator;

	public ReasonerTutorial(Ontology onto) {
		factory = new StructuralReasonerFactory();
		reasoner = factory.createReasoner(onto.getOntology());
		generator = new InferredOntologyGenerator(reasoner);
	}

	public void infer() {		
		reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
	}

	public void generateInferencedOntology(String url, String filename) {
		Ontology infOntology = new Ontology(url);
		infOntology.generateOntology(filename);
		generator.fillOntology(OWLManager.getOWLDataFactory(), infOntology.getOntology());
		infOntology.saveOntology();
	}
}
