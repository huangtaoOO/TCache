package com.example.tao.tcache;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import android.widget.SimpleCursorTreeAdapter;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;

import org.litepal.LitePal;

public class App extends Application {

    private boolean isLogin = false;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LitePal.initialize(this);
        Utils.init(this);
        LogUtils.getConfig().setGlobalTag(ConstantValue.TAG);
        boolean nightMode = SPUtils.getInstance(ConstantValue.CONFIG_SETTINGS).getBoolean(ConstantValue.KEY_NIGHT_MODE, false);
        AppCompatDelegate.setDefaultNightMode(nightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    private static App instance;


    public static App getInstance() {
        return instance;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
