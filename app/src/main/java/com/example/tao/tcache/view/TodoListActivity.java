package com.example.tao.tcache.view;

import android.content.Context;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tao.tcache.R;
import com.example.tao.tcache.adapter.TodoListAdapter;
import com.example.tao.tcache.base.BaseActivity;
import com.example.tao.tcache.bean.model.TodoBean;
import com.example.tao.tcache.contract.Contract;
import com.example.tao.tcache.presenter.TodoListActivityPresenter;
import com.wuyr.pathlayoutmanager.PathLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TodoListActivity extends BaseActivity<Contract.TodoListActivityView, TodoListActivityPresenter>
        implements Contract.TodoListActivityView {
    private PathLayoutManager pathLayoutManager;

    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.recycler_todo)
    RecyclerView recyclerTodo;

    private TodoListAdapter todoListAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_todo_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Path path;
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = ((WindowManager) getSystemService(Context.WINDOW_SERVICE));
        int w = 480;
        int h = 854;
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(dm);
            w = dm.widthPixels;
            h = dm.heightPixels;
        }
        float x1 = (float) Math.random();
        float x2 = (float) Math.random();
        float y1 = (float) Math.random();
        float y2 = (float) Math.random();
        path = new Path();
        path.moveTo((float) 0, (float) 0);
        path.cubicTo((x1 * w), (y1 * h), (x2 * w), (y2 * h), (float) (0.9 * w), (float) (0.9 * h));
        pathLayoutManager = new PathLayoutManager(path, 300);
        pathLayoutManager.setScrollMode(PathLayoutManager.SCROLL_MODE_LOOP);
        pathLayoutManager.setItemDirectionFixed(true);
        pathLayoutManager.setItemScaleRatio(0f, 0f, 1f, 0.5f, 0f, 1f);
        recyclerTodo.setLayoutManager(pathLayoutManager);
        recyclerTodo.setAdapter(todoListAdapter = new TodoListAdapter(this, new ArrayList<>()));
        todoListAdapter.setListener(new TodoListAdapter.Listener() {
            @Override
            public void onListener1(TodoBean bean) {
                mPresenter.complete(bean);
            }

            @Override
            public void onListener2(TodoBean bean) {
                mPresenter.delete(bean);
            }
        });
        mPresenter.getData(1 + "",2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        mPresenter.getData(1 + "",2);
                        break;
                    case 1:
                        mPresenter.getData(1 + "",0);
                        break;
                    case 2:
                        mPresenter.getData(1 + "",1);
                        break;
                    default:
                        mPresenter.getData(1 + "",2);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected TodoListActivityPresenter createPresenter() {
        return new TodoListActivityPresenter();
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadSuccess() {
        Toast.makeText(this,"成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadFailed() {

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }


    @Override
    public void refreshData(List<TodoBean> list) {
        int height = recyclerTodo.getHeight();
        if (list.size()<10){
            pathLayoutManager.setItemOffset(height);
        }else {
            pathLayoutManager.setItemOffset(300);
        }
        todoListAdapter.setList(list);
    }

}
