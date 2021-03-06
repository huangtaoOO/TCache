package com.example.tao.tcache.model;

import com.example.tao.tcache.base.BaseModel;
import com.example.tao.tcache.bean.model.Login;
import com.example.tao.tcache.contract.Contract;

import io.reactivex.Observable;

public class LoginActivityModel extends BaseModel implements Contract.LoginActivityModel {

    public LoginActivityModel() {
        setCookie(true);
    }

    @Override
    public Observable<Login> login(String username, String password) {
        return mApi.login(username, password);
    }
}
