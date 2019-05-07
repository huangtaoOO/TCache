package com.example.tao.tcache.view;

import android.content.Context;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
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
        WindowManager wm = ((WindowManager)getSystemService(Context.WINDOW_SERVICE));
        int w = 480 ;
        int h = 854 ;
        if (wm!=null) {
            wm.getDefaultDisplay().getMetrics(dm);
            w = dm.widthPixels;
            h = dm.heightPixels;
        }
        path = new Path();
        path.moveTo((float) (0.5*w), (float) (0.75*h));
        path.cubicTo((float) (0.1*w), (float) (0.3*h), (float) (0.333*w), (float) (0.2*h), (float) (0.5*w), (float) (0.3*h));
        path.cubicTo((float) (0.666*w), (float) (0.2*h), (float) (0.9*w), (float) (0.3*h),(float) (0.5*w), (float) (0.75*h));
        pathLayoutManager = new PathLayoutManager(path,400);
        pathLayoutManager.setScrollMode(PathLayoutManager.SCROLL_MODE_NORMAL);
        pathLayoutManager.setItemDirectionFixed(true);
        recyclerTodo.setLayoutManager(pathLayoutManager);
        recyclerTodo.setAdapter(todoListAdapter = new TodoListAdapter(this,new ArrayList<>()));
        mPresenter.getData(1+"");
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
        Log.i("成都市",list.toString());
        todoListAdapter.setList(list);
    }
}
