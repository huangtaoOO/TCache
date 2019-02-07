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

    public String requestType;

    public String request;

    public long expireTime;

    public String reply;

    public String parameter;

    public String requestHeader;

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
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

    @Override
    public String toString() {
        return "NetBean{" +
                "requestType='" + requestType + '\'' +
                ", request='" + request + '\'' +
                ", expireTime=" + expireTime +
                ", reply='" + reply + '\'' +
                ", parameter='" + parameter + '\'' +
                ", requestHeader='" + requestHeader + '\'' +
                '}';
    }
}
