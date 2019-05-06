package com.example.tao.tcache.util;

import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.example.tao.tcache.App;
import com.example.tao.tcache.ConstantValue;

/**
 * @author wangyz
 * @time 2019/1/24 16:24
 * @description LoginUtil
 */
public class LoginUtil {

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    public static String getLoginUser() {
        String cookies1 = SPUtils.getInstance(ConstantValue.CONFIG_COOKIE).getString(ConstantValue.USER_NAME);
        String cookies2 = SPUtils.getInstance(ConstantValue.CONFIG_COOKIE).getString(ConstantValue.USER_PASSWORD);
        if (!TextUtils.isEmpty(cookies1) && !TextUtils.isEmpty(cookies2)) {
           return cookies1;
        }
        return "";
    }

    public static void setLoginUser(){

    }

    /**
     * 清空登录信息
     */
    public static void clearLoginInfo() {
        SPUtils.getInstance("cookie").put("user", "");
    }

    /**
     * 是否已经登录
     *
     * @return
     */
    public static boolean isLogin() {
        return App.getInstance().isLogin();
    }

}
