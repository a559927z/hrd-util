package net.chinahrd.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
* 附件上传处理
*/
public class UploadUtil {
   private static final String ALLOW_SUFFIX = "jpg,png,gif,jpeg,xls,xlsx";//允许文件格式
   private static long ALLOW_SIZE = 2L*1024*1024;//允许文件大小
   private String fileName;
   private String[] fileNames;
    


   public String getFileName() {
       return fileName;
   }

   public void setFileName(String fileName) {
       this.fileName = fileName;
   }

   public String[] getFileNames() {
       return fileNames;
   }

   public void setFileNames(String[] fileNames) {
       this.fileNames = fileNames;
   }

    

   /**
    * 
    * <p class="detail">
    * 功能：重新命名文件
    * </p>
    * @return
    */
   private static String getFileNameNew(){
       SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
       return fmt.format(new Date());
   }
    
   /**
    * 
    * <p class="detail">
    * 功能：文件上传
    * </p>
    * @param files
    * @param destDir
    * @throws Exception
    */
   public void uploads(MultipartFile[] files, String destDir,HttpServletRequest request) throws Exception {
       String path = request.getContextPath();
       String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
       try {
           fileNames = new String[files.length];
           int index = 0;
           for (MultipartFile file : files) {
               String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
               int length = ALLOW_SUFFIX.indexOf(suffix);
               if(length == -1){
                   throw new Exception("请上传允许格式的文件");
               }
               if(file.getSize() > ALLOW_SIZE){
                   throw new Exception("您上传的文件大小已经超出范围");
               }
               String realPath = request.getSession().getServletContext().getRealPath("/");
               File destFile = new File(realPath+destDir);
               if(!destFile.exists()){
                   destFile.mkdirs();
               }
               String fileNameNew = getFileNameNew()+"."+suffix;//
               File f = new File(destFile.getAbsoluteFile()+"\\"+fileNameNew);
               file.transferTo(f);
               f.createNewFile();
               fileNames[index++] =basePath+destDir+fileNameNew;
           }
       } catch (Exception e) {
           throw e;
       }
   }
    
   /**
    * 
    * <p class="detail">
    * 功能：文件上传
    * </p>
    * @author wangsheng
    * @date 2014年9月25日 
    * @param files
    * @param destDir
    * @throws Exception
    */
   public static String upload(MultipartFile file, String destDir,HttpServletRequest request) throws Exception {
       String path = request.getContextPath();
       String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
       try {
               String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
               int length = ALLOW_SUFFIX.indexOf(suffix);
               if(length == -1){
                   throw new Exception("请上传允许格式的文件");
               }
               if(file.getSize() > ALLOW_SIZE){
                   throw new Exception("您上传的文件大小已经超出范围");
               }
                
               String realPath = request.getSession().getServletContext().getRealPath("/");
               File destFile = new File(realPath+destDir);
               if(!destFile.exists()){
                   destFile.mkdirs();
               }
               String fileNameNew = getFileNameNew()+"."+suffix;
               File f = new File(destFile.getAbsoluteFile()+"/"+fileNameNew);
               file.transferTo(f);
               f.createNewFile();
               return basePath+destDir+fileNameNew;
       } catch (Exception e) {
           throw e;
   }
}
}