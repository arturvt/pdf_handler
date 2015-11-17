package pdf_handler;

import java.awt.FlowLayout;
import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.junit.Test;

public class PDFHandlerTest extends BaseTest {
	

	@Test
	public void test() throws IOException {
		handler.printFileInfo();
	}
	
	@Test
	public void displayImage() {
		JFrame frame = new JFrame("page");
		frame.setSize(1280, 1000);
		frame.getContentPane().setLayout(new FlowLayout());
		try {
			Image image = handler.getPageInfo(4, 100);
			frame.add(new JLabel(new ImageIcon(image)));
						
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.setVisible(true);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
