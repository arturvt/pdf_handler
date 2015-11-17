package pdf_handler;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.exceptions.CryptographyException;
import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


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
	public void test02Dencrypt() throws IOException, CryptographyException {	
		PDFHandler toDecrypt = new PDFHandler(sourceName);
		assertTrue(toDecrypt.isEncrypted());
		toDecrypt.decrypt(ownerPass);
		assertFalse(toDecrypt.isEncrypted());
		
	}
	
	@AfterClass
	public static void tearDown() {
		File file = new File(sourceName);
		if (file.isFile()) {
			file.delete();
		}
	}

}
