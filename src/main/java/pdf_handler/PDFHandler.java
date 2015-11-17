package pdf_handler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.BadSecurityHandlerException;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

public class PDFHandler {

	private String fileName;
	private PDDocumentInformation pdfInfo;
	private PDDocument pdfDoc;
	
	private static final int ENCRYPT_LENGTH = 128;
	
	public PDFHandler(String fileName) throws FileNotFoundException {
		File file  = new File(fileName);
		if (!file.isFile()) {
			throw new FileNotFoundException("Please verify if the file exists.");
		}
		this.fileName = fileName;
		
	    Logger.getLogger("PDFHandler").info(()->"Reading file: " + this.fileName);
	    initFile(file);
	}
	
	private void initFile(File file) {
		try {
			this.pdfDoc = PDDocument.load(file);
			this.pdfInfo = pdfDoc.getDocumentInformation();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method encrypts a given PDF.
	 * An encrypted pdf can't be opened without a ownerPass or userPass.
	 * With a userPass it can be read, but can't be printed.
	 * With ownserPass it can be read and print. 
	 */
	public void encryptPDF(String sourceName, String ownerPass, String userPass) {
		AccessPermission ap = new AccessPermission();
		ap.setCanPrint(false);
		ap.setCanExtractContent(false);
//		ap.setReadOnly();
		StandardProtectionPolicy spp = new StandardProtectionPolicy(ownerPass, userPass, ap);
		spp.setEncryptionKeyLength(ENCRYPT_LENGTH);
		spp.setPermissions(ap);
		try {
			this.pdfDoc.protect(spp);
			if (!sourceName.endsWith(".pdf")) {
				sourceName+=".pdf";
			}
			this.pdfDoc.save(sourceName);
			this.pdfDoc.close();
		} catch (COSVisitorException | IOException e) {
			e.printStackTrace();
		} catch (BadSecurityHandlerException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getPageInfo(int page, int dpi) throws IOException {
		List<PDPage> pdPages = this.pdfDoc.getDocumentCatalog().getAllPages();
		return  pdPages.get(page).convertToImage(BufferedImage.TYPE_INT_RGB, dpi);		
	}
	
	public boolean isEncrypted() {
		return this.pdfDoc.isEncrypted();
	}
	
	
	/**
	 * Decrypt a PDF with a given password
	 * @param password
	 * @throws IOException 
	 * @throws CryptographyException 
	 */
	public void decrypt(String password) throws CryptographyException, IOException {
		this.pdfDoc.decrypt(password);
	}
	
	public void printFileInfo() throws IOException {
		System.out.println( "Page Count=" + this.pdfDoc.getNumberOfPages() );
		if (!this.pdfDoc.isEncrypted()) {
			System.out.println( "Title=" + this.pdfInfo.getTitle() );
			System.out.println( "Author=" + this.pdfInfo.getAuthor() );
			System.out.println( "Subject=" + this.pdfInfo.getSubject() );
			System.out.println( "Keywords=" + this.pdfInfo.getKeywords() );
			System.out.println( "Creator=" + this.pdfInfo.getCreator() );
			System.out.println( "Producer=" + this.pdfInfo.getProducer() );		
			System.out.println( "Creation Date=" + this.pdfInfo.getCreationDate() );
			System.out.println( "Modification Date=" + this.pdfInfo.getModificationDate());
			System.out.println( "Trapped=" + this.pdfInfo.getTrapped() );	
		} else {
			System.out.println("PDF is encrypted");
		}
		  
	}
	
}
