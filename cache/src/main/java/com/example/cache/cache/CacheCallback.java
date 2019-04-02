package com.example.cache.cache;

import com.example.cache.bean.CacheSource;

/**
 * @author: tao
 * @time: 2019/2/17
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 异步获取缓存回调接口
 */
public interface CacheCallback {
    void onGetCache(CacheSource cache);
}
