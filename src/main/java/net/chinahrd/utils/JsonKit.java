package net.chinahrd.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json工具类
 * @author bright
 * @since 1.0
 */
public abstract class JsonKit {
	private static final Logger log = LoggerFactory.getLogger(JsonKit.class);
	/**
	 * jackson的ObjectMapper对象
	 */
	private static ObjectMapper mapper = new ObjectMapper();
	/**
	 * 将对象转换为json字符串
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		if(object == null) return "";
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			log.warn("write to json string error:" + object, e);
			return "";
		}
	}
	
	/**
	 * 根据指定类型解析json字符串，并返回该类型的对象
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJson(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			return mapper.readValue(jsonString, clazz);
		} catch (Exception e) {
			log.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}
}
