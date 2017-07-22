package net.chinahrd.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 * 
 * @author zhiwei
 *
 */
public class Str {
	/**
	 * 判断是否为空，空字符串，空时间
	 * 
	 * @param str
	 * @return
	 */
	public static boolean IsEmpty(String str) {
		if (str == null) {
			return true;
		}
		str = str.trim();
		if ("null".equals(str.toLowerCase()) || "".equals(str) || "0000-00-00".equals(str)) {
			return true;
		}
		return false;
	}

	public static String num2Cn(String num) {
		switch (num) {
		case "0":
			return "零";
		case "1":
			return "一";
		case "2":
			return "二";
		case "3":
			return "三";
		case "4":
			return "四";
		case "5":
			return "五";
		case "6":
			return "六";
		case "7":
			return "七";
		case "8":
			return "八";
		case "9":
			return "九";
		default:
			return num;
		}
	}

	public static String dayofWeek2Cn(String num) {
		switch (num) {
		case "1":
			return "日";
		case "2":
			return "一";
		case "3":
			return "二";
		case "4":
			return "三";
		case "5":
			return "四";
		case "6":
			return "五";
		case "7":
			return "六";
		default:
			return num;
		}
	}

	/**
	 * 替换最后指定字符串
	 * 
	 * @param text
	 * @param searchString
	 *            指定被替换字符
	 * @param replacement
	 *            替换新字符
	 * @return
	 * @author jxzhang by 2017-4-11
	 */
	public static String replaceLast(String text, String searchString, String replacement) {
		return StringUtils.reverse(StringUtils.replaceOnce(StringUtils.reverse(text), searchString, replacement));
	}

	/**
	 * 替换最后指定字符串
	 * 
	 * @param text
	 * @param replacement
	 *            替换新字符
	 * @return
	 */
	public static String replaceLast(String text, String replacement) {
		return text.substring(0, text.length()-1) + replacement;
	}

	public static void main(String... s) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(DateUtil.strToDate("1991-10-25"));
		Calendar c2 = Calendar.getInstance();
		c2.setTime(new Date());
		System.out.println(c2.compareTo(c1));

	}
}
