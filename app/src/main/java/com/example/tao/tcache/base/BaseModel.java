package com.example.tao.tcache.base;

import com.example.tao.tcache.api.Api;
import retrofit2.Retrofit;

public class BaseModel {
    protected Retrofit mRetrofit;

    protected Api mApi;

    public BaseModel() {
        mRetrofit = NetworkClient.getNewRetrofit(null);
        mApi = mRetrofit.create(Api.class);
    }

    /**
     * 设置是否保存cookie
     *
     * @param saveCookie
     */
    public void setCookie(boolean saveCookie) {
        mRetrofit = NetworkClient.getNewRetrofit(saveCookie);
        mApi = mRetrofit.create(Api.class);
    }
}
