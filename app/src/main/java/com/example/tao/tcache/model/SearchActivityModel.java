package com.example.tao.tcache.model;

import com.example.tao.tcache.base.BaseModel;
import com.example.tao.tcache.bean.model.KeyWord;
import com.example.tao.tcache.contract.Contract;

import io.reactivex.Observable;

/**
 * @author wangyz
 * @time 2019/1/28 16:42
 * @description SearchActivityModel
 */
public class SearchActivityModel extends BaseModel implements Contract.SearchActivityModel {
    @Override
    public Observable<KeyWord> loadKeyWord() {
        return mApi.loadHotKey();
    }
}
