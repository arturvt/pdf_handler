package pdf_handler;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class BaseTest {
	
	protected static PDFHandler handler;
	
	public static String getFileFromResource(String fileName) {
		URL url = ClassLoader.getSystemResource(fileName);
		return url.getPath();
	}
	
	public String getResourceDir() {
		return ClassLoader.getSystemResource("").toString();
	}
	
	@BeforeClass
	public static void prepareTest() {
		try {
			handler = new PDFHandler(getFileFromResource("pdf_original.pdf"));
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		}
	}
	
	@AfterClass
	public static void tearDown() throws IOException {
		handler.closeDoc();
	}

}
