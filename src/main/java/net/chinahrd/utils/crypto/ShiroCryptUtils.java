package net.chinahrd.utils.crypto;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 
 * @author jxzhang on 2017年7月25日
 * @Verdion 1.0 版本
 */
public class ShiroCryptUtils {

	/**
	 * shiro封装base64加密
	 * 
	 * @param password
	 * @return
	 */
	public static String encBase64(String password) {
		return Base64.encodeToString(password.getBytes());
	}

	/**
	 * shiro封装base64解密
	 * 
	 * @param password
	 * @return
	 */
	public static String decBase64(String password) {
		return Base64.decodeToString(password);
	}

	/**
	 * shiro封装md5解密
	 * 
	 * @param password
	 * @param salt
	 *            盐
	 * @return
	 */
	public static String md5(String password, String salt) {
		return new Md5Hash(password, salt).toString();
	}
	
	public static void main(String[] args) {
		String md5 = ShiroCryptUtils.md5("sa123456", "hrbi");
		System.out.println(md5);
	}

}
