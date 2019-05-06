package com.example.tao.tcache.interceptor;

import android.annotation.TargetApi;
import android.os.Build;

import com.blankj.utilcode.util.SPUtils;
import com.example.tao.tcache.ConstantValue;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author wangyz
 * @time 2019/1/24 9:00
 * @description SaveCookieInterceptor
 */
public class WriteCookieInterceptor implements Interceptor {

    private static final String n = "loginUserName_wanandroid_com=[\\u4e00-\\u9fa5\\w\\-]+;([\\s\\S]*)";
    private static final String p = "token_pass_wanandroid_com=[\\u4e00-\\u9fa5\\w\\-]+;([\\s\\S]*)";

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        List<String> headers = response.headers("Set-Cookie");
        if (!headers.isEmpty()) {
            for (String s:headers) {
                if (s.matches(n)){
                    String a = s.split(";")[0];
                    a = a.replace("loginUserName_wanandroid_com=","");
                    String s1 = "loginUserName=" + a;
                    SPUtils.getInstance(ConstantValue.CONFIG_COOKIE).put(ConstantValue.USER_NAME, s1);
                }
                if (s.matches(p)){
                    String a = s.split(";")[0];
                    a = a.replace("token_pass_wanandroid_com=","");
                    String s1 = "loginUserPassword=" + a;
                    SPUtils.getInstance(ConstantValue.CONFIG_COOKIE).put(ConstantValue.USER_PASSWORD, s1);
                }
            }
        }
        return response;
    }
}
