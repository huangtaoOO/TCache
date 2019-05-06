package com.example.tao.tcache.model;

import com.example.tao.tcache.base.BaseModel;
import com.example.tao.tcache.bean.model.Collect;
import com.example.tao.tcache.bean.model.SearchResult;
import com.example.tao.tcache.contract.Contract;

import io.reactivex.Observable;

public class SearchResultActivityModel extends BaseModel implements Contract.SearchResultActivityModel {

    public SearchResultActivityModel() {
        setCookie(false);
    }

    @Override
    public Observable<SearchResult> search(String key, int page) {
        return mApi.search(page, key);
    }

    @Override
    public Observable<Collect> collect(int articleId) {
        return mApi.collect(articleId);
    }

    @Override
    public Observable<Collect> unCollect(int articleId) {
        return mApi.unCollect(articleId);
    }
}
