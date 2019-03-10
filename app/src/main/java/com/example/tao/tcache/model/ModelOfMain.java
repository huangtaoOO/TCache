package com.example.tao.tcache.model;

import android.util.Log;

import com.example.tao.tcache.basis.model.BaseModel;
import com.example.tao.tcache.basis.net.NetworkCallBack;
import com.example.tao.tcache.bean.PublicNumBean;
import com.example.tao.tcache.bean.ResponseBean;
import com.example.tao.tcache.net.NetworkClient;
import com.example.tao.tcache.net.api.UserApi;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: tao
 * @time: 2019/2/24
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class ModelOfMain extends BaseModel {

    public static final int TEXT = 1001;

    public ModelOfMain(NetworkCallBack networkCallBack) {
        super(networkCallBack);
    }

    @Override
    protected void requestInNet(int code, Object... parameter) {
        switch (code){
            case TEXT :
                disposable = NetworkClient.getNetworkClient().createApi(UserApi.class)
                        .wxarticle()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResponseBean<ArrayList<PublicNumBean>>>() {
                            @Override
                            public void accept(@NonNull ResponseBean<ArrayList<PublicNumBean>> bean) throws Exception {
                                Log.i("测试",bean.toString());
                                if (bean.getErrorCode()!=0){
                                    throw new  Exception(bean.getErrorMsg());
                                }else {
                                    networkCallBack.onSuccess(TEXT,bean);
                                }
                                disposable.dispose();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                networkCallBack.onFailure(TEXT,throwable.getMessage());
                                disposable.dispose();
                            }
                        });
                break;
        }
    }
}
