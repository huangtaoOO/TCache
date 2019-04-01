package com.example.cache.sql;

/**
 * @author: tao
 * @time: 2019/1/13
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 数据库 常量池
 */
public class SqlConstant {
    /**
     * SQL_NAME：数据库名
     */
    public static final String SQL_NAME = "T_Cache";

    /**
     * SQL_NAME：数据库版本
     */
    public static final int DB_VERSION = 1;

    /**
     *
     */
    public static final class cache {
        /**
         * 数据库表名
         */
        public static final String TABLE_CACHE = "cache";

        public static final String ID = "id";

        public static final String REQUEST_TYPE = "request_type";

        public static final String REQUEST_URL = "request_url";

        public static final String EXPIRE_TIME = "expire_time";

        public static final String PARAMETER = "parameter";

        public static final String REQUEST_HEADER = "request_header";

        public static final String CACHE_VERSION = "cache_version";

        public static final String REPLY = "reply";

    }

    /**
     * 建表的 sql 语句
     */
    public static final String CACHE_CREATE_TABLE_SQL = "create table " + cache.TABLE_CACHE + " ("
            + cache.ID + " integer primary key autoincrement ,"
            + cache.REQUEST_TYPE + " varchar(10) not null ,"
            + cache.REQUEST_URL + " text unique ,"
            + cache.EXPIRE_TIME + " bigint not null ,"
            + cache.PARAMETER + " text ,"
            + cache.REQUEST_HEADER + " text ,"
            + cache.CACHE_VERSION + " varchar(10) not null ,"
            + cache.REPLY + " text not null "
            + ");";

    /**
     * 通过url以及是否超时的查询语句
     */
    public static final String QUERRY_CACHE = SqlConstant.cache.REQUEST_URL
            + " = ? and " + SqlConstant.cache.EXPIRE_TIME + " <= ?";

    /**
     * 通过url删除语句
     */
    public static final String DELETE_WHERE = SqlConstant.cache.REQUEST_URL + " = ?";

    /**
     * 通过url以及是否超时的查询语句
     */
    public static final String QUERRY_CACHE_FOR_NAME = SqlConstant.cache.REQUEST_URL + " = ?";

}
