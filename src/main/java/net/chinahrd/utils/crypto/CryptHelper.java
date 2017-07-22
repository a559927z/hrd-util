package net.chinahrd.utils.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.util.DigestUtils;

public class CryptHelper {

	private static String CRYPT_KEY = "hrbi";

	// 加密
	private static Cipher ecip;
	// 解密
	private static Cipher dcip;

	static {
		try {
			String KEY = DigestUtils.md5DigestAsHex(CRYPT_KEY.getBytes())
					.toUpperCase();
			KEY = KEY.substring(0, 8);
			byte[] bytes = KEY.getBytes();
			DESKeySpec ks = new DESKeySpec(bytes);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			SecretKey sk = skf.generateSecret(ks);
			IvParameterSpec iv2 = new IvParameterSpec(bytes);

			ecip = Cipher.getInstance("DES/CBC/PKCS5Padding");
			ecip.init(Cipher.ENCRYPT_MODE, sk, iv2);

			dcip = Cipher.getInstance("DES/CBC/PKCS5Padding");
			dcip.init(Cipher.DECRYPT_MODE, sk, iv2);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 加密
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String content) throws Exception {
		byte[] bytes = ecip.doFinal(content.getBytes("ascii"));
		return CryptUtils.byte2hex(bytes);
	}

	/**
	 * 解密
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String content) throws Exception {
		byte[] bytes = CryptUtils.hex2byte(content);
		bytes = dcip.doFinal(bytes);
		return new String(bytes, "ascii");
	}
	// test
	public static void main(String[] args) throws Exception {
		String password = "0.16";
		String en = encrypt(password);
		System.out.println(en);
		System.out.println(decrypt(en));
	}
}