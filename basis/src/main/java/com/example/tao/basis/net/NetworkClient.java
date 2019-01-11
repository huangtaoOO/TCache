package com.example.tao.basis.net;

import com.example.tao.basis.Constant;
import com.example.tao.basis.utils.LogUtil;

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
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.i("HttpLog",message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
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

}
