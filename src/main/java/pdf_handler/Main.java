package pdf_handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
	
	private static final Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		System.out.println("Hey");
		logger.entry();
		logger.trace("Starting app");
		logger.info("aaaa");
		logger.warn("aaa");

	}

}
