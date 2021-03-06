package pdf_handler;

import static org.junit.Assert.*;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class ImagesHandler extends BaseTest{
	
	@Test
	public void generateDefaultSize() throws IOException {
		BufferedImage img = handler.getPageImage(4, 160);
		File target = new File("img_04.png");
		ImageIO.scanForPlugins();
		ImageIO.write(img, "png", target);
	}

	@Test
	public void testGetFirstPage() throws IOException {
		assertNotNull(this.handler.getPageImage(0, 96));
	}

}
