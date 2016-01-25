package pdf_handler;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.pdf_reader.core.PDFHandler;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PDFHandlerEncryptTest extends BaseTest {

	static String sourceName = "temp_encrypted.pdf";
	static String ownerPass = "gemt2015";
	static String userPass = "gemt2015";
	
//	@Test
//	public void test01Encrypt() throws IOException {	
//		handler.encryptPDF(sourceName, ownerPass, userPass);
//		File file = new File(sourceName);
//		assertTrue(file.isFile());
//	}
//	
//	@Test
//	public void test02openEncrypted() throws IOException {	
//		PDFHandler toDecrypt = new PDFHandler(sourceName, ownerPass);
//		assertTrue(toDecrypt.isEncrypted());
//		assertNotNull(toDecrypt.getAuthor());
//		
//	}
	
	@Test
	public void testEncrypt() throws IOException {
		File d = new File("/Users/atenorio/gemt");
		File encryptedDir = new File("/Users/atenorio/gemt_encrypted");
		
		Collection<File> files = FileUtils.listFiles(
				  d, 
				  new RegexFileFilter("^(.*?)"),  
				  DirectoryFileFilter.DIRECTORY
				);
		 
		int index = 0;
		for (File f:files) {
			if (f.getName().endsWith(".pdf")) {
				index++;
				String target = f.getAbsolutePath().replace("/gemt/", "/gemt_encrypted/");
				File fa = new File(target);
				if (!fa.getParentFile().exists())
					fa.getParentFile().mkdirs();
				fa.createNewFile();
				PDFHandler h = new PDFHandler(f.getAbsolutePath());
				h.encryptPDF(target, ownerPass, userPass);
				h.closeDoc();
				if (index % 5 == 0) {
					System.out.println("Current: " + index);
				}
			}
			
		}
		
		
		
//		
//		for (String f:files) {
//			String subLocation = d.getPath() + File.separator + f;			
//			String[] subDir = new File(subLocation).list(filterDir);
//			for (String sub:subDir) {
//				String subLocDir = d.getPath() + File.separator + sub;
//				System.out.println(subLocDir);
//				String[] fileName = new File(subLocDir).list(filterPDF);
//				for (String fileLocation:fileName) {
//					System.out.println(subLocDir + File.separator + fileLocation);
//				}
//			}
//		}
		
//		for (String f:files) {
//			String location = d.getPath() + File.separator + f;
//			String target = encryptedDir.getPath() + File.separator + f;
//			System.out.println("Location: " + location + ". Target: " + target);
//			PDFHandler h = new PDFHandler(location);
//			System.out.println(String.format("-> %s - %d", h.getTitle(), h.getQntPages()));
//			h.encryptPDF(target, ownerPass, userPass);
//			h.closeDoc();
//		}
		
		
	}
	
	@AfterClass
	public static void tearDown() {
		File file = new File(sourceName);
		if (file.isFile()) {
			file.delete();
		}
	}

}
