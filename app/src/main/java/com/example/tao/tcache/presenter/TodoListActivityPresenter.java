package com.example.tao.tcache.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.example.tao.tcache.App;
import com.example.tao.tcache.base.BasePresenter;
import com.example.tao.tcache.base.Reply;
import com.example.tao.tcache.bean.model.Login;
import com.example.tao.tcache.bean.model.TodoBean;
import com.example.tao.tcache.bean.model.TodoListBean;
import com.example.tao.tcache.contract.Contract;
import com.example.tao.tcache.model.TodoListActivityModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: tao
 * @time: 2019/5/7
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class TodoListActivityPresenter extends BasePresenter<Contract.TodoListActivityView>
        implements Contract.TodoListActivityPresenter {

    private Contract.TodoListActivityModel model;

    public TodoListActivityPresenter(){
        model = new TodoListActivityModel();
    }

    public void getData(String page){
        model.getTodoList(page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Reply<TodoListBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Reply<TodoListBean> todoBeanReply) {
                        getView().onLoadSuccess();
                        getView().refreshData(todoBeanReply.getData().getDatas());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                        getView().onLoadFailed();
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.i();
                    }
                });
    }
}
