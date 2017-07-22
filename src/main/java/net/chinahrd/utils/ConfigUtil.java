package net.chinahrd.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 配置文件响应工具类
 * Created by wqcai on 2016/1/8 008.
 */
public class ConfigUtil {

    // 获取默认的语言环境
    static Locale locale = Locale.getDefault();

    static ResourceBundle bundle = ResourceBundle.getBundle("conf/config", locale);


    public static  String  getValue(String key,Object... params){
        String msg;
        try {
            msg = bundle.getString(key);
            if (null != params && params.length > 0) {
                msg = MessageFormat.format(msg, params);
            }
            return msg;
        } catch (Exception e) {
            return "";
        }
    }
}
