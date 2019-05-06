package com.example.tao.tcache.api;

import com.example.cache.Constant;
import com.example.tao.tcache.ConstantValue;
import com.example.tao.tcache.bean.model.Article;
import com.example.tao.tcache.bean.model.Author;
import com.example.tao.tcache.bean.model.Banner;
import com.example.tao.tcache.bean.model.Collect;
import com.example.tao.tcache.bean.model.CollectInfo;
import com.example.tao.tcache.bean.model.KeyWord;
import com.example.tao.tcache.bean.model.Login;
import com.example.tao.tcache.bean.model.Logout;
import com.example.tao.tcache.bean.model.ProjectCategory;
import com.example.tao.tcache.bean.model.Register;
import com.example.tao.tcache.bean.model.SearchResult;
import com.example.tao.tcache.bean.model.Tree;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    /**
     * 加载Banner信息
     *
     * @return
     */
    @GET(ConstantValue.URL_BANNER)
    @Headers(Constant.HEADER_DEFAULT)
    Observable<Banner> loadBanner();

    /**
     * 加载首页文章列表
     *
     * @param page
     * @return
     */
    @GET(ConstantValue.URL_ARTICLE)
    @Headers(Constant.HEADER_NONET)
    Observable<Article> loadArticle(@Path("page") int page);

    /**
     * 加载体系
     *
     * @return
     */
    @GET(ConstantValue.URL_TREE)
    @Headers(Constant.HEADER_NONET)
    Observable<Tree> loadTree();

    /**
     * 加载体系文章列表
     *
     * @param cid
     * @param page
     * @return
     */
    @GET(ConstantValue.URL_TREE_ARTICLE)
    @Headers(Constant.HEADER_NONET)
    Observable<Article> loadTreeArticle(@Path("page") int page, @Query("cid") int cid);

    /**
     * 加载项目分类
     *
     * @return
     */
    @GET(ConstantValue.URL_PROJECT_CATEGORY)
    @Headers(Constant.HEADER_NONET)
    Observable<ProjectCategory> loadProjectCategory();

    /**
     * 加载项目文章列表
     *
     * @param page
     * @param cid
     * @return
     */
    @GET(ConstantValue.URL_PROJECT_ARTICLE)
    @Headers(Constant.HEADER_NONET)
    Observable<Article> loadProjectArticle(@Path("page") int page, @Query("cid") int cid);

    /**
     * 加载公众号列表
     *
     * @return
     */
    @GET(ConstantValue.URL_WX)
    @Headers(Constant.HEADER_NONET)
    Observable<Author> loadWx();

    /**
     * 加载公众号文章列表
     *
     * @param authorId
     * @param page
     * @return
     */
    @GET(ConstantValue.URL_WX_ARTICLE)
    @Headers(Constant.HEADER_NONET)
    Observable<Article> loadWxArticle(@Path("authorId") int authorId, @Path("page") int page);

    /**
     * 注册
     *
     * @param username
     * @param password
     * @param repassword
     * @return
     */
    @POST(ConstantValue.URL_REGISTER)
    @Headers(Constant.HEADER_NO)
    Observable<Register> register(@Query("username") String username, @Query("password") String password, @Query("repassword") String repassword);

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @POST(ConstantValue.URL_LOGIN)
    @Headers(Constant.HEADER_NO)
    Observable<Login> login(@Query("username") String username, @Query("password") String password);

    /**
     * 退出登录
     *
     * @return
     */
    @GET(ConstantValue.URL_LOGOUT)
    @Headers(Constant.HEADER_NO)
    Observable<Logout> logout();

    /**
     * 收藏文章
     *
     * @param id
     * @return
     */
    @POST(ConstantValue.URL_COLLECT)
    @Headers(Constant.HEADER_NO)
    Observable<Collect> collect(@Path("id") int id);

    /**
     * 取消收藏文章
     *
     * @param id
     * @return
     */
    @POST(ConstantValue.URL_UNCOLLECT)
    @Headers(Constant.HEADER_NO)
    Observable<Collect> unCollect(@Path("id") int id);

    /**
     * 获取收藏文章列表
     *
     * @param page
     * @return
     */
    @GET(ConstantValue.URL_COLLECT_LIST)
    @Headers(Constant.HEADER_NONET)
    Observable<CollectInfo> loadCollect(@Path("page") int page);

    /**
     * 获取搜索热词
     *
     * @return
     */
    @GET(ConstantValue.URL_HOT_KEY)
    @Headers(Constant.HEADER_NONET)
    Observable<KeyWord> loadHotKey();

    /**
     * 搜索
     *
     * @param page
     * @param kw
     * @return
     */
    @POST(ConstantValue.URL_SEARCH)
    @Headers(Constant.HEADER_NO)
    Observable<SearchResult> search(@Path("page") int page, @Query("k") String kw);

}
