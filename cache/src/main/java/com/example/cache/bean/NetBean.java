package com.example.cache.bean;


/**
 * @author: tao
 * @time: 2019/1/13
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 缓存的bean
 */
public class NetBean {

    //请求类型
    private String requestType;
    //请求url 一般值为请求地址+参数 的md5值
    private String requestUrl;
    //超时时间
    private long expireTime;
    //回复
    private String reply;
    //参数
    private String parameter;
    //请求头
    private String requestHeader;
    //缓存版本
    private String cacheVersion;

    public NetBean() {
    }

    public NetBean(String requestType, String requestUrl, long expireTime, String reply,
                   String parameter, String requestHeader, String cacheVersion) {
        this.requestType = requestType;
        this.requestUrl = requestUrl;
        this.expireTime = expireTime;
        this.reply = reply;
        this.parameter = parameter;
        this.requestHeader = requestHeader;
        this.cacheVersion = cacheVersion;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String request) {
        this.requestUrl = request;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getCacheVersion() {
        return cacheVersion;
    }

    public void setCacheVersion(String cacheVersion) {
        this.cacheVersion = cacheVersion;
    }

    @Override
    public String toString() {
        return "NetBean{" +
                "requestType='" + requestType + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", expireTime=" + expireTime +
                ", reply='" + reply + '\'' +
                ", parameter='" + parameter + '\'' +
                ", requestHeader='" + requestHeader + '\'' +
                ", cacheVersion='" + cacheVersion + '\'' +
                '}';
    }
}
