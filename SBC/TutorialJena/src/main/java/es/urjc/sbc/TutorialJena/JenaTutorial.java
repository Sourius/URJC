package es.urjc.sbc.TutorialJena;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

public class JenaTutorial {
	private String service = "http://dbpedia.org/sparql";

	public JenaTutorial() {
	}

	public void executeQuery(String queryString) {
		QueryExecution execution = QueryExecutionFactory.sparqlService(service, queryString);
		ResultSet results = execution.execSelect();
//		while(results.hasNext()) {
//			QuerySolution qsol = results.next();
//			System.out.println(qsol);
//		}
		ResultSetFormatter.out(results);
		System.out.println(results.getRowNumber());
	}
}
