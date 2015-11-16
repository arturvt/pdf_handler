package pdf_handler;

import static org.junit.Assert.*;

import java.awt.FlowLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.junit.BeforeClass;
import org.junit.Test;

public class PDFHandlerTest {
	
	private static PDFHandler handler;
	
	public static String getFileFromResource(String fileName) {
		URL url = ClassLoader.getSystemResource(fileName);
		return url.getPath();
	}
	
	@BeforeClass
	public static void prepareTest() {
		try {
			handler = new PDFHandler(getFileFromResource("pdf_file.pdf"));
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void test() {
		handler.printFileInfo();
	}
	
	@Test
	public void displayImage() {
		JFrame frame = new JFrame("page");
		frame.setSize(1280, 720);
		frame.getContentPane().setLayout(new FlowLayout());
		try {
//			frame.add(new JLabel(new ImageIcon(handler.getPageInfo(1, 300))));
//			frame.add(new JLabel(new ImageIcon(handler.getPageInfo(3, 200))));
			frame.add(new JLabel(new ImageIcon(handler.getPageInfo(4, 100))));
//			frame.add(new JLabel(new ImageIcon(handler.getPageInfo(5, 500))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.setVisible(true);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
