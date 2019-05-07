package com.example.tao.tcache.model;

import com.example.tao.tcache.base.BaseModel;
import com.example.tao.tcache.base.Reply;
import com.example.tao.tcache.bean.model.TodoBean;
import com.example.tao.tcache.bean.model.TodoListBean;
import com.example.tao.tcache.contract.Contract;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: tao
 * @time: 2019/5/7
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class TodoListActivityModel extends BaseModel implements Contract.TodoListActivityModel {

    public TodoListActivityModel() {
        setCookie(false);
    }

    @Override
    public Observable<Reply<TodoListBean>> getTodoList(String page) {
        return mApi.getTodoList(page);
    }
}
