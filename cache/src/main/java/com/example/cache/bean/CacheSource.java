package com.example.cache.bean;

import com.example.cache.cache.CacheType;

/**
 * @author: tao
 * @time: 2019/2/17
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class CacheSource {
    private String result ;
    private String source = CacheType.DISK_CACHE;

    public CacheSource() {
    }

    public CacheSource(String result) {
        this.result = result;
    }

    public CacheSource(String result, String source) {
        this.result = result;
        this.source = source;
    }

    @Override
    public String toString() {
        return "CatchSource{" +
                "result='" + result + '\'' +
                ", source='" + source + '\'' +
                '}';
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
