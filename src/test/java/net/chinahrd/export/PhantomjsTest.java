package net.chinahrd.export;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhantomjsTest {

	private final static Logger logger = LoggerFactory.getLogger(PhantomjsTest.class);

	// @Ignore
	@Test
	public void executeTest() {
		String path = "D:\\phantomjs\\phantomjs-2.1.1-windows\\";
		String bin = path + "bin\\phantomjs.exe";
		String js = path + "scripts\\print.js";
//		String url = "https://www.baidu.com";
		String url = "http://localhost:8080/run-vue/demo/display";
		String filename = "D:\\phantomjs\\temp\\expotDemo.pdf";

		String args = "-url " + url + " -filename " + filename + " -pdfSize A4 -timeout 1000";
		String cmd = bin + " " + js + " " + args;
		try {
			final Process process = Runtime.getRuntime().exec(cmd.toString());
			logger.debug("Command to execute:{}", cmd.toString());
			String output = IOUtils.toString(process.getInputStream());
			process.waitFor();
			logger.debug("Output: {}", output);
			// return output;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
