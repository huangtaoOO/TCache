package com.example.tao.basis.model;

import com.example.tao.basis.net.NetworkCallBack;

import io.reactivex.disposables.Disposable;

/**
 * @author: tao
 * @time: 2019/2/24
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public abstract class BaseModel {

    protected Disposable disposable;
    protected NetworkCallBack networkCallBack;

    private BaseModel(NetworkCallBack networkCallBack){
        this.networkCallBack = networkCallBack;
    }

    public void getData(int code,Object... parameter){
        //TODO 调用网络请求前的操作

        requestInNet(code,parameter);
    }

    protected abstract void requestInNet(int code,Object... parameter);
}
