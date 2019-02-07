package com.example.cache.interceptor;

import android.content.Context;
import android.util.Log;
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

/**
 * @author: tao
 * @time: 2019/1/13
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class TCacheInterceptor implements Interceptor {
    public static final String USE_CACHE = "use_cache";
    public static final String TRUE = "true";

    private Context context;

    public void setContext(Context context)
    {
        this.context = context;
    }

    public TCacheInterceptor(Context context)
    {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request request = chain.request();
        String cacheHead = request.header(USE_CACHE);
        String cache_control = request.header("Cache-Control");
        // 意思是要缓存
        // 这里还支持WEB端协议的缓存头
        // 只支持get和post方法
        boolean canUseCache = (TRUE.equals(cacheHead) ||
                (cache_control != null && !cache_control.isEmpty())) &&
                ("GET".equals(request.method()) || "post".equals(request.method()));

        //如果没有网络且不能使用缓存怎返回失败，如果没有缓存则返回失败
        long oldnow = System.currentTimeMillis();
        Response response;
        if (!NetUtil.hasInternet(context)){
            if (canUseCache){
                response = getCacheResponse(request, oldnow);
                if (response==null){
                    return getFaileRespons(chain,"无网络和缓存");
                }else {
                    return response;
                }
            }else {
                return getFaileRespons(chain,"无网络");
            }
        }
        //有网络能使用缓存，有缓存直接返回无缓存下一步
        if (canUseCache){
            response = getCacheResponse(request, oldnow);
            if (response!=null){
                return response;
            }
        }
        response = chain.proceed(request);
        //网络请求成功并且能够使用缓存才进行存储
        if (response.isSuccessful()&&canUseCache){
            ResponseBody responseBody = response.body();
            long now = System.currentTimeMillis();
            String url = request.url().url().toString();
            String responStr = null;
            String reqBodyStr = getPostParams(request);
            responStr = responseBody.string();
            if (responseBody != null)
            {
                if (responStr == null) {
                    responStr = "";
                }
                CacheManager.getInstance(context).setCache(CacheManager.encryptMD5(url + reqBodyStr), responStr);//存缓存，以链接+参数进行MD5编码为KEY存
                Log.i("HttpRetrofit", "--> Push Cache:" + url + " :Success");
            }
            return getOnlineResponse(response, responStr);
        }else {
            return response;
        }
    }

    private Response getFaileRespons(Chain chain,String message){
        return new Response.Builder()
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .code(504)
                .message(message)
                .body(Util.EMPTY_RESPONSE)
                .sentRequestAtMillis(-1L)
                .receivedResponseAtMillis(System.currentTimeMillis())
                .build();
    }

    private Response getCacheResponse(Request request, long oldNow)
    {
        Log.i("HttpRetrofit", "--> Try to Get Cache   --------");
        String url = request.url().url().toString();
        String params = getPostParams(request);
        String cacheStr = CacheManager.getInstance(context).getCache(CacheManager.encryptMD5(url + params));//取缓存，以链接+参数进行MD5编码为KEY取
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
        long useTime = System.currentTimeMillis() - oldNow;
        Log.i("HttpRetrofit", "<-- Get Cache: " + response.code() + " " + response.message() + " " + url + " (" + useTime + "ms)");
        Log.i("HttpRetrofit", cacheStr + "");
        return response;
    }

    private Response getOnlineResponse(Response response, String body)
    {
        ResponseBody responseBody = response.body();
        return new Response.Builder()
                .code(response.code())
                .body(ResponseBody.create(responseBody == null ? null : responseBody.contentType(), body))
                .request(response.request())
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
    private String getPostParams(Request request)
    {
        String reqBodyStr = "";
        String method = request.method();
        if ("POST".equals(method)) // 如果是Post，则尽可能解析每个参数
        {
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody)
            {
                FormBody body = (FormBody) request.body();
                if (body != null)
                {
                    for (int i = 0; i < body.size(); i++)
                    {
                        sb.append(body.encodedName(i)).append("=").append(body.encodedValue(i)).append(",");
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
