package pdf_handler;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Test;

public class PDFReadertest {

	private static PDFReader reader;
	
	public static String getFileFromResource(String fileName) {
		URL url = ClassLoader.getSystemResource(fileName);
		return url.getPath();
	}
	
	@BeforeClass
	public static void prepareTest() {
		try {
			reader = new PDFReader(getFileFromResource("pdf_file.pdf"));
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetNumberOfPages() {
		Integer result = reader.getNumberOFPages();
		System.out.println(result);
	}
	
	@Test
	public void testGetPageInfo() {
		reader.printPageInfo(1);
		reader.printPageInfo(2);
		reader.printPageInfo(262);
		
	}

}
