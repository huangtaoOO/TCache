package com.example.tao.tcache.model;

import com.example.tao.tcache.base.BaseModel;
import com.example.tao.tcache.bean.model.Register;
import com.example.tao.tcache.contract.Contract;

import io.reactivex.Observable;

/**
 * @author wangyz
 * @time 2019/1/24 10:26
 * @description RegisterActivityModel
 */
public class RegisterActivityModel extends BaseModel implements Contract.RegisterActivityModel {
    
    @Override
    public Observable<Register> register(String username, String password) {
        return mApi.register(username, password, password);
    }
}
