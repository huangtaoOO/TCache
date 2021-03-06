package com.example.tao.tcache.model;

import com.example.tao.tcache.base.BaseModel;
import com.example.tao.tcache.bean.model.Logout;
import com.example.tao.tcache.contract.Contract;

import io.reactivex.Observable;

public class MenuFragmentModel extends BaseModel implements Contract.MenuFragmentModel {

    public MenuFragmentModel() {
        setCookie(true);
    }

    @Override
    public Observable<Logout> logout() {
        return mApi.logout();
    }
}
