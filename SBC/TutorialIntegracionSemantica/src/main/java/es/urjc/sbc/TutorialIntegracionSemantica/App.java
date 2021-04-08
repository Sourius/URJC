package es.urjc.sbc.TutorialIntegracionSemantica;

public class App {
	public static void main(String[] args) {
//		DocumentTutorial doc = new DocumentTutorial();
//    	doc.readDOC("holaMundo.doc");
//    	doc.readDOCX("holaMundo.docx");
//    	doc.readXLS("fesfilm-imdb-list.xls");
//    	doc.convertXLS2XLSX("fesfilm-imdb-list.xls", "fesfilm-imdb-list.xlsx");
//    	doc.readXLSX("fesfilm-imdb-list.xlsx");
//    	doc.readPDF("holaMundo.pdf");
//    	doc.readXML("prueba.xml");
//    	doc.readXMLWithXPath("prueba.xml");
//    	doc.getWebDocumentTable("https://www.worldometers.info/geography/alphabetical-list-of-countries/", 2);
//		doc.readJSON("prueba.json");
		SWAPI swapi = new SWAPI();
		swapi.readData();
	}
}
