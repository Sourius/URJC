package es.urjc.sbc.OWLAPITutorial;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLDatatype;

public class App {
	public static void main(String[] args) {
		String url = "http://sbc2019.es/onto";
		String filename = "./ontología.owl";
		String className = "Animal";

		BlankOntology ontologia = new BlankOntology(url);
		ontologia.generateOntology(filename);
		ontologia.generateClass(className);
		ontologia.generateSubClass("Dog", "Animal");
		ontologia.addObjectProperty("eat", "Dog", "Animal");
		
		OWLDatatype type = OWLManager.getOWLDataFactory().getIntegerOWLDatatype();
		ontologia.addDataProperty("age", type);
		
		ontologia.addIndividual("Lassie", "Dog");
	}
}
