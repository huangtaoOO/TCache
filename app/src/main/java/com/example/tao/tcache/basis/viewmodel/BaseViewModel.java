package com.example.tao.tcache.basis.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;

import com.example.tao.tcache.basis.model.BaseModel;

import java.util.ArrayList;

/**
 * @author: tao
 * @time: 2019/2/24
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public abstract class BaseViewModel<M extends BaseModel>{

    protected M model;

    protected final MutableLiveData<Intent> startIntent = new MutableLiveData<>();

    protected final MutableLiveData<ArrayList<Object>> messageToContext = new MutableLiveData<>();

    public BaseViewModel(){
        model = setModel();
    }

    protected abstract M setModel();

    protected void getData(int code , Object... param){
        if (model == null){
            throw new NullPointerException("进行获取数据的操作前，检查Model是否赋值");
        }
        model.getData(code,param);
    }

    protected void sendMessageToContext(ArrayList<Object> params){
        messageToContext.setValue(params);
    }
}
