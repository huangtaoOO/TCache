package com.example.tao.tcache.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.example.tao.tcache.base.BasePresenter;
import com.example.tao.tcache.bean.model.Collect;
import com.example.tao.tcache.bean.model.SearchResult;
import com.example.tao.tcache.contract.Contract;
import com.example.tao.tcache.model.SearchResultActivityModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchResultActivityPresenter extends BasePresenter<Contract.SearchResultActivityView> implements Contract.SearchResultActivityPresenter {

    private Contract.SearchResultActivityModel mModel;

    public SearchResultActivityPresenter() {
        mModel = new SearchResultActivityModel();
    }


    @Override
    public void search(String key, int page) {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.search(key, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SearchResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
            }

            @Override
            public void onNext(SearchResult result) {
                LogUtils.i();
                if (isViewAttached()) {
                    getView().onSearch(result);
                    getView().onLoadSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtils.i();
            }
        });
    }

    @Override
    public void collect(int articleId) {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.collect(articleId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Collect>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(Collect result) {
                LogUtils.i();
                if (isViewAttached()) {
                    getView().onCollect(result, articleId);
                    getView().onLoadSuccess();
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

    @Override
    public void unCollect(int articleId) {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        mModel.unCollect(articleId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Collect>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i();
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(Collect result) {
                LogUtils.i();
                if (isViewAttached()) {
                    getView().onUnCollect(result, articleId);
                    getView().onLoadSuccess();
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
