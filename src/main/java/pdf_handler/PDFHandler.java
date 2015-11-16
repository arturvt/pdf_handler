package pdf_handler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;

public class PDFHandler {

	private String fileName;
	private PDDocumentInformation pdfInfo;
	private PDDocument pdfDoc;
	
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
	
	public BufferedImage getPageInfo(int page, int dpi) throws IOException {
		List<PDPage> pdPages = this.pdfDoc.getDocumentCatalog().getAllPages();
		return  pdPages.get(page).convertToImage(BufferedImage.TYPE_INT_RGB, dpi);		
	}
	
	public void printFileInfo() {
		System.out.println( "Page Count=" + this.pdfDoc.getNumberOfPages() );
		System.out.println( "Title=" + this.pdfInfo.getTitle() );
		System.out.println( "Author=" + this.pdfInfo.getAuthor() );
		System.out.println( "Subject=" + this.pdfInfo.getSubject() );
		System.out.println( "Keywords=" + this.pdfInfo.getKeywords() );
		System.out.println( "Creator=" + this.pdfInfo.getCreator() );
		System.out.println( "Producer=" + this.pdfInfo.getProducer() );
		try {
			System.out.println( "Creation Date=" + this.pdfInfo.getCreationDate() );
			System.out.println( "Modification Date=" + this.pdfInfo.getModificationDate());
		} catch (IOException e) {
			System.out.println( "Could not fetch dates.");
		}
		
		System.out.println( "Trapped=" + this.pdfInfo.getTrapped() );  
	}
	
}
