package com.example.cache.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.example.cache.bean.CacheSource;
import com.example.cache.bean.NetBean;
import com.example.cache.memory.lruCacheUtil;
import com.example.cache.sql.DaoCacheDataBase;
import java.io.UnsupportedEncodingException;
import java.lang.ref.SoftReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CacheManager
{

    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public static final String TAG = "CacheManager";

    private lruCacheUtil lruCacheUtil;
    private DaoCacheDataBase daoCacheDataBase ;

    private volatile static CacheManager mCacheManager;

    private SoftReference<Context> softReference;

    public static CacheManager getInstance(Context context)
    {
        if (mCacheManager == null)
        {
            synchronized (CacheManager.class)
            {
                if (mCacheManager == null)
                {
                    mCacheManager = new CacheManager(context);
                }
            }
        }
        return mCacheManager;
    }


    private CacheManager(Context context)
    {
        softReference = new SoftReference<>(context);
        lruCacheUtil = new lruCacheUtil();
        daoCacheDataBase = new DaoCacheDataBase(context);
    }

    /**
     * 删除内存和数据库缓存
     * @param context
     * @throws Exception
     */
    public void deleteAll(Context context) throws Exception
    {
        lruCacheUtil.removeAll();
        daoCacheDataBase.deleteAllData();
    }

    /**
     * 同步设置缓存
     */
    public void putCache(String key, String value,String type,long time)
    {
        NetBean netBean = new NetBean();
        netBean.setRequestUrl(key);
        netBean.setReply(value);
        netBean.setExpireTime(System.currentTimeMillis() + time);
        netBean.setCacheVersion(softReference.get()==null?
                String.valueOf(CacheConfiguration.getAppVersion())
                :String.valueOf(getAppVersion(softReference.get())));
        netBean.setRequestType(type);
        if (lruCacheUtil == null && daoCacheDataBase == null)
            return;
        if (lruCacheUtil !=null)
            lruCacheUtil.addNetBeanToMemory(key,netBean);
        if (daoCacheDataBase != null){
            daoCacheDataBase.insertCacheInfo(netBean);
        }

    }

    /**
     * 异步设置缓存
     */
    public void setCache(final String key, final String value,final String type,final long time) {
        cachedThreadPool.submit(() -> putCache(key, value,type,time));
    }

    /**
     * 同步获取缓存
     */
    public CacheSource getCache(String key)
    {
        CacheSource cacheSource = new CacheSource();
        if (lruCacheUtil!=null){
            NetBean netBean = lruCacheUtil.getNetBeanFromMemCache(key);
            if (netBean==null){
                netBean = daoCacheDataBase.queryStudents(key);
            }
            cacheSource.setResult(CacheType.MEMORY_CACHE);
            cacheSource.setResult(netBean==null?null:netBean.getReply());
            return cacheSource;
        }
        if (daoCacheDataBase!=null){
            NetBean netBean = daoCacheDataBase.queryStudents(key);
            cacheSource.setResult(CacheType.DISK_CACHE);
            cacheSource.setResult(netBean.getReply());
            return cacheSource;
        }

       return null;
    }

    /**
     * 异步获取缓存
     */
    public void getCache(final String key, final CacheCallback callback) {
        cachedThreadPool.submit(() -> {
            CacheSource cache = getCache(key);
            callback.onGetCache(cache);
        });
    }

    /**
     * 移除缓存
     */
    public boolean removeCache(String key) {
        if (lruCacheUtil!=null && daoCacheDataBase!=null){
            lruCacheUtil.removeNetBeanFromMemory(key);
            return daoCacheDataBase.deleteData(key);
        }
        return false;
    }

    /**
     * 对字符串进行MD5编码
     */
    public static String encryptMD5(String string)
    {
        try
        {
            byte[] hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash)
            {
                if ((b & 0xFF) < 0x10)
                {
                    hex.append("0");
                }
                hex.append(Integer.toHexString(b & 0xFF));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return string;
    }

    /**
     * 获取APP版本号
     */
    private int getAppVersion(Context context)
    {
        PackageManager pm = context.getPackageManager();
        try
        {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi == null ? 0 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return 0;
    }
}
