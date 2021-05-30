package es.urjc.sbc.practica_integracion_semantica.document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class DocumentWriter {
	private XWPFDocument doc;
	private FileOutputStream out;
	
	public DocumentWriter(String filename){
		File file = new File(filename);
		try {
			if (!file.exists()) {
				// create file
				file.createNewFile();
			}
			out = new FileOutputStream(file);
			doc = new XWPFDocument();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	// close file
	public void closeFile() {
		try {
			doc.close();
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	// write data in the document
	public void write(String s) {
		// "\n<p>\n" has been added between each paragraph and section extracted from wikipedia to seperate them
		String[] aux = s.split("\n<p>\n");
		// add each paragraph or section
		for(String par: aux) {
			XWPFParagraph paragraph = doc.createParagraph();
			XWPFRun run = paragraph.createRun();
			run.setText(par);
			try {
				doc.write(out);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}	
	
}
