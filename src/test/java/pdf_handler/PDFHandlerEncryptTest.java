package pdf_handler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.pdf_reader.core.PDFHandler;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PDFHandlerEncryptTest extends BaseTest {

	static String sourceName = "temp_encrypted.pdf";
	static String ownerPass = "1111";
	static String userPass = "aaaa";
	
	@Test
	public void test01Encrypt() throws IOException {	
		handler.encryptPDF(sourceName, ownerPass, userPass);
		File file = new File(sourceName);
		assertTrue(file.isFile());
	}
	
	@Test
	public void test02openEncrypted() throws IOException {	
		PDFHandler toDecrypt = new PDFHandler(sourceName, ownerPass);
		assertTrue(toDecrypt.isEncrypted());
		assertNotNull(toDecrypt.getAuthor());
		
	}
	
	@AfterClass
	public static void tearDown() {
		File file = new File(sourceName);
		if (file.isFile()) {
			file.delete();
		}
	}

}
