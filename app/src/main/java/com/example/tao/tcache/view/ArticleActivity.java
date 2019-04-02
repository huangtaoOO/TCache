package com.example.tao.tcache.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.example.tao.tcache.ConstantValue;
import com.example.tao.tcache.R;
import com.example.tao.tcache.base.BaseActivity;
import com.example.tao.tcache.contract.Contract;
import com.example.tao.tcache.presenter.ArticleActivityPresenter;
import com.just.agentweb.AgentWeb;

import org.apache.commons.lang3.StringEscapeUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author wangyz
 * @time 2019/1/18 17:34
 * @description ArticleActivity
 */
public class ArticleActivity extends BaseActivity<Contract.ArticleActivityView, ArticleActivityPresenter> implements Contract.ArticleActivityView {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.activity_article_container)
    LinearLayout activityArticleContainer;
    private String mLink;


    private AgentWeb mAgentWeb;

    @Override
    protected void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroy();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_article;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mLink = getIntent().getStringExtra(ConstantValue.KEY_LINK);
        if (TextUtils.isEmpty(mLink)) {
            return;
        }
        String title = getIntent().getStringExtra(ConstantValue.KEY_TITLE);
        if (!TextUtils.isEmpty(title)) {
            this.title.setText(StringEscapeUtils.unescapeHtml4(title));
        }

        mAgentWeb = AgentWeb.with(this).setAgentWebParent(activityArticleContainer, new LinearLayout.LayoutParams(-1, -1)).useDefaultIndicator().createAgentWeb().ready().go(mLink);
        mAgentWeb.getAgentWebSettings().getWebSettings().setUseWideViewPort(true);
        mAgentWeb.getAgentWebSettings().getWebSettings().setLoadWithOverviewMode(true);
    }

    @Override
    protected ArticleActivityPresenter createPresenter() {
        return new ArticleActivityPresenter();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onLoading() {
        LogUtils.i();
    }

    @Override
    public void onLoadSuccess() {
        LogUtils.i();
    }

    @Override
    public void onLoadFailed() {
        LogUtils.e();
    }

    @OnClick(R.id.back)
    public void back() {
        finish();
    }

    @OnClick(R.id.share)
    public void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, title.getText() + "\n" + mLink);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, "Share"));
    }

}
