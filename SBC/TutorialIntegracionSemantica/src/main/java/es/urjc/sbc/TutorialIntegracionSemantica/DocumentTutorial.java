package es.urjc.sbc.TutorialIntegracionSemantica;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.input.DOMBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class DocumentTutorial {
	public void readDOC(String filename) {
		FileInputStream is;
		System.out.println();
		try {
			is = new FileInputStream(new File(filename));
			HWPFDocument doc = new HWPFDocument(is);
			System.out.println(doc.getText());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readDOCX(String filename) {
		FileInputStream is;
		System.out.println();
		try {
			is = new FileInputStream(new File(filename));
			XWPFDocument docx = new XWPFDocument(is);
			XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
			System.out.println(extractor.getText());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readXLS(String filename) {
		FileInputStream is;
		System.out.println();
		try {
			is = new FileInputStream(new File(filename));
			HSSFWorkbook xls = new HSSFWorkbook(is);
			System.out.println("Numero de paginas: " + xls.getNumberOfSheets());

			for (int x = 0; x < xls.getNumberOfSheets(); x++) {
				HSSFSheet sheet = xls.getSheetAt(x);
				for (int y = sheet.getFirstRowNum(); y < 10; y++) {
					HSSFRow row = sheet.getRow(y);
					if (row != null) {
						for (int z = row.getFirstCellNum(); z < row.getLastCellNum(); z++) {
							HSSFCell cell = row.getCell(z);
							if (cell != null && cell.getCellType() != CellType.BLANK) {
								System.out.print(cell + " ");
							}
						}
						System.out.println();
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void convertXLS2XLSX(String xlsFilename, String xlsxFilename) {
		FileInputStream is;
		BufferedOutputStream os;
		try {
			is = new FileInputStream(new File(xlsFilename));

			HSSFWorkbook xls = new HSSFWorkbook(is);
			XSSFWorkbook xlsx = new XSSFWorkbook();

			for (int x = 0; x < xls.getNumberOfSheets(); x++) {
				HSSFSheet xlsSheet = xls.getSheetAt(x);
				XSSFSheet xlsxSheet = xlsx.createSheet(xlsSheet.getSheetName());

				Iterator<Row> rowIt = xlsSheet.rowIterator();
				while (rowIt.hasNext()) {
					Row xlsSheetRow = rowIt.next();
					Row xlsxSheetRow = xlsxSheet.createRow(xlsSheetRow.getRowNum());

					xlsxSheetRow.setRowNum(xlsSheetRow.getRowNum());
					xlsxSheetRow.setRowNum(xlsSheetRow.getRowNum());
					xlsxSheetRow.setHeight(xlsSheetRow.getHeight());
					xlsxSheetRow.setHeightInPoints(xlsSheetRow.getHeightInPoints());
					xlsxSheetRow.setZeroHeight(xlsSheetRow.getZeroHeight());

					Iterator<Cell> cellIt = xlsSheetRow.cellIterator();
					while (cellIt.hasNext()) {
						Cell xlsRowCell = cellIt.next();
						Cell xlsxRowCell = xlsxSheetRow.createCell(xlsRowCell.getColumnIndex(),
								xlsRowCell.getCellType());

						xlsxRowCell.getSheet().setColumnWidth(xlsxRowCell.getColumnIndex(),
								xlsRowCell.getSheet().getColumnWidth(xlsRowCell.getColumnIndex()));

						switch (xlsRowCell.getCellType()) {
						case _NONE:
							break;
						case BLANK:
							xlsxRowCell.setBlank();
							break;
						case ERROR:
							xlsxRowCell.setCellErrorValue(xlsRowCell.getErrorCellValue());
							break;
						case BOOLEAN:
							xlsxRowCell.setCellValue(xlsRowCell.getBooleanCellValue());
							break;
						case FORMULA:
							xlsxRowCell.setCellFormula(xlsRowCell.getCellFormula());
							break;
						case NUMERIC:
							xlsxRowCell.setCellValue(xlsRowCell.getNumericCellValue());
							break;
						case STRING:
							xlsxRowCell.setCellValue(xlsRowCell.getStringCellValue());
						default:
							break;
						}
					}

				}
			}
			os = new BufferedOutputStream(new FileOutputStream(new File(xlsxFilename)));
			xlsx.write(os);

			xls.close();
			xlsx.close();
			is.close();
			os.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readXLSX(String filename) {
		FileInputStream is;
		System.out.println();
		try {
			is = new FileInputStream(new File(filename));
			XSSFWorkbook xlsx = new XSSFWorkbook(is);
			System.out.println("Numero de paginas: " + xlsx.getNumberOfSheets());

			for (int x = 0; x < xlsx.getNumberOfSheets(); x++) {
				XSSFSheet sheet = xlsx.getSheetAt(x);
				for (int y = sheet.getFirstRowNum(); y < 10; y++) {
					XSSFRow row = sheet.getRow(y);
					if (row != null) {
						for (int z = row.getFirstCellNum(); z < row.getLastCellNum(); z++) {
							XSSFCell cell = row.getCell(z);
							if (cell != null) {
								System.out.print(cell + " ");
							}
						}
						System.out.println();
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readPDF(String filename) {
		try {
			PDDocument doc = PDDocument.load(new File(filename));
			PDFTextStripper stripper = new PDFTextStripper();
			System.out.println(stripper.getText(doc));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readXML(String filename) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			org.w3c.dom.Document w3Doc = builder.parse(new File(filename));
			org.jdom2.Document jdomDoc = new DOMBuilder().build(w3Doc);

			List<Element> elements = jdomDoc.getRootElement().getChildren();
			for (Element element : elements) {
				System.out.println(element.getName() + " " + element.getValue());
			}
//			new XMLOutputter().output(elements.getChild("dorsal"), System.out);			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readXMLWithXPath(String filename) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			org.w3c.dom.Document w3Doc = builder.parse(new File(filename));
			org.jdom2.Document jdomDoc = new DOMBuilder().build(w3Doc);

			XPathFactory xPathFactory = XPathFactory.instance();
			XPathExpression<Element> exp = xPathFactory.compile("//player", Filters.element());

			List<Element> ev = exp.evaluate(jdomDoc);
			for (Element element : ev) {
				List<Element> children = element.getChildren();
				for (Element child : children) {
					System.out.println(child.getName() + " " + child.getValue());
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getWebDocumentTable(String url, int colNum) {
		Connection con = Jsoup.connect(url);
		try {
			org.jsoup.nodes.Document doc = con.get();
			System.out.println(doc.title());
			Elements elements = doc.select("table tbody tr");
			for (org.jsoup.nodes.Element tr : elements) {
				Elements cols = tr.select("td");
				int i = 1;
				for (org.jsoup.nodes.Element col : cols) {
					if (i == colNum) {
						System.out.println(col.text());
					}
					i++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readJSON(String filename) {
		Gson gson = new Gson();
		try {
			JsonObject json = gson.fromJson(new FileReader(new File(filename)), JsonObject.class);
			System.out.println(gson.toJson(json));
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
