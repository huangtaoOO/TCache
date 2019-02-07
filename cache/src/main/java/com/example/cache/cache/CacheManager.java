package com.example.cache.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by xiaolei on 2017/5/17.
 */

public class CacheManager
{
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public static final String TAG = "CacheManager";

    //max cache size 10mb
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 10;

    private static final int DISK_CACHE_INDEX = 0;

    private static final String CACHE_DIR = "responses";

    private DiskLruCache mDiskLruCache;

    private volatile static CacheManager mCacheManager;

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

    
    public void delete(Context context) throws Exception
    {
        File diskCacheDir = getDiskCacheDir(context, CACHE_DIR);
        if (mDiskLruCache != null)
        {
            DiskLruCache.deleteContents(diskCacheDir);
        }
    }

    private CacheManager(Context context)
    {
        File diskCacheDir = getDiskCacheDir(context, CACHE_DIR);
        if (!diskCacheDir.exists())
        {
            boolean b = diskCacheDir.mkdirs();
            Log.d(TAG, "!diskCacheDir.exists() --- diskCacheDir.mkdirs()=" + b);
        }
        if (diskCacheDir.getUsableSpace() > DISK_CACHE_SIZE)
        {
            try
            {
                mDiskLruCache = DiskLruCache.open(diskCacheDir,
                        getAppVersion(context), 1/*一个key对应多少个文件*/, DISK_CACHE_SIZE);
                Log.d(TAG, "mDiskLruCache created");
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 同步设置缓存
     */
    public void putCache(String key, String value)
    {
        if (mDiskLruCache == null)
            return;
        OutputStream os = null;
        try
        {
            DiskLruCache.Editor editor = mDiskLruCache.edit(encryptMD5(key));
            os = editor.newOutputStream(DISK_CACHE_INDEX);
            os.write(value.getBytes());
            os.flush();
            editor.commit();
            mDiskLruCache.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (os != null)
            {
                try
                {
                    os.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 异步设置缓存
     */
    public void setCache(final String key, final String value)
    {
        cachedThreadPool.submit(new Runnable()
        {
            @Override
            public void run()
            {
                putCache(key, value);
            }
        });
    }

    /**
     * 同步获取缓存
     */
    public String getCache(String key)
    {
        if (mDiskLruCache == null)
        {
            return null;
        }
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try
        {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(encryptMD5(key));
            if (snapshot != null)
            {
                fis = (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);
                bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int len;
                while ((len = fis.read(buf)) != -1)
                {
                    bos.write(buf, 0, len);
                }
                byte[] data = bos.toByteArray();
                return new String(data);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (fis != null)
            {
                try
                {
                    fis.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (bos != null)
            {
                try
                {
                    bos.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 异步获取缓存
     */
    public void getCache(final String key, final CacheCallback callback)
    {
        cachedThreadPool.submit(new Runnable()
        {
            @Override
            public void run()
            {
                String cache = getCache(key);
                callback.onGetCache(cache);
            }
        });
    }

    /**
     * 移除缓存
     */
    public boolean removeCache(String key)
    {
        if (mDiskLruCache != null)
        {
            try
            {
                return mDiskLruCache.remove(encryptMD5(key));
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获取缓存目录
     */
    private File getDiskCacheDir(Context context, String uniqueName)
    {
        String cachePath = context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
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
