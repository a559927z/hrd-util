package net.chinahrd.utils.export;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.chinahrd.utils.Identities;
import net.chinahrd.utils.WebUtils;

@Component
public class PhantomJSExecutor {

	private final static Logger logger = LoggerFactory.getLogger(PhantomJSExecutor.class);

	/*** 截图成功后输入 */
	private static final String COMPLETE_OUTPUT = "complete";

	private PhantomJSReference phantomJSReference;

	/**
	 * 截图并返回图片的字节流
	 * 
	 * @param url
	 *            要截图的网页url
	 * @return
	 */
	public byte[] rasterizeAsBytes(String url) {
		PrintArguments args = new PrintArguments();
		args.setUrl(url);
		return rasterizeAsBytes(args);
	}

	/**
	 * 截图并返回图片的字节流
	 * 
	 * @param url
	 *            要截图的网页url
	 */
	public byte[] rasterizeAsBytes(PrintArguments args) {
		if (args.getFilename() == null) {
			// StringBuilder filename = new StringBuilder();
			// filename.append(phantomJSReference.getOutputDir());
			// filename.append(new Date().getTime());
			// filename.append('-');
			// filename.append(RandomStringUtils.randomAlphabetic(10));
			// args.setFilename(filename.toString());

			args.setFilename(WebUtils.getAbsolutePath() + File.separatorChar + Identities.uuid2());
		}
		String output = execute(args.toArgumentsString());
		boolean success = null != output && output.startsWith(COMPLETE_OUTPUT);
		if (false == success && logger.isDebugEnabled()) {
			logger.debug("There may be something wrong when rasterize:'{}'", args.toString());
		}

		File file = new File(args.getFullFilePath());
		byte[] bytes = null;
		try {
			bytes = FileUtils.readFileToByteArray(file);
			file.delete();
		} catch (IOException e) {
			logger.error("Error occurs when rasterize page:{}", args.toString(), e);
		}
		return bytes != null ? bytes : new byte[] {};
	}

	private String execute(String... args) {
		StringBuilder cmd = new StringBuilder();
		cmd.append(WebUtils.getAbsolutePath() + File.separatorChar + "phantomjs" + File.separatorChar + "phantomjs.exe")
				.append(' ');
		cmd.append(WebUtils.getAbsolutePath() + File.separatorChar + "phantomjs" + File.separatorChar + "print.js")
				.append(' ');
		if (args != null) {
			cmd.append(StringUtils.join(args, ' '));
		}
		try {
			final Process process = Runtime.getRuntime().exec(cmd.toString());
			logger.debug("Command to execute:{}", cmd.toString());
			String output = IOUtils.toString(process.getInputStream());
			process.waitFor();
			logger.debug("Output: {}", output);
			return output;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public PhantomJSReference getPhantomJSReference() {
		return phantomJSReference;
	}

	@Autowired
	public void setPhantomJSReference(PhantomJSReference phantomJSReference) {
		this.phantomJSReference = phantomJSReference;
	}

}
