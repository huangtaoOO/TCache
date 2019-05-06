package com.example.tao.tcache.model;

import com.example.tao.tcache.base.BaseModel;
import com.example.tao.tcache.bean.model.KeyWord;
import com.example.tao.tcache.contract.Contract;

import io.reactivex.Observable;

public class SearchActivityModel extends BaseModel implements Contract.SearchActivityModel {
    @Override
    public Observable<KeyWord> loadKeyWord() {
        return mApi.loadHotKey();
    }
}
