package br.com.pdf_reader.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

public class PDFHandler {
	
	private final static Logger LOGGER = Logger.getLogger(PDFHandler.class.getName());

	private PDDocumentInformation pdfInfo;
	private PDDocument pdfDoc;
	private PDPageTree pdPages;
	private String password = "";

	private static final int ENCRYPT_LENGTH = 128;

	public PDFHandler(String fileName) throws FileNotFoundException {
		initFile(fileName);
	}
	
	public PDFHandler(String filename, String password) throws FileNotFoundException {
		this.password = password;
		initFile(filename);
	}

	private void initFile(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		if (!file.isFile()) {
			throw new FileNotFoundException("Please verify if the file exists.");
		}
		try {
			this.pdfDoc = PDDocument.load(file, password);
			this.pdfInfo = pdfDoc.getDocumentInformation();
			this.pdPages = this.pdfDoc.getDocumentCatalog().getPages();
			LOGGER.info("Total pages loaded -> " + this.pdPages.getCount());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method encrypts a given PDF. An encrypted pdf can't be opened
	 * without a ownerPass or userPass. With a userPass it can be read, but
	 * can't be printed. With ownserPass it can be read and print.
	 */
	public void encryptPDF(String sourceName, String ownerPass, String userPass) {
		AccessPermission ap = new AccessPermission();
		ap.setCanPrint(false);
		ap.setCanExtractContent(false);
		// ap.setReadOnly();
		StandardProtectionPolicy spp = new StandardProtectionPolicy(ownerPass, userPass, ap);
		spp.setEncryptionKeyLength(ENCRYPT_LENGTH);
		spp.setPermissions(ap);
		try {
			this.pdfDoc.protect(spp);
			if (!sourceName.endsWith(".pdf")) {
				sourceName += ".pdf";
			}
			this.pdfDoc.save(sourceName);
			this.pdfDoc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BufferedImage getPageImage(int page, int dpi) throws IOException {
//		for (COSName n: this.pdPages.get(0).getResources().getXObjectNames()) {
//			System.out.println(n);
//			this.pdPages.get(0).getResources().getXObject(n).getCOSStream();
//			
//		}
		
		
		return new PDFRenderer(this.pdfDoc).renderImageWithDPI(page, dpi, ImageType.RGB);
	}

	public boolean isEncrypted() {
		return this.pdfDoc.isEncrypted();
	}

	public void printFileInfo() throws IOException {
		System.out.println("Page Count=" + this.pdfDoc.getNumberOfPages());
		if (!this.pdfDoc.isEncrypted()) {
			System.out.println("Title=" + this.pdfInfo.getTitle());
			System.out.println("Author=" + this.pdfInfo.getAuthor());
			System.out.println("Subject=" + this.pdfInfo.getSubject());
			System.out.println("Keywords=" + this.pdfInfo.getKeywords());
			System.out.println("Creator=" + this.pdfInfo.getCreator());
			System.out.println("Producer=" + this.pdfInfo.getProducer());
			System.out.println("Creation Date=" + this.pdfInfo.getCreationDate());
			System.out.println("Modification Date=" + this.pdfInfo.getModificationDate());
			System.out.println("Trapped=" + this.pdfInfo.getTrapped());
		} else {
			System.out.println("PDF is encrypted");
		}
	}

	public String getTitle() {
		return this.pdfInfo.getTitle();
	}

	public String getAuthor() {
		return this.pdfInfo.getAuthor();
	}

	public int getQntPages() {
		return this.pdfDoc.getNumberOfPages();
	}

	public void closeDoc() throws IOException {
		this.pdfDoc.close();
	}
}
