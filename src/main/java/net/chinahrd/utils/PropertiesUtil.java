package net.chinahrd.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * 获取资源文件类 Created by wqcai on 2016/2/1.
 *
 */
public class PropertiesUtil {

	private static final Logger log = Logger.getLogger(PropertiesUtil.class);

	public static Properties pps;

	public static Properties getPro() {
		if (pps == null)
			return pps = new Properties();
		else
			return pps;
	}

	public static String getProperty(String p) {
		return PropertiesUtil.getPro().getProperty(p);
	}

	/**
	 * 
	 * 获取指定Property文件下的值
	 * 
	 * @param propsPath
	 *            指定property位置
	 * @param key
	 *            键
	 * @return 值
	 * @author jxzhang on 2017年04月16日
	 */
	public static String getProperty(String propsPath, String key) {
		String val = "";
		try {
			Properties properties = getProperties(propsPath);
			Object p = properties.get(key);
			if (null == p) {
				log.warn("配置文件中没有定义" + key);
			}
			val = (String) p;
		} catch (IOException e) {
			log.warn("项目根目录下沒有" + propsPath + "目录或文件", e);
		}
		return val;
	}

	/**
	 * 讀取項目中的配置文件
	 * 
	 * @param propsPath
	 *            指定property位置
	 * @return Properties
	 * @throws IOException
	 * @author jxzhang on 2017年04月16日
	 */
	public static Properties getProperties(String propsPath) throws IOException {
		Properties pps = new Properties();
		InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(propsPath);
		pps.load(inputStream);
		return pps;
	}

}
