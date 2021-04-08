package es.urjc.sbc.ReasonerTutorial;

public class App {

	public static void main(String[] args) {
		Ontology ontology = new Ontology("http://sbc2020.es/reasoner");
		ontology.generateOntology("./ontologia.owl");

		ontology.addClass("Animal");
		ontology.addSubClass("Tigre", "Animal");
		ontology.addSubClass("Vaca", "Animal");
		ontology.addSubClass("Oveja", "Animal");

		ontology.addObjectProperty("come", "Animal", "Animal");
		//ontology.addClass("Carnivoro");
		ontology.addEquivalence("Carnivoro", "come", "Animal");
		ontology.addInclusion("Tigre", "come", "Vaca");

		ReasonerTutorial reasoner = new ReasonerTutorial(ontology);
		reasoner.infer();
		reasoner.generateInferencedOntology("http://sbc2020.es/inf", "./infOntology.owl");
		
	}
}
