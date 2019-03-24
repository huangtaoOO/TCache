package com.example.cache.memory;

import android.util.LruCache;
import com.example.cache.bean.NetBean;

/**
 * @author: tao
 * @time: 2019/2/12
 * @e-mail: tao.h@askdd.org
 * @explain: 说明
 */
public class lruCacheUtil {
    private LruCache<String, NetBean> lruCache;

    public lruCacheUtil() {
        lruCache = new LruCache<>(10);
    }


    public void addNetBeanToMemory(String key, NetBean cache) {
        if (getNetBeanFromMemCache(key) == null) {
            lruCache.put(key, cache);
        }
    }
    public NetBean getNetBeanFromMemCache(String key) {
        NetBean netBean = lruCache.get(key);
        if (netBean == null) return null;
        if (System.currentTimeMillis() < netBean.getExpireTime()){
            return netBean;
        }else {
            return null;
        }
    }

    // 从缓存中删除指定的Bitmap
    public void removeNetBeanFromMemory(String key) {
        lruCache.remove(key);
    }

    //清空缓存
    public void removeAll(){
        lruCache.evictAll();
    }
}
