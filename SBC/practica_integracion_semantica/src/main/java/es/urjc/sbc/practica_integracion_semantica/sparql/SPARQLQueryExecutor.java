package es.urjc.sbc.practica_integracion_semantica.sparql;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

public class SPARQLQueryExecutor {
	private String service;
	private String defaultService = "http://dbpedia.org/sparql";

	public SPARQLQueryExecutor() {
	}

	public SPARQLQueryExecutor(String service) {
		setService(service);
	}

	public void setService(String service) {
		this.service = service;
	}

	public void executeQuery(String queryString, String service) {
		QueryExecution execution = QueryExecutionFactory.sparqlService(service, queryString);
		ResultSet results = execution.execSelect();
		ResultSetFormatter.out(results);
		System.out.println(results.getRowNumber());
	}

	public void executeQuery(String queryString) {
		if (service == null) {
			executeQuery(queryString, defaultService);
		} else {
			executeQuery(queryString, service);
		}
	}
}
