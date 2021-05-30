package es.urjc.sbc.practica_integracion_semantica.api;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WikiReader {

	private String baseURL = "https://en.wikipedia.org/wiki/";
	
	/*
	 * get data from wikipedia baseUrl/path and return as string
	 * path is the search term like: Lion
	 */
	public String getDataFromWikipedia(String path) {
		// connect to wikipedia
		Connection con = Jsoup.connect(baseURL+path);
		StringBuilder outputSummary = new StringBuilder();
		StringBuilder outputInfoBox = new StringBuilder();
		try {
			// get data
			org.jsoup.nodes.Document doc = con.get();
			System.out.println(doc.title());
			
			// extract header
			String heading = doc.select("#content #firstHeading").text();
			outputInfoBox.append(heading + "\n\n<p>\n");

			Elements elements = doc.select("#content #bodyContent .mw-parser-output").get(0).children();
			for (Element element : elements) {
				// extract taxonomy
				if (element.hasClass("infobox")) {
					Elements rows = element.select("tbody tr");
					int i = 0;
					for (org.jsoup.nodes.Element inforow : rows) {
						if (i >= 4 && i < rows.size() - 1) {
							String rowheader = inforow.select("th").text();
							String rowinfo = inforow.select("td").text();
							outputInfoBox.append(rowheader + rowinfo + "\n");
						}
						i++;
					}
					outputInfoBox.append("\n<p>\n");
				} else if (element.hasClass("toc")) {
					break;
				} else if (element.hasClass("noprint")) {
					continue;
				} else {
					// extract description
					outputSummary.append(element.text() + "\n\n<p>\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// concat all and return
		return outputInfoBox + "\n" + outputSummary;
	}
}
