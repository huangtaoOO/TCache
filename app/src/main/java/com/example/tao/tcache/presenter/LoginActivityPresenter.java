package com.example.tao.tcache.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.example.tao.tcache.base.BasePresenter;
import com.example.tao.tcache.bean.model.Login;
import com.example.tao.tcache.contract.Contract;
import com.example.tao.tcache.model.LoginActivityModel;
import com.example.tao.tcache.util.LoginUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LoginActivityPresenter extends BasePresenter<Contract.LoginActivityView>
        implements Contract.LoginActivityPresenter {

    private Contract.LoginActivityModel mModel;

    public LoginActivityPresenter() {
        mModel = new LoginActivityModel();
    }

    @Override
    public void login(String username, String password) {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.login(username, password).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Login>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
                LoginUtil.setLoginUser(username,password);
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(Login result) {
                LogUtils.i();
                if (isViewAttached()) {
                    getView().onLoadSuccess();
                    getView().onLogin(result);
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(e.getMessage());
                if (isViewAttached()) {
                    getView().onLoadFailed();
                }
            }

            @Override
            public void onComplete() {
                LogUtils.i();
            }
        });
    }
}
