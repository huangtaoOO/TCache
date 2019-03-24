package com.example.cache;

/**
 * @author: tao
 * @time: 2019/3/24
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class Constant {

    //普通默认策略，既第一次请求获取保存网络后面再请求使用缓存
    public static final String CACHE_DEFAULT = "CACHE_DEFAULT";

    //没网时候使用缓存
    public static final String CACEH_NONET = "CACEH_NONET";

    //根据web缓存协议进行缓存
    public static final String CACHE_WEB = "CACHE_WEB";

    //不使用缓存
    public static final String CACHE_NO = "CACHE_NO";

    //请求头的键
    public static final String HEADER = "use_cache";

    /**
     * 对应的请求头
     */
    public static final String HEADER_DEFAULT = HEADER + ":" + CACHE_DEFAULT;
    public static final String HEADER_NONET = HEADER + ":" + CACEH_NONET;
    public static final String HEADER_WEB = HEADER + ":" + CACHE_WEB;
    public static final String HEADER_NO = HEADER + ":" + CACHE_NO;

}
