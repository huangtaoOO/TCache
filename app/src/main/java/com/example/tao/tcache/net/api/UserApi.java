package com.example.tao.tcache.net.api;

import com.example.tao.tcache.bean.PublicNumBean;
import com.example.tao.tcache.bean.ResponseBean;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * @author: tao
 * @time: 2019/1/12
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public interface UserApi {


    /**
     * 测试，获取公众号列表
     * @return
     */
    @GET("wxarticle/chapters/json")
    @Headers("use_cache:true")
    Observable<ResponseBean<ArrayList<PublicNumBean>>> wxarticle();
}
