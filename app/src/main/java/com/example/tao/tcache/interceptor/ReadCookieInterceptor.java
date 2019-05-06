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

/**
 * @author wangyz
 * @time 2019/1/24 9:19
 * @description ReadCookieInterceptor
 */
public class ReadCookieInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        String cookies1 = SPUtils.getInstance(ConstantValue.CONFIG_COOKIE).getString(ConstantValue.USER_NAME);
        if (!TextUtils.isEmpty(cookies1)) {
            builder.addHeader("Cookie", cookies1);
            Log.i("测试1",cookies1);
        }
        String cookies2 = SPUtils.getInstance(ConstantValue.CONFIG_COOKIE).getString(ConstantValue.USER_PASSWORD);
        if (!TextUtils.isEmpty(cookies2)){
            builder.addHeader("Cookie",cookies2);
            Log.i("测试2",cookies2);
        }
        return chain.proceed(builder.build());
    }
}
