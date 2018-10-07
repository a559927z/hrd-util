package net.chinahrd.utils.export;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.UUID;

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
//		try {
//			// String webappPath = WebUtils.getServletContext().getResource("").toString();
////			String webappPath = WebUtils.getBasePath(WebUtils.getRequest());
//			String webappPath = WebUtils.getAbsolutePath();
//			File webappDir = new File(webappPath);
//			File webappPhantomDir = new File(webappPath + File.separatorChar + "phantomjs");
//
//			// String clazzPath =
//			// PhantomJSExecutor.class.getResource("").getPath();//WebUtils.getCompFileAbsolutePath(PhantomJSExecutor.class);
//			// String clazzPath =
//			// this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
//			String clazzPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().toString();
//			// String clazzPath = this.getClass().getResource;
//			File clazzDir = new File(clazzPath);
//			File clazzPhantomDir = new File(clazzPath + File.separatorChar + "phantomjs");
//
//			StringBuilder cmd = new StringBuilder();
//			// 如果 webapp 目录下有
//			// if (FileUtils.directoryContains(webappDir, webappPhantomDir)) {
//			// cmd.append(webappPath + File.separatorChar + "phantomjs" + File.separatorChar
//			// + "phantomjs.exe")
//			// .append(' ');
//			// cmd.append(webappPath + File.separatorChar + "phantomjs" + File.separatorChar
//			// + "print.js").append(' ');
//			//
//			// } else if (FileUtils.directoryContains(clazzDir, clazzPhantomDir)) {
//			// cmd.append(clazzPath + File.separatorChar + "phantomjs" + File.separatorChar
//			// + "phantomjs.exe")
//			// .append(' ');
//			// cmd.append(clazzPath + File.separatorChar + "phantomjs" + File.separatorChar
//			// + "print.js").append(' ');
//			// } else {
//			// logger.error("no phantomjs directory, may be phantomjs.exe no exsit");
//			// throw new RuntimeException();
//			// }
//
//			// if (checkIsExist(webappPath + File.separatorChar + "phantomjs" +
//			// File.separatorChar + "phantomjs.exe")) {
//			// cmd.append(webappPath + File.separatorChar + "phantomjs" + File.separatorChar
//			// + "phantomjs.exe")
//			// .append(' ');
//			// cmd.append(webappPath + File.separatorChar + "phantomjs" + File.separatorChar
//			// + "print.js").append(' ');
//			// } else if (checkIsExist(
//			// clazzPath + File.separatorChar + "phantomjs" + File.separatorChar +
//			// "phantomjs.exe")) {
//			// cmd.append(clazzPath + File.separatorChar + "phantomjs" + File.separatorChar
//			// + "phantomjs.exe")
//			// .append(' ');
//			// cmd.append(clazzPath + File.separatorChar + "phantomjs" + File.separatorChar
//			// + "print.js").append(' ');
//			// } else {
//			// logger.error("no phantomjs directory, may be phantomjs.exe no exsit");
//			// throw new RuntimeException();
//			// }
//
//			String exeFullPath = webappPath + File.separatorChar + "phantomjs" + File.separatorChar + "phantomjs.exe";
//			String jsFullPath = webappPath + File.separatorChar + "phantomjs" + File.separatorChar + "print.js";
////			String jsFullPath = webappPath + File.separatorChar + "phantomjs" + File.separatorChar + "rasterize.js";
//
//			cmd.append(exeFullPath).append(' ').append(jsFullPath).append(' ');
//			
//			final Process process = Runtime.getRuntime().exec(cmd.toString());
////			System.out.println(cmd.toString());
//			logger.debug("Command to execute:{}", cmd.toString());
//			String output = IOUtils.toString(process.getInputStream());
//			process.waitFor();
//			logger.debug("Output: {}", output);
//			return output;
//		} catch (IOException e1) {
//			e1.printStackTrace();
//			throw new RuntimeException(e1);
//		} catch (InterruptedException e) {
//			throw new RuntimeException(e);
//		} catch (URISyntaxException e) {
//			throw new RuntimeException(e);
//		}

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

	public static boolean checkIsExist(String path) {
		int fileNum = 0, folderNum = 0;
		File file = new File(path);
		if (file.exists()) {
			LinkedList<File> list = new LinkedList<File>();
			File[] files = file.listFiles();
			for (File file2 : files) {
				if (file2.isDirectory()) {
					System.out.println("文件夹:" + file2.getAbsolutePath());
					list.add(file2);
					folderNum++;
				} else {
					System.out.println("文件:" + file2.getAbsolutePath());
					fileNum++;
				}
			}
			File temp_file;
			while (!list.isEmpty()) {
				temp_file = list.removeFirst();
				files = temp_file.listFiles();
				for (File file2 : files) {
					if (file2.isDirectory()) {
						System.out.println("文件夹:" + file2.getAbsolutePath());
						list.add(file2);
						folderNum++;
					} else {
						System.out.println("文件:" + file2.getAbsolutePath());
						fileNum++;
					}
				}
			}
		} else {
			System.out.println("文件不存在!");
			return false;
		}
		System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
		return true;
	}

	public static void main(String[] args) throws IOException {
		String webappPath = "D:/Program Files/apache-tomcat-8.0.45-ide/wtpwebapps/dict-Test/";

		File webappDir = new File(webappPath);
		File webappPhantomDir = new File(webappPath + File.separatorChar + "phantomjs");

		// 如果 webapp 目录下有
		if (FileUtils.directoryContains(webappDir, webappPhantomDir)) {
			System.out.println(112);
		}

	}
}
