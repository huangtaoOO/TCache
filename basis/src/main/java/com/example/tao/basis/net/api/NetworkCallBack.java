package com.example.tao.basis.net.api;

/**
 * @author: tao
 * @time: 2018/10/10
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public interface NetworkCallBack {

    /**
     * 网络接口成功回调
     * @param tag 判断标签，区分是哪个方法调用的回调
     * @param o 返回的实体类
     */
    void onSuccess(int tag, Object o);

    /**
     * 网络接口失败回调
     * @param tag 判断标签，区分是哪个方法调用的回调
     * @param s
     */
    void onFailure(int tag, String s);
}
