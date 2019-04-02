package com.example.cache.cache;

/**
 * @author: tao
 * @time: 2019/2/17
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 缓存配置
 */
public class CacheConfiguration {

    private static int appVersion = 1;

    private static long timeOut = 3 * 24 * 60 *60 * 1000;

    public static int getAppVersion() {
        return appVersion;
    }

    public static void setAppVersion(int appVersion) {
        CacheConfiguration.appVersion = appVersion;
    }

    public static long getTimeOut() {
        return timeOut;
    }

    public static void setTimeOut(int timeOut) {
        CacheConfiguration.timeOut = timeOut;
    }
}
