package es.urjc.sbc.practica_integracion_semantica.document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CSVWriter {
	private BufferedWriter writer;
	private CSVPrinter csvPrinter;

	public CSVWriter(String filename) {
		File file = new File(filename);
		try {
			if (file.exists()) {
				// create new file
				file.createNewFile();
			}
			// prepare writer
			writer = new BufferedWriter(new FileWriter(file));
			// prepare csvPrinter and add headers
			csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
					.withHeader("Nombre Cientifico", "Familia", "Groupo", "Enlace")
					.withDelimiter(','));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// write data in a row
	public void writeData(String scientific_name, String family, String group, String url) {
		try {
			csvPrinter.printRecord(scientific_name, family, group, url);
			csvPrinter.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// close file
	public void closeFile() {
		try {
			csvPrinter.close();
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
