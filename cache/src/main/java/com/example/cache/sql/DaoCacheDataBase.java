package com.example.cache.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.cache.bean.NetBean;

/**
 * @author: tao
 * @time: 2019/2/12
 * @e-mail: tao.h@askdd.org
 * @explain: 说明
 */
public class DaoCacheDataBase {

    private CacheHelper helper;
    private SQLiteDatabase database ;

    public DaoCacheDataBase(Context context){
        helper = new CacheHelper(context);
        database = helper.getWritableDatabase();
    }

    /**
     * 插入一条缓存信息
     * @param bean
     */
    public void insertCacheInfo(NetBean bean){
        ContentValues contentValues =  new ContentValues();
        contentValues.put(SqlConstant.cache.REQUEST_TYPE,bean.getRequestType());
        contentValues.put(SqlConstant.cache.REQUEST_URL,bean.getRequestUrl());
        contentValues.put(SqlConstant.cache.REQUEST_HEADER,bean.getRequestHeader());
        contentValues.put(SqlConstant.cache.CACHE_VERSION,bean.getCacheVersion());
        contentValues.put(SqlConstant.cache.PARAMETER,bean.getParameter());
        contentValues.put(SqlConstant.cache.EXPIRE_TIME,bean.getExpireTime());
        contentValues.put(SqlConstant.cache.REPLY,bean.getReply());
        database.insert(SqlConstant.cache.TABLE_CACHE,null,contentValues);
    }

    /**
     * 查询一条缓存信息
     * @param requestUrl 根据url_type查询
     * @return 无缓存或者缓存超时
     */
    public NetBean queryStudents(String requestUrl){
        long time = System.currentTimeMillis();
        Cursor cursor = database.query(SqlConstant.cache.TABLE_CACHE, null,
                SqlConstant.QUERRY_CACHE, new String[]{requestUrl, String.valueOf(time)},
                null, null, null, null);
        if (cursor!=null&&cursor.moveToFirst()) {
            String reques_type = cursor.getString(cursor.getColumnIndex(SqlConstant.cache.REQUEST_TYPE));
            String request_url = cursor.getString(cursor.getColumnIndex(SqlConstant.cache.REQUEST_URL));
            String parameter = cursor.getString(cursor.getColumnIndex(SqlConstant.cache.PARAMETER));
            String request_header = cursor.getString(cursor.getColumnIndex(SqlConstant.cache.REQUEST_HEADER));
            String cache_version = cursor.getString(cursor.getColumnIndex(SqlConstant.cache.CACHE_VERSION));
            long expire_time = cursor.getLong(cursor.getColumnIndex(SqlConstant.cache.EXPIRE_TIME));
            String reply = cursor.getString(cursor.getColumnIndex(SqlConstant.cache.REPLY));
            NetBean netBean = new NetBean(reques_type,request_url,expire_time,reply,parameter,request_header,cache_version);
            cursor.close();
            return netBean;
        }else {
            return null;
        }
    }

    /**
     * 删除一条缓存信息
     * @param requestUrl
     * @return
     */
    public boolean deleteData(String requestUrl){
        return database.delete(SqlConstant.cache.TABLE_CACHE,SqlConstant.DELETE_WHERE,new String[]{
                requestUrl
        })!=0;
    }

    /**
     * 删除表中所有数据
     */
    public void deleteAllData(){
        database.delete(SqlConstant.cache.TABLE_CACHE,null,null);
    }
}
