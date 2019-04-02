package com.example.tao.tcache.model;

import android.annotation.TargetApi;
import android.os.Build;

import com.blankj.utilcode.util.NetworkUtils;
import com.example.tao.tcache.ConstantValue;
import com.example.tao.tcache.base.BaseModel;
import com.example.tao.tcache.bean.db.Article;
import com.example.tao.tcache.bean.model.Collect;
import com.example.tao.tcache.contract.Contract;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author wangyz
 * @time 2019/1/22 9:51
 * @description WxArticleFragmentModel
 */
public class WxArticleFragmentModel extends BaseModel implements Contract.WxArticleFragmentModel {

    public WxArticleFragmentModel() {
        setCookie(false);
    }

    @Override
    public Observable<List<Article>> load(int authorId, int page) {
        Observable<List<Article>> loadFromLocal = Observable.create(emitter -> {
            List<Article> list = LitePal.where("type=? and authorId=?", Article.TYPE_WX + "", authorId + "").order("time desc").offset(page * ConstantValue.PAGE_SIZE).limit(ConstantValue.PAGE_SIZE).find(Article.class);
            emitter.onNext(list);
            emitter.onComplete();
        });
        if (NetworkUtils.isConnected()) {
            Observable<List<Article>> loadFromNet = doLoadArticleFromNet(authorId, page);
            return Observable.concat(loadFromLocal, loadFromNet);
        } else {
            return loadFromLocal;
        }
    }

    @Override
    public Observable<List<Article>> refresh(int authorId, int page) {
        return doLoadArticleFromNet(authorId, page);
    }

    @Override
    public Observable<Collect> collect(int articleId) {
        return mApi.collect(articleId);
    }

    @Override
    public Observable<Collect> unCollect(int articleId) {
        return mApi.unCollect(articleId);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Observable<List<Article>> doLoadArticleFromNet(int authorId, int page) {
        return mApi.loadWxArticle(authorId, page).filter(a -> a.getErrorCode() == 0).map(a -> {
            List<Article> all = LitePal.where("type=? and authorId=?", Article.TYPE_WX + "", authorId + "").find(Article.class);
            List<Article> list = new ArrayList<>();
            a.getData().getDatas().stream().forEach(d -> {
                long count = all.stream().filter(m -> m.articleId == d.getId()).count();
                if (count <= 0) {
                    Article article = new Article();
                    article.type = Article.TYPE_WX;
                    article.articleId = d.getId();
                    article.title = d.getTitle();
                    article.des = d.getDesc();
                    article.authorId = d.getChapterId();
                    article.author = d.getAuthor();
                    article.category = d.getSuperChapterName() + "/" + d.getChapterName();
                    article.time = d.getPublishTime();
                    article.link = d.getLink();
                    list.add(article);
                } else {
                    all.stream().filter(m -> m.articleId == d.getId()).forEach(m -> {
                        if (m.time != d.getPublishTime() || m.collect != d.isCollect()) {
                            m.title = d.getTitle();
                            m.des = d.getDesc();
                            m.authorId = d.getChapterId();
                            m.author = d.getAuthor();
                            m.category = d.getSuperChapterName() + "/" + d.getChapterName();
                            m.time = d.getPublishTime();
                            m.link = d.getLink();
                            m.collect = d.isCollect();
                            if (!m.collect) {
                                m.setToDefault("collect");
                            }
                            m.update(m.id);
                        }
                    });
                }
            });
            LitePal.saveAll(list);
            List<Article> result = LitePal.where("type=? and authorId=?", Article.TYPE_WX + "", authorId + "").order("time desc").offset(page * ConstantValue.PAGE_SIZE).limit(ConstantValue.PAGE_SIZE).find(Article.class);
            return result;
        });
    }
}
