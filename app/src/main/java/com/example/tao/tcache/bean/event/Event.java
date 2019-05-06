package com.example.tao.tcache.bean.event;

public class Event {

    /**
     * 切换到体系二级类别列表
     */
    public static final int TYPE_TREE_ARTICLE_FRAGMENT = 1;

    /**
     * 登录成功
     */
    public static final int TYPE_LOGIN = 2;

    /**
     * 退出登录
     */
    public static final int TYPE_LOGOUT = 3;

    /**
     * 收藏
     */
    public static final int TYPE_COLLECT = 4;

    /**
     * 取消收藏
     */
    public static final int TYPE_UNCOLLECT = 5;

    /**
     * 切换夜间模式
     */
    public static final int TYPE_CHANGE_DAY_NIGHT_MODE = 6;

    /**
     * 目标界面-MainActivity
     */
    public static final int TARGET_MAIN = 1;

    /**
     * 目标界面-目录
     */
    public static final int TARGET_MENU = 2;

    /**
     * 目标界面-首页
     */
    public static final int TARGET_HOME = 3;

    /**
     * 目标界面-体系
     */
    public static final int TARGET_TREE = 4;

    /**
     * 目标界面-项目
     */
    public static final int TARGET_PROJECT = 5;

    /**
     * 目标界面-公众号
     */
    public static final int TARGET_WX = 6;

    /**
     * 目标界面-收藏列表
     */
    public static final int TARGET_COLLECT = 7;

    /**
     * 目标界面-搜索结果
     */
    public static final int TARGET_SEARCH_RESULT = 8;

    public int target;

    public int type;

    public String data;

}
