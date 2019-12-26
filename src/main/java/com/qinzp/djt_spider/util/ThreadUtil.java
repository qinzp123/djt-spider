package com.qinzp.djt_spider.util;

/**
 * @ClassName ThreadUtil
 * @Description TODO
 * @Author qinzp
 * @Date 2019/12/24/18:47
 * @Version 1.0
 */
public class ThreadUtil {
    public static void sleep(long millions) {
        try {
            Thread.currentThread().sleep(millions);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
