package com.example.tao.tcache.interceptor;

import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.example.cache.Constant;
import com.example.tao.tcache.ConstantValue;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ReadCookieInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        Request.Builder builder = chain.request().newBuilder();
        String cookies = SPUtils.getInstance(ConstantValue.CONFIG_COOKIE).getString(ConstantValue.KEY_USER);
        if (!TextUtils.isEmpty(cookies)) {
            for (String cookie : cookies.split(";")) {
                builder.addHeader("Cookie", cookie);
            }
            return chain.proceed(builder.build());
        }
        return response;
    }
}
