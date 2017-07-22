package net.chinahrd.utils.export;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.chinahrd.utils.WebUtils;

@Component
public class PhantomJSReference {

	private final static Logger logger = LoggerFactory.getLogger(PhantomJSExecutor.class);

	private static final String MAC_OS = "macosx";

	private static final String LINUX_OS = "linux";

	private static final String WIN_OS = "windows";

	/**
	 * 根路径，当某个路径没有配置时，取 basePath + 默认目录
	 */
	private String basePath;

	/**
	 * phantomJS可执行文件路径
	 */
	private String binaryPath;

	/**
	 * 临时存放生成图片文件的路径
	 */
	private String outputPath;

	/**
	 * 脚本文件存放路径
	 */
	private String scriptPath;
	/**
	 * 远程访问路径
	 */
	private String remotePath;

	public PhantomJSReference() {
	}

	public PhantomJSReference(String basePath) {
		super();
		this.basePath = basePath;
	}

	private String getBasePath() {
		if (null == basePath) {
			basePath = getDefaultBasePath();
		}
		return basePath;
	}

	private String getDefaultBasePath() {
		String fullPath = this.getClass().getResource("").getPath();
		try {
			fullPath = URLDecoder.decode(fullPath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}

		String pathArr[] = fullPath.split("classes");
		String reponsePath = new File(pathArr[0]).getPath();

		StringBuilder path = new StringBuilder();
		path.append(reponsePath).append(File.separatorChar);
		path.append("phantomjs").append(File.separatorChar);
		return path.toString();
	}

	/**
	 * 脚本文件存放路径
	 */
	public String getScriptPath() {
		return scriptPath;
	}

	/**
	 * 临时存放生成图片文件的目录
	 */
	public String getOutputDir() {
		if (StringUtils.isBlank(outputPath)) {
			// outputPath = System.getProperty("java.io.tmpdir");
			outputPath = WebUtils.getAbsolutePath();
		}
		return outputPath;
	}

	/**
	 * phantomJS可执行文件路径
	 */
	public String getBinaryPath() {
		if (null == binaryPath) {
			String folder;
			if (WIN_OS.equals(getHostOs())) {
				folder = WIN_OS;
			} else {
				folder = getHostOs() + "-" + getOsArch();
			}
			String dir = FilenameUtils.concat(getBasePath(), folder);
			binaryPath = dir + File.separatorChar + "phantomjs";
		}
		return binaryPath;
	}

	private String getHostOs() {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win"))
			return WIN_OS;
		else if (os.contains("mac"))
			return MAC_OS;
		else
			return LINUX_OS;
	}

	private String getOsArch() {
		String arch = System.getProperty("os.arch").toLowerCase();
		return arch.indexOf("64") >= 0 ? "x86_64" : "i686";
	}

	@Value("${phantomjs.binary.path}")
	public void setBinaryPath(String binaryPath) {
		if (StringUtils.isBlank(binaryPath)) {
			throw new IllegalArgumentException("Phantomjs binary path is required!");
		}
		this.binaryPath = binaryPath;
	}

	@Value("${phantomjs.script.path}")
	public void setScriptPath(String scriptPath) {
		if (StringUtils.isBlank(scriptPath)) {
			throw new IllegalArgumentException("Phantomjs script path is required!");
		}
		this.scriptPath = scriptPath;
	}

	@Value("${phantomjs.output.dir}")
	public void setOutputPath(String outputPath) {
		if (StringUtils.isBlank(outputPath)) {
			logger.info("Phantomjs script path isn't assigned, will use: {}", getOutputDir());
		}
		this.outputPath = outputPath;
	}

	public String getRemotePath() {
		return remotePath;
	}

	@Value("${phantomjs.remote.path}")
	public void setRemotePath(String remotePath) {
		if (StringUtils.isBlank(remotePath)) {
			throw new IllegalArgumentException("Phantomjs remote path is required!");
		}
		this.remotePath = remotePath;
	}
}
