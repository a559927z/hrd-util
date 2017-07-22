/**
*net.chinahrd.utils
*/
package net.chinahrd.utils;

/**
 * 基础工具类
 * @author htpeng
 *2016年9月23日下午4:58:30
 */
public class Util {
	/**
	 * 元转万元
	 * @param yuan
	 * @return
	 */
	public double yuanToMillion(double yuan){
		return yuan/10000;
	}
	public double yuanToMillion(String yuan ){
		try {
			return yuanToMillion(Double.parseDouble(yuan));
		} catch (NumberFormatException e) {
			throw new NumberFormatException("金额错误!");
		}
		
	}
	/**
	 * 万元转元
	 * @param yuan
	 * @return
	 */
	public double millionToYuan(double million){
		return million*10000;
	}
	public double millionToYuan(String yuan ){
		try {
			return millionToYuan(Double.parseDouble(yuan));
		} catch (NumberFormatException e) {
			throw new NumberFormatException("金额错误!");
		}
		
	}

    public static void main(String... arg){
    	System.out.println(Identities.uuid2());
    	System.out.println(Identities.uuid2());
    	System.out.println(Identities.uuid2());
    }
}
