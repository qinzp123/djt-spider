package com.qinzp.djt_spider.util;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @ClassName LoadPropertyUtil
 * @Description TODO
 * @Author qinzp
 * @Date 2019/12/24/16:53
 * @Version 1.0
 */
public class LoadPropertyUtil {

    public static String getValue(String key) {
        String value = null;
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
            //遍历取值
            value = resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static void main(String[] args) {
        String value = getValue("threadNum");
        System.out.println("value = " + value);
    }
}
