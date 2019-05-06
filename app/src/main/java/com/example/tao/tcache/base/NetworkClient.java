package com.example.tao.tcache.base;

import android.util.Log;
import com.example.cache.interceptor.TCacheInterceptor;
import com.example.tao.tcache.App;
import com.example.tao.tcache.ConstantValue;
import com.example.tao.tcache.interceptor.ReadCookieInterceptor;
import com.example.tao.tcache.interceptor.WriteCookieInterceptor;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: tao
 * @time: 2019/1/12
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 网络请求客户端
 */
public class NetworkClient {

    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    private static final int TIME_OUT = 60*5;

    /**
     * 私有化构造方法
     */
    private NetworkClient(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.i("HttpLog",message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new ReadCookieInterceptor())
                .addInterceptor(new TCacheInterceptor(App.getInstance()))
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(ConstantValue.URL_BASE)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static class SingletonHolder{
        private static final NetworkClient INSTANCE = new NetworkClient();
    }

    public static NetworkClient getNetworkClient (){
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取api
     * @param service
     * @param <T>
     * @return
     */
    public <T> T createApi(Class<T> service){
        return retrofit.create(service);
    }

    /**
     * 获取独立的Retrofit
     * @param saveCookie 是否启用cookie
     * @return                  Retrofit
     */
    public static Retrofit getNewRetrofit(Boolean saveCookie){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.i("HttpLog",message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder build = new OkHttpClient()
                .newBuilder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor);
        if (saveCookie!=null){
            build.addInterceptor(new ReadCookieInterceptor());
            build.addInterceptor(new WriteCookieInterceptor(saveCookie));
        }
        build.addInterceptor(new TCacheInterceptor(App.getInstance()));
        OkHttpClient okHttpClient =  build.build();
        return new Retrofit.Builder()
                .baseUrl(ConstantValue.URL_BASE)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

}
