package net.chinahrd.utils.version.util;

import java.io.File;


/**
 * 
 * @author htpeng 2017年4月13日下午6:35:39
 */
public class SysUrlUtil {
	private static String requestRealUrl = "";
	private static String projectUrl = ""; // 运行时根目录
	private static String projectDevelopUrl = ""; // 开发根目录
	private static final String SIGN_FILE_NAME = "WEB-INF";
	private static final String PACKAGE_ROOT_URL = "bin";
	public static final String methodNoFond = "aaa---2-3--dd--f--g-a1-23-4-a-";

	// public static final String File_=File.pathSeparator;
	/**
	 * 得到项目根目录的绝对路径
	 * 
	 * @return
	 */
	public static String getProjectRoot() {
		if (projectUrl.length() == 0) {
			projectUrl = Thread.currentThread().getContextClassLoader().getResource("//").getPath();

			if (projectUrl.indexOf("/") == 0) {
				projectUrl = projectUrl.replaceFirst("/", "");
			}
			// projectUrl="D:/penghaitao/桌面/copyXiangm/copyXiangm/bin/";
			// String
			// url=SysUrlUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			// System.out.println(url);
			// File file = new File(url);
			// projectUrl = file.getParent();
			// System.out.println(projectUrl);
			// while (!projectUrl.endsWith(SIGN_FILE_NAME)) {
			// file = new File(projectUrl);
			// projectUrl = file.getParent();
			// }
		}
		return projectUrl;
	}

	public static String getProjectDevelopRoot() {
		if (projectDevelopUrl.length() == 0) {
			projectDevelopUrl = Thread.currentThread().getContextClassLoader().getResource("//").getPath();

			if (projectDevelopUrl.indexOf("/") == 0) {
				projectDevelopUrl = projectDevelopUrl.replaceFirst("/", "");
			}

			projectDevelopUrl = projectDevelopUrl.substring(0, projectDevelopUrl.lastIndexOf("/"));
			projectDevelopUrl = projectDevelopUrl.substring(0, projectDevelopUrl.lastIndexOf("/")) + "/src";

		}
		return projectDevelopUrl;
	}

	/**
	 * 根据request的绝对路径除去项目名
	 * 
	 * @param url
	 * @return
	 */
	public static String getRequestConfigUrl(String url) {
		url = url.substring(url.indexOf("/") + 1);
		url = url.substring(url.indexOf("/"), url.indexOf("."));
		return url;
	}

	/**
	 * 根据除去项目名的路径 取得配置的control路径
	 * 
	 * @param url
	 * @return
	 */
	public static String getContorlMapKeyByConfigUrl(String url) {
		url = url.substring(0, url.indexOf("."));
		return url;
	}

	// /**
	// * 根据除去项目名的路径 取得配置的方法名
	// * @param url
	// * @return
	// */
	// public static String getMethodNameByConfigUrl(HttpServletRequest
	// request){
	//
	// String method=request.getParameter("method");
	// if(null==method){
	// method=methodNoFond;
	// }
	// return method;
	// }

	/**
	 * 得到项目class的根目录绝对路径
	 * 
	 * @return
	 */
	public static String getProjectPackageRootUrl() {
		System.out.println(getProjectRoot() + File.separator + PACKAGE_ROOT_URL);
		return getProjectRoot() + File.separator + PACKAGE_ROOT_URL;
	}

	/**
	 * 
	 * @return
	 */
	public static String getReplaceClassRealToPackeage() {
		return getProjectPackageRootUrl() + File.separator;
	}

	public static String pathToPage(String url) {
		url = url.replace("\\", "/");
		url = url.replace(SysUrlUtil.getProjectRoot(), "");
		url = url.substring(0, url.length() - 6);
		url = url.replace("\\", ".");
		url = url.replace("/", ".");
		return url;
	}

	public static String packToPath(String packageName) {
		return packToPath(getProjectDevelopRoot(), packageName);
	}

	public static String packToPath(String filename, String packageName) {
		packageName = packageName.replaceAll("\\.", "\\\\");

		return filename + "\\" + packageName;
	}
	// public static void main(String[] args){
	// System.out.println("/xiangmu/index.jsp".substring("/xiangmu/index.jsp".indexOf("/"+1)));
	// }
	//

}
