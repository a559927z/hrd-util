package net.chinahrd.utils.export;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import net.chinahrd.utils.WebUtils;

/**
 * 
 * @author jxzhang on 2017年6月8日
 * @Verdion 1.0 版本
 */
public class PhantomJSUtil {

	
	
	
	/**
	 * 导出页面
	 * 
	 * @param dirName
	 *            导出的目录名称
	 * @param xhrPage
	 *            要求导出的请求页面
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void export(String dirName, String xhrPage, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PrintArguments args = new PrintArguments();
		StringBuilder urlStr = new StringBuilder();
		urlStr.append(WebUtils.getBasePath(request)).append(xhrPage);

		args.setUrl(urlStr.toString());
		args.setPdfSize(PrintArguments.PdfSize.A4);
		args.setTimeout(1000);
		PhantomJSExecutor exe = new PhantomJSExecutor();
		
		byte[] bytes = exe.rasterizeAsBytes(args);

		String path = WebUtils.getAbsolutePath() + dirName + File.separator + "temp" + File.separator + "testArgs.pdf";
		FileUtils.writeByteArrayToFile(new File(path), bytes);

		// 设置响应头和客户端保存文件名
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		String filename = new String(("月报").getBytes(), "iso8859-1");
		response.setHeader("Content-Disposition", "attachment;fileName=" + filename + ".pdf");
		OutputStream os = response.getOutputStream();
		os.write(bytes, 0, bytes.length);
	}
	
	
}
