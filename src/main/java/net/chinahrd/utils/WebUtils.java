package net.chinahrd.utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.ContextLoader;

/**
 * web工具包
 * 
 * @author jxzhang on 2017年3月15日
 * @Verdion 1.0 版本
 */
public class WebUtils {

	/**
	 * 获取项目全路径
	 * 
	 * @param request
	 * @return http://localhost:8080/项目名称/
	 */
	public static String getBasePath(HttpServletRequest request) {
		StringBuilder path = new StringBuilder();
		path.append(request.getScheme()).append("://");
		path.append(request.getServerName()).append(":");
		path.append(request.getServerPort());
		path.append(request.getContextPath());
		return path.toString();
	}

	/**
	 * 获取ServletContext上下文</br>
	 * 通过Spring.ContextLoader获取，底层也是通HttpServletRequest获取
	 * 
	 * @return
	 */
	public static ServletContext getServletContext() {
		return ContextLoader.getCurrentWebApplicationContext().getServletContext();
	}

	/**
	 * 获取ServletContext上下文</br>
	 * 通HttpServletRequest获取
	 * 
	 * @return
	 */
	public static ServletContext getServletContext(HttpServletRequest request) {
		return request.getSession().getServletContext();
	}

	/**
	 * 项目绝对路径
	 * 
	 * @return D:\项目名称\src\main\webapp\
	 */
	public static String getAbsolutePath() {
		return getServletContext().getRealPath("/");
	}

	/**
	 * 获取当前请求路径
	 * 
	 * 
	 * @param request
	 * @return /run/sys/onlineOptView
	 */
	public static String getRequestURI(HttpServletRequest request) {
		return request.getRequestURI();
	}

	/**
	 * 当前文件编译绝对位置
	 * 
	 * @param clz
	 * @return
	 */
	public static String getCompFileAbsolutePath(Class<?> clz) {
		return clz.getClass().getResource("").getPath();
	}

	public static String getFileAbsolutePath(Class<?> clz) {
		return clz.getProtectionDomain().getCodeSource().getLocation().getFile();
	}
	
}
