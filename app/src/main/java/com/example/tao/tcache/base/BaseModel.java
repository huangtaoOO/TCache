package com.example.tao.tcache.base;

import com.example.tao.tcache.ConstantValue;
import com.example.tao.tcache.api.Api;
import com.example.tao.tcache.interceptor.ReadCookieInterceptor;
import com.example.tao.tcache.interceptor.WriteCookieInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author wangyz
 * @time 2019/1/22 8:50
 * @description BaseModel
 */
public class BaseModel {

    protected Retrofit mRetrofit;

    protected Api mApi;

    public BaseModel() {
        mApi = NetworkClient.getNetworkClient().createApi(Api.class);
    }

    /**
     * 设置是否保存cookie
     *
     * @param saveCookie
     */
    public void setCookie(boolean saveCookie) {
        mApi = NetworkClient.getNetworkClient().createApi(Api.class);
    }

}
