package pdf_handler;
import java.awt.Image;
/**
 * @author Artur Tenorio - arturvt@gmail.com
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.RandomAccess;
import java.util.logging.Logger;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

public class PDFReader {
	
	private String fileName;
	private PDFFile pdfFile;
	
	
	// Page proportion on A4 = 0,75
	enum Sizes {
		SMALL(692,910),
		DEFAULT(910, 1213), 
		HIGH(1213, 1617);
		
		private int width;
		private int height;
		
		Sizes(int width, int heigth) {
			this.width = width;
			this.height = heigth;
		}
		
		public int w() {
			return width;
		}
		public int h() {
			return height;
		}
	}
	
	public PDFReader(String fileName) throws FileNotFoundException {
		
		File file  = new File(fileName);
		if (!file.isFile()) {
			throw new FileNotFoundException("Please verify if the file exists.");
		}
		this.fileName = fileName;
		
	    Logger.getLogger("PDFReader").info(()->"Reading file: " + this.fileName);
	    initPDFFile(file);
		
	}
	
	private PDFFile readPDFFile(File file) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		FileChannel channel = raf.getChannel();
		ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
		raf.close();
		return new PDFFile(buf);
	}
 	
	private void initPDFFile(File file) {
		try {
			this.pdfFile = readPDFFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getNumberOFPages() {
		return this.pdfFile.getNumPages();
	}
	
////	public Image getPageAsImage(int page) {
//		return this.pdfFile.getPage(page).getImage(Sizes.DEFAULT.w(), Sizes.DEFAULT.h(), clip, observer);
//		
//	}
	
	public void printPageInfo(int i) {
		PDFPage page = this.pdfFile.getPage(i);
		Logger.getLogger("PDFReader").info(()->"Page Width: " + page.getWidth());
		Logger.getLogger("PDFReader").info(()->"Page Height : " + page.getHeight());
		Logger.getLogger("PDFReader").info(()->"Page Aspect : " + page.getAspectRatio());
//		page.getImage(width, height, clip, observer);
//		page.getImage(width, height, clip, observer, drawbg, wait)
	}

}
