package com.example.cache.interceptor;

import android.content.Context;
import android.util.Log;

import com.example.cache.cache.CacheConfiguration;
import com.example.cache.cache.CacheManager;
import com.example.cache.cache.CacheType;
import com.example.cache.tool.NetUtil;

import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;

import static com.example.cache.Constant.CACEH_NONET;
import static com.example.cache.Constant.CACHE_DEFAULT;
import static com.example.cache.Constant.CACHE_NO;
import static com.example.cache.Constant.CACHE_WEB;
import static com.example.cache.Constant.HEADER;

/**
 * @author: tao
 * @time: 2019/1/13
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class TCacheInterceptor implements Interceptor {

    private Context context;

    public TCacheInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String cacheHead = request.header(HEADER);
        boolean isGetAndPost = ("GET".equals(request.method()) || "post".equals(request.method()));
        Response response;
        if (isGetAndPost && cacheHead != null && cacheHead.length() != 0) {
            switch (cacheHead) {
                case CACHE_DEFAULT:
                    response = cacheDefault(request);
                    if (response != null) {
                        return response;
                    }
                    break;
                case CACEH_NONET:
                    response = cacheNoNet(request);
                    if (response != null) {
                        return response;
                    }
                    break;
                case CACHE_WEB:
                    response = cacheWeb(request);
                    if (response != null) {
                        return response;
                    }
                    break;
                case CACHE_NO:
                    //不使用缓存不进行处理
                    break;
                default:
                    //没有填写信息，默认不使用缓存不进行处理
                    break;
            }
        }

        response = chain.proceed(request);
        //网络请求成功并且能够使用缓存才进行存储
        if (response.isSuccessful() && isGetAndPost &&
                cacheHead != null && cacheHead.length() != 0) {

            switch (cacheHead) {
                case CACHE_DEFAULT:
                    return defaultSaveCache(response,request,CacheConfiguration.getTimeOut());
                case CACEH_NONET:
                    return defaultSaveCache(response,request,CacheConfiguration.getTimeOut());
                case CACHE_WEB:
                    return saveCacheUseWeb(response,request);
                case CACHE_NO:
                    return response;
                default:
                    return response;
            }
        } else {
            return response;
        }
    }


    /**
     * 默认策略获取缓存
     * @param request 请求
     * @return 缓存的Response 如果为null则表示需要进行网络请求获取数据
     */
    private Response cacheDefault(Request request){
        //默认策略如下：
        //1.无缓存，返回null让网络进行请求
        //2.有缓存,返回缓存数据
        Log.i("TCacheInterceptor","cacheDefault -- start");
        long timeNow = System.currentTimeMillis();
        Response response = getCacheResponse(request, timeNow);
        if (response!=null){
            return response;
        }else{
            return null;
        }
    }

    /**
     * 无网络时获取获取缓存
     * @param request 请求
     * @return 缓存的Response 如果为null则表示需要进行网络请求获取数据
     */
    private Response cacheNoNet(Request request){
        //无网络使用缓存
        //1.判断是否有网络，无网络返回自行构建的respone
        //2.有网络，返回null，请求网络
        Log.i("TCacheInterceptor","cacehNoNet -- start");
        long timeNow = System.currentTimeMillis();
        if (NetUtil.hasInternet(context)){
            return null;
        }else {
            Response response = getCacheResponse(request, timeNow);
            if (response!=null){
                return response;
            }else{
                return getFailResponse(request,"无网络，无缓存");
            }
        }
    }

    /**
     * 根据web缓存协议获取缓存
     * @param request 请求
     * @return 缓存的Response 如果为null则表示需要进行网络请求获取数据
     */
    private Response cacheWeb(Request request){
        //根据web协议使用缓存，在这里主要缓存的存储有关，这里直接调用默认方法即可
        //1.直接查找是否有缓存，如果有直接使用
        Log.i("TCacheInterceptor","cacheWeb -- start");
        long timeNow = System.currentTimeMillis();
        Response response = getCacheResponse(request, timeNow);
        if (response!=null){
            return response;
        }else{
            return null;
        }
    }

    /**
     * 默认缓存数据的方法
     * @param response 回复
     * @param request 请求
     * @param time 超时时间
     * @return 返回一个新的response用于上层调用
     * @throws IOException io异常
     */
    private Response defaultSaveCache(Response response,Request request ,long time) throws IOException {
        ResponseBody responseBody = response.body();
        String url = request.url().url().toString();
        String responStr;
        String reqBodyStr = getPostParams(request);
        responStr = responseBody==null?"":responseBody.string();
        //存缓存，以链接+参数进行MD5编码为KEY存
        CacheManager.getInstance(context).setCache(CacheManager.encryptMD5(url + reqBodyStr),
                responStr,request.method(),time);
        Log.i("HttpRetrofit", "--> Push Cache:" + url + " :Success");
        return getOnlineResponse(response, responStr);
    }

    /**
     * 使用web缓存协议进行缓存数据
     * @param response 回复
     * @param request 请求
     * @return 返回一个新的response用于上层调用
     * @throws IOException io异常
     */
    private Response saveCacheUseWeb(Response response,Request request) throws IOException{
        String cache_control = response.header("Cache-control");
        if (cache_control==null || cache_control.length() == 0) return response;
        switch (cache_control){
            case "public":
                return defaultSaveCache(response,request,CacheConfiguration.getTimeOut());
            case "private":
                return defaultSaveCache(response,request,CacheConfiguration.getTimeOut());
            case "no-cache":
                return response;
            case "no-store":
                return response;
            case "must-revalidation":
                return defaultSaveCache(response,request,CacheConfiguration.getTimeOut());
            case "proxy-revalidation":
                return defaultSaveCache(response,request,CacheConfiguration.getTimeOut());
            default:
                if (cache_control.contains("max-age=")){
                    String string = cache_control.substring("max-age=".length());
                    long time = Long.parseLong(string);
                    return defaultSaveCache(response,request,time);
                }else {
                    return response;
                }
        }
    }

    /**
     * 返回一个失败的Response
     * @param request 请求
     * @param message 消息
     * @return Response
     */
    private Response getFailResponse(Request request, String message){
        return new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(504)
                .message(message)
                .body(Util.EMPTY_RESPONSE)
                .sentRequestAtMillis(-1L)
                .receivedResponseAtMillis(System.currentTimeMillis())
                .build();
    }

    /**
     * 得到缓存的数据
     * @param request 请求
     * @param timeNow 现在的时间
     * @return  返回null表示没有缓存，需要进行网络请求
     */
    private Response getCacheResponse(Request request, long timeNow)
    {
        Log.i("HttpRetrofit", "--> Try to Get Cache   --------");
        String url = request.url().url().toString();
        String params = getPostParams(request);
        //取缓存，以链接+参数进行MD5编码为KEY取
        String cacheStr = CacheManager.getInstance(context)
                .getCache(CacheManager.encryptMD5(url + params)).getResult();
        if (cacheStr == null)
        {
            Log.i("HttpRetrofit", "<-- Get Cache Failure ---------");
            return null;
        }
        Response response = new Response.Builder()
                .code(200)
                .body(ResponseBody.create(null, cacheStr))
                .request(request)
                .message(CacheType.DISK_CACHE)
                .protocol(Protocol.HTTP_1_0)
                .build();
        long useTime = System.currentTimeMillis() - timeNow;
        Log.i("HttpRetrofit", "<-- Get Cache: " + response.code()
                + " " + response.message() + " " + url + " (" + useTime + "ms)");
        Log.i("HttpRetrofit", cacheStr + "");
        return response;
    }

    /**
     * 由于Response只能使用一次，故缓存后重新构建一个新的Response
     * @param response 网络回复
     * @param body 返回的数据
     * @return Response
     */
    private Response getOnlineResponse(Response response, String body)
    {
        ResponseBody responseBody = response.body();
        return new Response.Builder()
                .code(response.code())
                .body(ResponseBody.create(responseBody == null ? null : responseBody.contentType(), body))
                .request(response.request())
                .headers(response.headers())
                .message(response.message())
                .protocol(response.protocol())
                .build();
    }

    /**
     * 获取在Post方式下。向服务器发送的参数
     *
     * @param request
     * @return
     */
    private String getPostParams(Request request) {
        String reqBodyStr = "";
        String method = request.method();
        // 如果是Post，则尽可能解析每个参数
        if ("POST".equals(method)) {
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                if (body != null) {
                    for (int i = 0; i < body.size(); i++) {
                        sb.append(body.encodedName(i)).append("=")
                                .append(body.encodedValue(i)).append(",");
                    }
                    sb.delete(sb.length() - 1, sb.length());
                }
                reqBodyStr = sb.toString();
                sb.delete(0, sb.length());
            }
        }
        return reqBodyStr;
    }
}
