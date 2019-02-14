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


    // 把Bitmap对象加入到缓存中
    public void addBitmapToMemory(String key, NetBean bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            lruCache.put(key, bitmap);
        }
    }
    // 从缓存中得到Bitmap对象
    public NetBean getBitmapFromMemCache(String key) {
        return lruCache.get(key);
    }

    // 从缓存中删除指定的Bitmap
    public void removeBitmapFromMemory(String key) {
        lruCache.remove(key);
    }
}
