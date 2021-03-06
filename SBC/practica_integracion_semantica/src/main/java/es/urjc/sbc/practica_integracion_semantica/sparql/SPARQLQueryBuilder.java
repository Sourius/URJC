package es.urjc.sbc.practica_integracion_semantica.sparql;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SPARQLQueryBuilder {
	private Map<String, String> queryPrefixMap;
	private Map<String, String> commonPrefixMap;

	private List<String> selectList;
	private List<String> whereList;
	private List<String> optionList;

	public SPARQLQueryBuilder() {
		queryPrefixMap = new HashMap<>();
		commonPrefixMap = new HashMap<>();
		selectList = new LinkedList<>();
		whereList = new LinkedList<>();
		optionList = new LinkedList<>();
		setCommonPrefix();
	}

	public void addPrefix(String prefix, String url) {
		queryPrefixMap.put(prefix, url);
	}

	public void addCommonPrefix(String prefix) {
		queryPrefixMap.put(prefix, commonPrefixMap.get(prefix));
	}

	private void setCommonPrefix() {
		String owlPrefix = "http://www.w3.org/2002/07/owl#";
		String xsdPrefix = "http://www.w3.org/2001/XMLSchema#";
		String rdfsPrefix = "http://www.w3.org/2000/01/rdf-schema#";
		String rdfPrefix = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
		String foafPrefix = "http://xmlns.com/foaf/0.1/";
		String dcPrefix = "http://purl.org/dc/elements/1.1/";
		String resourcePrefix = "http://dbpedia.org/resource/";
		String db2Prefix = "PREFIX dbpedia2: <http://dbpedia.org/property/";
		String dbPrefix = "PREFIX dbpedia: <http://dbpedia.org/";
		String skosPrefix = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#";

		commonPrefixMap.put("owl", owlPrefix);
		commonPrefixMap.put("xsd", xsdPrefix);
		commonPrefixMap.put("rdfs", rdfsPrefix);
		commonPrefixMap.put("rdf", rdfPrefix);
		commonPrefixMap.put("foaf", foafPrefix);
		commonPrefixMap.put("dc", dcPrefix);
		commonPrefixMap.put(" ", resourcePrefix);
		commonPrefixMap.put("db2", db2Prefix);
		commonPrefixMap.put("db", dbPrefix);
		commonPrefixMap.put("skos", skosPrefix);
	}

	// returns the prefix list in query format
	private String generatePrefixString() {
		StringBuilder sb = new StringBuilder();

		for (String prefix : queryPrefixMap.keySet()) {
			String url = queryPrefixMap.get(prefix);
			sb.append("PREFIX " + prefix + ": <" + url + ">\n");
		}
		return sb.toString();
	}

	// add variable to select
	public void addToSelect(String variable) {
		selectList.add(variable);
	}

	// clear select variables
	public void clearSelect() {
		selectList.clear();
	}

	// return select string in query format
	private String generateSelectString() {
		String res = "SELECT";
		for (String s : selectList) {
			res += " ?" + s.toUpperCase();
		}
		res += "\n";
		return res;
	}

	// add query to where
	public void addToWhere(String condition) {
		whereList.add(condition);
	}

	public void addToWhere(String subject, String prefix, String property) {
		StringBuilder sb = new StringBuilder();
		sb.append("?" + subject.toUpperCase());
		sb.append(" " + prefix + ":" + property);
		sb.append(" ?" + property.toUpperCase());
		whereList.add(sb.toString());
	}

	public void addToWhere(String variable, String prefix, String property, String name) {
		StringBuilder sb = new StringBuilder();
		sb.append("?" + variable.toUpperCase());
		sb.append(" " + prefix + ":" + property);
		sb.append(" ?" + name.toUpperCase());
		whereList.add(sb.toString());
	}

	public void addToWhere(String variable, String prefix, String property, String objectPrefix, String object) {
		StringBuilder sb = new StringBuilder();
		sb.append("?" + variable.toUpperCase());
		sb.append(" " + prefix + ":" + property);
		sb.append(" " + objectPrefix + ":" + object + ".");
		whereList.add(sb.toString());
	}

	// clear where list
	public void clearWhere() {
		whereList.clear();
	}

	// add filters
	public void addFilterWithBrackets(String filtername, String subject, String prefix, String property) {
		whereList.add(filtername.toUpperCase() + "(");
		addToWhere(subject, prefix, property);
		whereList.add(")");
	}

	public void addFilterWithBrackets(String filtername, String subject, String prefix, String property, String name) {
		whereList.add(filtername.toUpperCase() + "(");
		addToWhere(subject, prefix, property, name);
		whereList.add(")");
	}

	public void addFilterWithKeys(String filtername, String subject, String prefix, String property) {
		whereList.add(filtername.toUpperCase() + "{");
		addToWhere(subject, prefix, property);
		whereList.add("}");
	}

	public void addFilterWithKeys(String filtername, String subject, String prefix, String property, String name) {
		whereList.add(filtername.toUpperCase() + "{");
		addToWhere(subject, prefix, property, name);
		whereList.add("}");
	}

	// return where in query format with all the queries/conditions and filters
	private String generateWhereClause() {
		StringBuilder sb = new StringBuilder();
		sb.append("WHERE{");
		for (String condition : whereList) {
			sb.append("\n" + condition);
		}
		sb.append("\n}");
		return sb.toString();
	}

	// add option to query
	public void addOptions(String option) {
		optionList.add(option.toUpperCase());
	}

	// clear options
	public void clearOptions() {
		optionList.clear();
	}

	// return options in query format
	private String generateOptionsString() {
		String res = optionList.isEmpty() ? "" : "\n";
		for (String s : optionList) {
			s = s.toUpperCase();
			res += " " + s;
		}
		return res;
	}

	// return query in sparql format
	public String generateQueryString() {
		StringBuilder sb = new StringBuilder();
		sb.append(generatePrefixString());
		sb.append(generateSelectString());
		sb.append(generateWhereClause());
		sb.append(generateOptionsString());
		return sb.toString();
	}

}
