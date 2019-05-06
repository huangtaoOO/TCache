package com.example.tao.tcache.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.example.tao.tcache.App;
import com.example.tao.tcache.base.BasePresenter;
import com.example.tao.tcache.bean.model.Logout;
import com.example.tao.tcache.contract.Contract;
import com.example.tao.tcache.model.MenuFragmentModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wangyz
 * @time 2019/1/17 15:50
 * @description MenuFragmentPresenter
 */
public class MenuFragmentPresenter extends BasePresenter<Contract.MenuFragmentView> implements Contract.MenuFragmentPresenter {

    private Contract.MenuFragmentModel mModel;

    public MenuFragmentPresenter() {
        mModel = new MenuFragmentModel();
    }

    @Override
    public void logout() {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.logout().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Logout>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
                App.getInstance().setLogin(false);
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(Logout result) {
                LogUtils.i();
                if (isViewAttached()) {
                    getView().onLoadSuccess();
                    getView().onLogout(result);
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
