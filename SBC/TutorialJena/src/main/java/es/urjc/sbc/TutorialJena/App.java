package es.urjc.sbc.TutorialJena;

public class App {
    public static void main( String[] args ){
    	JenaTutorial jena = new JenaTutorial();
    	    	
    	String yago = "http://dbpedia.org/class/yago/";
    	QueryGenerator generator = new QueryGenerator();
//    	generator.addCommonPrefix("rdf");
//    	generator.addPrefix("yago", yago);
//    	generator.addToSelect("capital");
//    	generator.addToWhere("capital", "rdf", "type", "yago", "WikicatCapitalsInEurope");
    	
    	String queryString;
    	
//    	queryString = generator.generateQueryString();
//    	System.out.println(queryString);
//        jena.executeQuery(queryString);
//        
//        generator.addCommonPrefix("foaf");
//        generator.addToSelect("name");
//        generator.addToWhere("capital", "foaf", "name");
//        generator.addOptions("order by ?capital");
//        queryString = generator.generateQueryString();
//        System.out.println("\n"+queryString);
//        jena.executeQuery(queryString);
//        
        generator = new QueryGenerator();
        generator.addCommonPrefix("rdf");
    	generator.addPrefix("yago", yago);
    	generator.addToSelect("capital");
    	generator.addToWhere("capital", "rdf", "type", "yago", "WikicatCapitalsInEurope");
    	generator.addCommonPrefix("foaf");
        generator.addToSelect("name");
        generator.addFilterWithKeys("minus","capital","foaf","name");
        generator.addOptions("order by ?capital");
        queryString = generator.generateQueryString();
        System.out.println("\n"+queryString);
        jena.executeQuery(queryString);
    }
}
