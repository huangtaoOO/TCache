package com.example.tao.tcache.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.tao.tcache.R;
import com.example.tao.tcache.adapter.CollectArticleAdapter;
import com.example.tao.tcache.base.BaseActivity;
import com.example.tao.tcache.bean.db.Collect;
import com.example.tao.tcache.bean.event.Event;
import com.example.tao.tcache.contract.Contract;
import com.example.tao.tcache.custom.SpaceItemDecoration;
import com.example.tao.tcache.presenter.CollectActivityPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * @author wangyz
 * @time 2019/1/28 14:19
 * @description CollectActivity
 */
public class CollectActivity extends BaseActivity<Contract.CollectActivityView, CollectActivityPresenter> implements Contract.CollectActivityView {


    @BindView(R.id.head)
    View head;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.activity_collect_article_list)
    RecyclerView activityCollectArticleList;
    @BindView(R.id.activity_collect_article_refresh)
    SmartRefreshLayout activityCollectArticleRefresh;
    @BindView(R.id.loading)
    WaveLoadingView loading;

    private Context mContext;

    private CollectArticleAdapter mAdapter;

    private int mPage;

    private List<Collect> mList = new ArrayList<>();

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        EventBus.getDefault().register(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        activityCollectArticleList.setLayoutManager(layoutManager);
        activityCollectArticleList.addItemDecoration(new SpaceItemDecoration(mContext.getResources().getDimensionPixelSize(R.dimen.main_list_item_margin)));

        mAdapter = new CollectArticleAdapter(this, mList);
        activityCollectArticleList.setAdapter(mAdapter);

        mPresenter.load(mPage);

        activityCollectArticleRefresh.setOnRefreshListener(refreshLayout -> {
            mPresenter.refresh(0);
        });

        activityCollectArticleRefresh.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            mPresenter.load(mPage);
        });
    }

    @Override
    protected CollectActivityPresenter createPresenter() {
        return new CollectActivityPresenter();
    }

    @Override
    public void onLoading() {
        LogUtils.i();
    }

    @Override
    public void onLoadSuccess() {
        LogUtils.i();
        activityCollectArticleRefresh.finishRefresh();
        activityCollectArticleRefresh.finishLoadMore();
    }

    @Override
    public void onLoadFailed() {
        LogUtils.e();
        ToastUtils.showShort(mContext.getString(R.string.load_failed));
        activityCollectArticleRefresh.finishRefresh();
        activityCollectArticleRefresh.finishLoadMore();
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onLoad(List<Collect> list) {
        if (loading.getVisibility() == View.VISIBLE) {
            loading.setVisibility(View.GONE);
        }
        if (list != null) {
            List<Collect> tempList = new ArrayList<>();
            list.stream().forEach(m -> {
                long count = mList.stream().filter(n -> n.articleId == m.articleId).count();
                if (count <= 0) {
                    tempList.add(m);
                }
            });
            mList.addAll(tempList);
            mAdapter.setList(mList);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onRefresh(List<Collect> list) {
        if (list != null) {
            List<Collect> tempList = new ArrayList<>();
            list.stream().forEach(m -> {
                long count = mList.stream().filter(n -> n.articleId == m.articleId).count();
                if (count <= 0) {
                    tempList.add(m);
                }
            });
            mList.addAll(0, tempList);
            mAdapter.setList(mList);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onUnCollect(com.example.tao.tcache.bean.model.Collect result, int articleId) {
        if (result != null) {
            if (result.getErrorCode() == 0) {
                List<Collect> tempList = mList.stream().filter(a -> a.articleId != articleId).collect(Collectors.toList());
                mList.clear();
                mList.addAll(tempList);
                mAdapter.setList(mList);
            } else {
                ToastUtils.showShort(mContext.getString(R.string.uncollect_failed));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_COLLECT) {
            if (event.type == Event.TYPE_UNCOLLECT) {
                int articleId = Integer.valueOf(event.data);
                mPresenter.unCollect(articleId);
            }
        }
    }

    @OnClick(R.id.back)
    public void back() {
        finish();
    }

}
