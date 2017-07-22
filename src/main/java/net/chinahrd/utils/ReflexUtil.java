package net.chinahrd.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Java 反射类
 * Created by wqcai on 2016/1/27 027.
 */
public class ReflexUtil {
    static Logger logger = LoggerFactory.getLogger(ReflexUtil.class);

    //getMethod
    static public Object invokeMethod(String propertiesName, Object object) {
        try {
            if (object == null) return null;
            if (!propertiesName.contains(".")) {
                String methodName = "get" + getMethodName(propertiesName);
                Method method = object.getClass().getMethod(methodName);
                return method.invoke(object);
            }
            String methodName = "get" + getMethodName(propertiesName.substring(0, propertiesName.indexOf(".")));
            Method method = object.getClass().getMethod(methodName);
            return invokeMethod(propertiesName.substring(propertiesName.indexOf(".") + 1), method.invoke(object));

        } catch (Exception e) {
            logger.error(e.toString(), e);
            return null;
        }
    }

    private static String getMethodName(String fildeName) {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    public static void main(String args[]) {
//        Video video = new Video();
//        Album album = new Album();
//        album.setAlbumId(346l);
//        video.setAlbum(album);
//        video.setVideoId(126l);
//        System.out.println(ReflexUtil.invokeMethod("album.albumId", video));
    }
}
