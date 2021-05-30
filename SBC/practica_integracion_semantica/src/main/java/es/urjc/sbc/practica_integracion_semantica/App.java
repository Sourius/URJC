package es.urjc.sbc.practica_integracion_semantica;

import org.semanticweb.owlapi.model.OWLOntology;

import es.urjc.sbc.practica_integracion_semantica.api.WikiReader;
import es.urjc.sbc.practica_integracion_semantica.api.WildLifeAPIReader;
import es.urjc.sbc.practica_integracion_semantica.document.CSVWriter;
import es.urjc.sbc.practica_integracion_semantica.document.DocumentWriter;
import es.urjc.sbc.practica_integracion_semantica.ontologia.OntologyBuilder;
import es.urjc.sbc.practica_integracion_semantica.ontologia.OntologyLoader;
import es.urjc.sbc.practica_integracion_semantica.ontologia.OntologyReasoner;
import es.urjc.sbc.practica_integracion_semantica.sparql.SPARQLQueryBuilder;
import es.urjc.sbc.practica_integracion_semantica.sparql.SPARQLQueryExecutor;

public class App {
	public static void main(String[] args) {
		String ontologyFilename = "src/main/resources/clasificacion_animales.owl";
		// Loadontology
		OntologyLoader loader = new OntologyLoader();
		OWLOntology onto = loader.loadOntology(ontologyFilename);
		OntologyBuilder builder = new OntologyBuilder(onto);

		// add some prefix
		builder.addPrefix("wiki", "https://es.wikipedia.org/wiki/");
//		builder.addPrefix("end", "http://www.earthsendangered.com/");
//		builder.addPrefix("a-z", "https://a-z-animals.com/animals/");
//		builder.addPrefix("atlas", "https://atlasanimal.com/");
		builder.addPrefix("dbr", "https://dbpedia.org/resource/");

		// get animals from api
		WildLifeAPIReader apiReader = new WildLifeAPIReader();
		apiReader.loadDataFromAPI();

//		SPARQLQueryBuilder queryBuilder = new SPARQLQueryBuilder();
//		queryBuilder.addPrefix("dbr", "https://dbpedia.org/resource/");
//		SPARQLQueryExecutor queryExecutor = new SPARQLQueryExecutor();
		
		for (String group : apiReader.getGroups()) {
			// add group
			String groupName = apiReader.translateGroupName(group);
			builder.addIndividualToClass("dbr:" + apiReader.filterName(group), groupName);
			builder.addData("dbr:" + apiReader.filterName(group), "nombre_grupo", groupName);

			for (String family : apiReader.getFamilies(group)) {
				// add family
				String familyName = apiReader.filterName(family);
				builder.addSubClass("dbr:" + familyName, "Familia");
				builder.addSentence("dbr:" + familyName, "pertenece_grupo", "dbr:" + apiReader.filterName(group));
				builder.addData("dbr:" + familyName, "family_name", familyName);
				builder.addData("dbr:" + familyName, "nombre_grupo", groupName);

				// add species to ontology
				for (String specie : apiReader.getSpecies(family)) {
					// create query
					// execute query
//					queryExecutor.executeQuery(queryBuilder.generateQueryString());

					// add specie
					String specieName = apiReader.filterSpecieName(specie);
					builder.addIndividualToClass("dbr:" + specieName, "Animal");
					builder.addSentence("dbr:" + specieName, "pertenece_familia", "dbr:" + familyName);
					builder.addData("dbr:" + specieName, "nombre_cientifico", specieName);
					builder.addData("dbr:" + specieName, "family_name", familyName);

					if (groupName.equals("Anfibio")) {
						builder.addObjectProperty("come", "dbr:" + specieName, "Animal");
					}
				}
			}
		}
		
		// read data from wiki and write data to csv and docx
		CSVWriter csvWriter = new CSVWriter("src/main/resources/salida.csv");
		WikiReader wiki = new WikiReader();
		DocumentWriter writer = new DocumentWriter("src/main/resources/salida.docx");
		
		for (String group : apiReader.getGroups()) {
			String groupName = apiReader.translateGroupName(group);
			
			for (String family : apiReader.getFamilies(group)) {
				String familyName = apiReader.filterName(family);
				
				for (String specie : apiReader.getSpecies(family)) {
					String specieName = apiReader.filterSpecieName(specie);	
					// read data
					String data = wiki.getDataFromWikipedia(specieName);			
					// write data
					writer.write(data);
					csvWriter.writeData(specieName, familyName, groupName, "https://es.wikipedia.org/wiki/" + specieName);
				}
			}
		}
		
		// close files	
		csvWriter.closeFile();
		writer.closeFile();

		// infer ontology
		OntologyReasoner razonador = new OntologyReasoner(onto);
		razonador.infer();
		builder.saveOntology("src/main/resources/ontologia_inferida.owl");
	}
}
