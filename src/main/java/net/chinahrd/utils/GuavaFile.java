package net.chinahrd.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * @Description:Guava的文件
 * 
 * @ClassName: FileGuava
 * @Copyright: Copyright (c) 2014
 * 
 * @author Comsys-LZP
 * @date 2014-6-26 下午01:18:18
 * @version V2.0
 */
public class GuavaFile {
	public static void main(String[] args) {
		try {
			File readFile = new File(System.getProperty("user.dir") + "/src/resources/showarp.txt");
			StringBuilder content = new StringBuilder();
			if (readFile.exists()) {
				List<String> lines = readFile(readFile);
				for (String string : lines) {
					System.out.println(string);
					content.append(string + "\n");
				}
			}
			File writeFile = new File(System.getProperty("user.dir") + "/src/resources/showarp"
					+ new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".txt");
			writeFile(content.toString(), writeFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description: Guava文件读取
	 * 
	 * @param file
	 * @return
	 * 
	 * @Title: FileGuava.java
	 * @Copyright: Copyright (c) 2014
	 * 
	 * @author Comsys-LZP
	 * @date 2014-6-26 下午01:20:50
	 * @version V2.0
	 */
	public static List<String> readFile(File file) throws Exception {
		if (!file.exists()) {
			return null;
		}
		return Files.readLines(file, Charsets.UTF_8);
	}

	/**
	 * @Description: 从文件中获取有规则的数据
	 * 
	 * @param file
	 * @return
	 * 
	 * @Title: FileGuava.java
	 * @Copyright: Copyright (c) 2014
	 * 
	 * @author Comsys-LZP
	 * @date 2014-6-26 下午01:56:42
	 * @version V2.0
	 */
	public List<String[]> readFileData(File file) throws Exception {
		List<String[]> list = new ArrayList<String[]>();
		for (String rLine : readFile(file)) {
			list.add(rLine.split("\\s+"));
		}
		return list;
	}

	/**
	 * @Description: Guava写文件
	 * 
	 * @param content
	 * @param file
	 * 
	 * @Title: FileGuava.java
	 * @Copyright: Copyright (c) 2014
	 * 
	 * @author Comsys-LZP
	 * @date 2014-6-26 下午01:32:06
	 * @version V2.0
	 */
	public static void writeFile(String content, File file) throws Exception {
		if (!file.exists()) {
			file.createNewFile();
		}
		Files.write(content, file, Charsets.UTF_8);
	}
}
