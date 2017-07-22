package net.chinahrd.utils.version.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.dom4j.Attribute;

/**
 * 
 * @author mx__sword
 *
 */
public class Util {
	public final static String GET_METHOD="get";
	public final static String SET_METHOD="set";
	/**
	 *
	 * @param obj
	 * @param att
	 */
	public static void setMethod(Object obj,Attribute att){
		setMethod(obj,att.getName(),att.getValue());
	}
	/**
	 * 
	 * @param obj
	 * @param att
	 */
	public static void getMethod(Object obj,Attribute att){
		getMethod(obj,att.getName(),att.getValue());
	}
	
	/**
	 * 
	 * @param obj
	 * @param att
	 */
	public static String getMethodNameByName(String type,String xmlName){
		String[] arr =xmlName.split("_");
		StringBuffer sb=new StringBuffer(type);
		for(String str:arr){
			if(str.length()>0){
				sb.append(getFirstToCase(str));
			}
		}
		return sb.toString();
	}
	
	public static String getFirstToCase(String xmlName){
		return String.valueOf(xmlName.charAt(0)).toUpperCase()+xmlName.substring(1);
	}
	public static String getGMethodNameByName(String xmlName){
		
		return getMethodNameByName(GET_METHOD,xmlName);
	}
	public static String getSMethodNameByName(String xmlName){
		
		return getMethodNameByName(SET_METHOD,xmlName);
	}

	public static String getNameByGetMethod(String getMenthodName){
		String name=getMenthodName.replaceFirst(GET_METHOD, "");
		char fistCh=name.charAt(0);
		if(fistCh>=122||fistCh<=97){
			fistCh=(char)(fistCh+32);
		}
		name=String.valueOf(fistCh)+name.substring(1);
		return name;
	}
	public static void setMethod(Object obj,String name,Object value){
		try {
			if(value instanceof String){
				Method method = obj.getClass().getMethod(getMethodNameByName("set",name),String.class);
				method.invoke(obj,value.toString());
			}else if(value instanceof Boolean){
				Method method = obj.getClass().getMethod(getMethodNameByName("set",name),Boolean.class);
				method.invoke(obj,Boolean.valueOf(value.toString()));
			}else if(value instanceof Integer){
				Method method = obj.getClass().getMethod(getMethodNameByName("set",name),Integer.class);
				method.invoke(obj,Integer.valueOf(value.toString()));
			}else if(value instanceof Double){
				Method method = obj.getClass().getMethod(getMethodNameByName("set",name),Double.class);
				method.invoke(obj,Double.valueOf(value.toString()));
			}
			
			
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static void setMethod(Object obj,String name,String value,Class clazz){
		try {
			Method method = obj.getClass().getMethod(getMethodNameByName("set",name),clazz);
			method.invoke(obj,value);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param obj
	 * @param att
	 */
	public static void getMethod(Object obj,String name,String value){
		try {
			Method method = obj.getClass().getMethod(getMethodNameByName("get",name),String.class);
			method.invoke(obj,value);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static String getName(String packName){
		int index=-1;
		int count=0;
		for(int i=packName.length()-1;i>=0;i--){
			if(packName.charAt(i)=='.'||packName.charAt(i)=='$'){
				index=i;
				break;
			}
			count++;
		}
		return getFirstToCase(new String(packName.toCharArray(),index+1,count));
	}
	
	public static String getTypeByClassName(String className) {
		if (className.contains(".")) {
			className = className.substring(className.indexOf(".")+1,
					className.length());
			return getTypeByClassName(className);
		}
		return className;

	}
//	/**
//	 * 
//	 * @param obj
//	 * @param methodName
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws InvocationTargetException 
//	 * @throws IllegalAccessException 
//	 * @throws IllegalArgumentException 
//	 * @throws NoSuchMethodException 
//	 * @throws SecurityException 
//	 */
//	public static Method getMethod(Object obj,String methodName) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException{
//		return obj.getClass().getMethod(methodName,HttpServletRequest.class,Map.class);
//	}
	
}
