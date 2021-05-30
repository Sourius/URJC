package es.urjc.sbc.practica_integracion_semantica.ontologia;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OntologyLoader {
	private OWLOntologyManager manager;

	public OntologyLoader() {
		manager = OWLManager.createOWLOntologyManager();
	}

	public OWLOntology loadOntology(String filename) {
		try {
			return manager.loadOntologyFromOntologyDocument(new File(filename));
		} catch (OWLOntologyCreationException e) {
			throw new RuntimeException(e);
		}
	}
}
