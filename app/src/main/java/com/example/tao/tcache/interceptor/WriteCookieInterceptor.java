package com.example.tao.tcache.interceptor;

import android.annotation.TargetApi;
import android.os.Build;

import com.blankj.utilcode.util.SPUtils;
import com.example.tao.tcache.ConstantValue;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

public class WriteCookieInterceptor implements Interceptor {

    private boolean mSaveCookie;

    public WriteCookieInterceptor(boolean saveCookie) {
        this.mSaveCookie = saveCookie;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (mSaveCookie) {
            List<String> headers = response.headers("Set-Cookie");
            if (!headers.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                headers.stream().forEach(h -> {
                    sb.append(h).append(";");
                });
                SPUtils.getInstance(ConstantValue.CONFIG_COOKIE).put(ConstantValue.KEY_USER, sb.toString());
            }
        }
        return response;
    }
}
