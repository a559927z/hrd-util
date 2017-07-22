package net.chinahrd.utils.pdf2html;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 下载地址：
 * https://github.com/coolwanglu/pdf2htmlEX/wiki/Download
 *
 */
public class Pdf2htmlEXUtil {
	private final static Logger logger = LoggerFactory.getLogger(Pdf2htmlEXUtil.class);
	/**
	 * 调用pdf2htmlEX将pdf文件转换为html文件
	 * 
	 * @param command
	 *            调用exe的字符串
	 * @return
	 */
	public static boolean pdf2html1(String command) {
		Runtime rt = Runtime.getRuntime();
		try {
			Process p = rt.exec(command);
			StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
			// kick off stderr
			errorGobbler.start();
			StreamGobbler outGobbler = new StreamGobbler(p.getInputStream(), "STDOUT");
			// kick off stdout
			outGobbler.start();
//			int w = p.waitFor();
//			System.out.println(w);
//			int v = p.exitValue();
//			System.out.println(v);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/** 
     * 调用pdf2htmlEX将pdf文件转换为html文件   尚未在Linux下测试
     * @param exeFilePath pdf2htmlEX.exe文件路径 
     * @param pdfFile pdf文件绝对路径 
     * @param destDir 生成的html文件存放路径 
     * @param htmlFileName 生成的html文件名称 
     * @return 
     */  
    public static boolean pdf2html(String exeFilePath,String pdfFile,String destDir,String htmlFileName){  
        if(!(exeFilePath!=null&&!"".equals(exeFilePath)  
                &&pdfFile!=null&&!"".equals(pdfFile)  
                &&htmlFileName!=null&&!"".equals(htmlFileName))){  
        	logger.info("传递的参数有误！");  
            return false;  
        }  
        Runtime rt = Runtime.getRuntime();  
        StringBuilder command = new StringBuilder();  
        command.append(exeFilePath).append(" ");  
        if(destDir!=null&&!"".equals(destDir.trim()))//生成文件存放位置,需要替换文件路径中的空格  
            command.append("--dest-dir ").append(destDir.replace(" ", "\" \"")).append(" ");  
        command.append("--optimize-text 1 ");//尽量减少用于文本的HTML元素的数目 (default: 0)  
        command.append("--split-pages 1 ");  
        command.append("--process-outline 0 ");//html中显示链接：0——false，1——true  
        command.append("--font-format woff ");//嵌入html中的字体后缀(default ttf) ttf,otf,woff,svg  
        command.append(pdfFile.replace(" ", "\" \"")).append(" ");//需要替换文件路径中的空格  
        if(htmlFileName!=null&&!"".equals(htmlFileName.trim())){  
            command.append(htmlFileName);  
            if(htmlFileName.indexOf(".html")==-1)  
                command.append(".html");  
        }  
        try {  
            Process p = rt.exec(command.toString());  
            StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");                
            //开启屏幕标准错误流  
            errorGobbler.start();    
            StreamGobbler outGobbler = new StreamGobbler(p.getInputStream(), "STDOUT");    
            //开启屏幕标准输出流  
            outGobbler.start();   
            int w = p.waitFor();  
            int v = p.exitValue();  
            if(w==0&&v==0){  
                return true;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return false;  
    }  
      

	public static void main(String[] args) {
//		pdf2html1("d:\\pdf2htmlex\\pdf2htmlEX.exe d:\\pdf2htmlex\\b.pdf hello.html");
		pdf2html("d:\\pdf2htmlex\\pdf2htmlEX.exe","d:\\pdf2htmlex\\a.pdf","d:\\pdf2htmlex","a.html");
		//pdf2htmlEX.exe --split-pages 1 --dest-dir /d d.pdf d.html 将文件存到分区的d文件夹中
	}
}
