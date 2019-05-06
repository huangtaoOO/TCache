package com.example.tao.tcache.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.tao.tcache.ConstantValue;
import com.example.tao.tcache.R;
import com.example.tao.tcache.base.BaseActivity;
import com.example.tao.tcache.bean.model.KeyWord;
import com.example.tao.tcache.contract.Contract;
import com.example.tao.tcache.presenter.SearchActivityPresenter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.OnClick;
import me.itangqi.waveloadingview.WaveLoadingView;

public class SearchActivity extends BaseActivity<Contract.SearchActivityView, SearchActivityPresenter> implements Contract.SearchActivityView {

    @BindView(R.id.tag_hot_key)
    TagFlowLayout mTag;

    @BindView(R.id.key_word)
    EditText mKeyWord;

    @BindView(R.id.loading)
    WaveLoadingView mWaveLoadingView;

    private Context mContext;

    private List<String> mHotKeyList = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mContext = SearchActivity.this;

        mPresenter.loadKeyWord();

    }

    @Override
    protected SearchActivityPresenter createPresenter() {
        return new SearchActivityPresenter();
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

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onLoadKeyWord(KeyWord result) {
        if (mWaveLoadingView.getVisibility() == View.VISIBLE) {
            mWaveLoadingView.setVisibility(View.GONE);
        }
        if (result != null && result.getErrorCode() == 0) {
            mHotKeyList = result.getData().stream().map(k -> k.getName()).collect(Collectors.toList());
            mTag.setAdapter(new TagAdapter<String>(mHotKeyList) {
                @Override
                public View getView(FlowLayout flowLayout, int i, String s) {
                    TextView tv = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_tree_tag, mTag, false);
                    tv.setText(s);
                    return tv;
                }
            });
            mTag.setOnTagClickListener((view, i, flowLayout) -> {
                Intent intent = new Intent(mContext, SearchResultActivity.class);
                intent.putExtra(ConstantValue.KEY_KEYOWRD, mHotKeyList.get(i));
                mContext.startActivity(intent);
                return true;
            });
        }
    }

    @OnClick(R.id.back)
    public void back() {
        finish();
    }

    @OnClick(R.id.search)
    public void search() {
        if (TextUtils.isEmpty(mKeyWord.getText())) {
            ToastUtils.showShort(mContext.getString(R.string.input_key));
            return;
        }
        Intent intent = new Intent(mContext, SearchResultActivity.class);
        intent.putExtra(ConstantValue.KEY_KEYOWRD, mKeyWord.getText().toString());
        mContext.startActivity(intent);
    }
}
