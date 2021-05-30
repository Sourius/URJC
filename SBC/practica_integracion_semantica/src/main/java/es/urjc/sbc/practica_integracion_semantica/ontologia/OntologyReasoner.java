package es.urjc.sbc.practica_integracion_semantica.ontologia;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;

public class OntologyReasoner {
	private OWLReasonerFactory factory;
	private OWLReasoner reasoner;
	private InferredOntologyGenerator generator;
	private OWLOntology ontology;

	public OntologyReasoner(OWLOntology onto) {
		ontology = onto;
		factory = new StructuralReasonerFactory();
		reasoner = factory.createReasoner(ontology);
		generator = new InferredOntologyGenerator(reasoner);
	}

	public void infer() {
		reasoner.precomputeInferences(InferenceType.values());
		generator.fillOntology(OWLManager.getOWLDataFactory(), ontology);
	}
}
