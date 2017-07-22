/**
*net.chinahrd.biz.paper.mobile.util
*/
package net.chinahrd.utils;

/**
 * 编码转换  加密解密
 * @author htpeng
 *2016年6月16日下午3:13:39
 */
public class Transcode {
	
	private static String decode(String content){//将16进制数转换为汉字
		
		 String enUnicode=null;
		  String deUnicode=null;
		  for(int i=0;i<content.length();i++){
		      if(enUnicode==null){
		       enUnicode=String.valueOf(content.charAt(i));
		      }else{
		       enUnicode=enUnicode+content.charAt(i);
		      }
		      if(i%4==3){
		       if(enUnicode!=null){
		        if(deUnicode==null){
		         deUnicode=String.valueOf((char)Integer.valueOf(enUnicode, 16).intValue());
		        }else{
		         deUnicode=deUnicode+String.valueOf((char)Integer.valueOf(enUnicode, 16).intValue());
		        }
		       }
		       enUnicode=null;
		      }
		      
		     }
		  return deUnicode;
	}
	
	/**
	 * 将16进制数转换为汉字
	 * @param content
	 * @return
	 */
	public static String toStringHex(String content){
		StringBuffer sb=new StringBuffer();
		  for(String str:content.split(",")){
			  int len=4-str.length();
			  String add="";
			  while(len>0){
				  add+="0";
				  len--;
			  }
			  sb.append(decode(add+str));
		  }
		  
		  return sb.toString();
	}
}
