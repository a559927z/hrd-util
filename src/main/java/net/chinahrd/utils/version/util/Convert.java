package net.chinahrd.utils.version.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * AD
 * @author pht
 *
 */
public class Convert {
	public static final int dowsize=65533;
	/**
	 * ���Ƶ����Ĵ�С
	 * @param downSize
	 * @return
	 */
	public static int checkDownSize(String downSize) {
		int len=0;
		if(downSize.trim().length()>0){
			len=integer(downSize);
		}
		if(len>dowsize){
			len=dowsize;
		}
		return len;
	}
	/**
	 * ���Ƶ����Ĵ�С
	 * @param downSize
	 * @return
	 */
	public static int checkDownSize(int downSize) {
		if(downSize>dowsize){
			downSize=dowsize+1;
		}
		return downSize;
	}
	
	
	 /**
	  * ȡ����
	  * @param str
	  * @return
	  */
	public static Integer integer(Object str){ 
		if(str==null|| "null".equals(str.toString()) || "".equals(str.toString())){
			return 0;
		}
		double number=filterDouble(str);
		DecimalFormat df = new DecimalFormat("0");
		return  Integer.parseInt(df.format(number));
	}
	public static long tolong(Object str){ 
		if(str==null|| "null".equals(str.toString()) || "".equals(str.toString())){
			return 0;
		}
		double number=filterDouble(str);
		DecimalFormat df = new DecimalFormat("0");
		return  Long.parseLong(df.format(number));
	}
	public static Double filterDouble(String str) {
		Double value = new Double(0);
		if (str==null||"null".equals(str)||"".equals(str)) {
			return value;
		} else {
			str = str.trim();
			try {
				value = Double.parseDouble(str);
			} catch (Exception e) {
				e.printStackTrace();
				return new Double(0);
			}
		}
		return value;
	}
	
	/**
	 * �����ѧ����
	 * @param str
	 * @return
	 */
	public static String fileterDouble(Object str){
		String value="";
		double sta=0.00;
		if(str==null||"null".equals(str.toString())||"".equals(str.toString())){
			return "0.00";
		}
		sta=Double.valueOf(str.toString()).doubleValue();
		DecimalFormat df = new DecimalFormat("0.00");
		if(sta==0){
			return "0.00";
		}else {
			try{
				value=df.format(sta);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(filterDouble(value)==0){value="0.00";}
		return (value);
	}
	
	public static Float filterFloat(Object obj) {
		Float value = new Float(0);
		if (obj==null) {
			return value;
		} else {
			String str = obj.toString();
			str = str.trim();

			if ("".equals(str)||"null".equalsIgnoreCase(str)) {
				return value;
			}
			
			try {
				value = Float.valueOf(str);
			} catch (Exception e) {
//				e.printStackTrace();
				return new Float(0);
			}
		}
		return value;
	}
	
	public static Double filterDouble(Object obj) {
		Double value = new Double(0);
		if (obj==null) {
			return value;
		} else {
			String str = obj.toString();
			str = str.trim();

			if ("".equals(str)||"null".equalsIgnoreCase(str)) {
				return value;
			}
			
			try {
				value = Double.valueOf(str);
			} catch (Exception e) {
//				e.printStackTrace();
				return new Double(0);
			}
		}
		return value;
	}
	
	public static String filter(String str) {
		if (str==null || "null".equals(str.trim()) || "NULL".equals(str.trim())) {
			return "";
		} else {
			str = str.trim();
			return str;
		}
	}
	/**
	 * ��֤�Ƿ�Ϊ�գ�ת��String
	 * @param obj
	 * @return
	 */
	public static String filter(Object obj) {
		if (obj==null) {
			return "";
		} else {
			String str = obj.toString();
			str = str.trim();
			return str;
		}
	}
	
	
	
	
	
	
	/**
     * ���㵥λ---ͬ������
     * @param String str1,String tr2
     * @return String
     */
	public static String RmbDw_Tbzj(String str1,String str2,String rmbdw){
		if("".equals(str2) || str2 == null){
			str2="0";
		}
		return calTB(Rmb(str1,rmbdw), Rmb(str2,rmbdw));
	}
	
	/**
     * ���㵥λ---����
     * @param String str1,String tr2
     * @return String
     */
	public static String RmbDw_Bz(String str1,String str2,String rmbdw){
		return calBZ(Rmb(str1,rmbdw), Rmb(str2,rmbdw));
	}
	
	/**
     * �ж�ֵ�Ƿ�Ϊ0(0.0,0.00,0.00~~~~~~~)�����Ϊ0
     * @param String str1
     * @return str1
     */
	public static String isOne(String str1){
		String m="0.";
		for(int x=0;x<10;x++){
			m+="0";
			if(str1.equals(m)){
				str1="0";
			}
		}
		return str1;
	}
	/**
	 * �ַ����
	 * @param String str1,String str2  String oper   
	 *   operΪ������
	 * @return String
	 * @author �?��
	 */
	public static String calStr(String str1,String str2,String oper){
		double a =filterDouble((isPer(str1)));
		double b =filterDouble((isPer(str2)));
		return calStr(a,b,oper);
	}
	/**
	 * �ַ����
	 * @param String str1,String str2  String oper   
	 *   operΪ������
	 * @return String
	 * @author �?��
	 */
	public static String calStr(Double str1,Double str2,String oper){
		double a =str1;
		double b =str2;
		double c=0;
		
		String mathOper=String.valueOf(oper.charAt(0));
		if(mathOper.equals("+")){
			c =a+b;
		}else if(mathOper.equals("-")){
			 c =a-b;
		}else if(mathOper.equals("*")){
			c =a*b;
		}else if(mathOper.equals("/")){
			if(a==0&&b==0){
				c=0;
			}else if(b==0){
				c=1;
			}else {
				c =a/b;
				if(c-0==0){
					c=0;
				}
			}	
		}
		if(oper.length()==1){
			return  format(c);
		}else{
			return  format(c*100,"0.00");
		}
		
	}

	/**
	 * ����ͬ������
	 * @param String str1,String str2
	 * @return String
	 * @author �?��
	 */
	public static String calTB(String str1,String str2){
		return calStr(calStr(str1,str2,"-"),str2,"/%");
	}
    
	/**
	 * ����˰����ͬ������
	 * @param String str1,String str2
	 * @return String
	 * @author �?��
	 */
	public static String calSFTB(String str1,String str2){
		return calStr(isPer_100(str1),isPer_100(str2),"-%");
	}
    
	/**
	 * ����˰��
	 * @param String str1,String str2
	 * @return String
	 * @author �?��
	 */
	public static String calSF(String str1,String str2){
		return calBZ(str1,str2);
	}

	/**
	 * �������
	 * @param String str1,String str2
	 * @return String
	 * @author �?��
	 */
	public static String calBZ(String str1,String str2){
		return calStr(str1,str2,"/%");
	}
	

	/**
	 * ������ݿ��ȡ������Ƿ�Ϊ0 ���
	 * @param Object obj
	 * @return String
	 * @author �?��
	 */
	public static String getStr(Object obj){
		return getStr(obj,"");
	}
	
	
	public static String getStr(Object obj,String defual){
		String result=defual;
		if(null!=obj && !"null".equals(obj)){
			if(!obj.toString().equals("")){
				int len =obj.toString().length();
				int low=len-2;
				if(low<0){
					low=0;
				}
				 Pattern p = Pattern.compile("[0]{"+len+"}||([0].[0]{0,"+low+"})");
				 Matcher m = p.matcher(obj.toString());
				 boolean b = m.matches();
				 if(!b){
					try {
							double d=Double.parseDouble(obj.toString());
							 result=format(d);
					} catch (NumberFormatException e) {
						result=obj.toString().trim();
					}
				 }else{
					 result="0";
				 }
			}
		}
		return result;
	}
	/**
	 * ��ʽ����� 
	 * @param double d
	 * @return String
	 * @author �?��
	 */
	public static String format(double d){
		if(d-0==0){
			return  "0";
		}else{
			return  format(d,"0.00");
		}
	}
	public static String format(String d){
		double num=filterDouble(d);
		return  format(num);
	}
	public static String format(double d,String p){
		DecimalFormat df= new DecimalFormat();
		df.applyPattern(p);
		return  df.format(d);
	}
	/**
     * ������%����ȡ% �����100
     * @param String
     * @return
     */
	public static String isPer(String str){
		
		String result="0";
		str=filter(str);
		try {
			if(str.contains("%")){
				str = str.substring(0, str.length()-1);
			}
			Double.parseDouble(str);
			result=str;
		} catch (NumberFormatException e) {
		}
		return result;
	}
	/**
     * ������%����ȡ% �����100
     * @param String
     * @return
     */
	public static String isPer_100(String str){
		
		String result="0";
		str=filter(str);
		try {
			if(str.contains("%")){
				str = str.substring(0, str.length()-1);
			}
			double b=Double.parseDouble(str);
			b=b/100;
			result=String.valueOf(b);
		} catch (NumberFormatException e) {
		}
		return result;
	}
	public static Integer filterInteger(Object obj) {
		return integer(obj);
	}
	public static long filterLog(Object obj) {
		return tolong(obj);
	}
	/**
     * String��������ҵ�λ(String)
     * @param String str1,String tr2
     * @return String
     */
	public static String Rmb(String str1,String str2){
		str1 = getObj(str1);
		if("undefined".equals(str1) || "null".equals(str1) || str1==null || "0".equals(getStr(str1)) || "".equals(getStr(str1))){
			return str1;
		}
		if("undefined".equals(str2) || "null".equals(str2) || str2==null || "0".equals(getStr(str2)) || "".equals(getStr(str2))){
			return str1;
		}
		return String.valueOf(format(Double.parseDouble(str1)/Double.parseDouble(str2)));
	}
	/**
     * String��������ҵ�λ(String)
     * @param String str1,String tr2
     * @return String
     */
	public static String Rmb(Object str1,String str2){
		str1 = getObj(str1);
		if("undefined".equals(str1) || "null".equals(str1) || str1==null || "0".equals(getStr(str1)) || "".equals(getStr(str1))){
			return (String)str1;
		}
		if("undefined".equals(str2) || "null".equals(str2) || str2==null || "0".equals(getStr(str2)) || "".equals(getStr(str2))){
			return (String)str1;
		}
		return String.valueOf(format(Double.parseDouble((String)str1)/Double.parseDouble(str2)));
	}
	
	/**
	 * ��ֵ��װ��json����ҳ���ȡobj
	 * @param Object obj
	 * @return String
	 * @author ����
	 */
	public static String getObj(Object obj){
		return  fileterDouble(filter(obj));
	}
	/**
	 * ��ò�ͬ��λ������ҵ�ֵ
	 * @param Object obj,String rmbdw
	 * @return String
	 * @author �?��
	 */
	public static String getRmb(Object obj,String rmbdw){
		return calStr(Convert.getStr(obj,"0"), rmbdw, "/");
	}
	
	public static String getRmb(String value,String rmbdw){
		return calStr(value, rmbdw, "/");
	}
	

	 /**
	  * 
	  * @param str
	  * @return
	  */
	public static String lendouble(Object str){ 
		if(str==null|| "null".equals(str.toString()) || "".equals(str.toString())){
			return "";
		}
		double number=Convert.filterDouble(str);
		DecimalFormat df = new DecimalFormat("0.00");
		return  df.format(number);
	}

	/**
	 * ת���ɰٷֱ�
	 * @param d
	 * @return
	 */
	public static String getPercent(double d){
		return format(d,"##.##%");
	}
/**
 * �ж��Ƿ�Ϊ����
 * @param value
 * @return
 */
	public static boolean isFs(String value){
		boolean isYes=false;
		if(filter(value).indexOf("-")==0){
			isYes= true;
		}
		return  isYes;
		
	}
//	public static void main(String []adfs){
//		System.out.println(format(0.566,"##.##%"));
//	}

}
