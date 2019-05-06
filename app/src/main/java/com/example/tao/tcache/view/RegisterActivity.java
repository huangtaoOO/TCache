package com.example.tao.tcache.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.tao.tcache.R;
import com.example.tao.tcache.base.BaseActivity;
import com.example.tao.tcache.bean.model.Register;
import com.example.tao.tcache.contract.Contract;
import com.example.tao.tcache.presenter.RegisterActivityPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<Contract.RegisterActivityView, RegisterActivityPresenter> implements Contract.RegisterActivityView {

    @BindView(R.id.back)
    ImageView mBack;

    @BindView(R.id.username)
    EditText mUsername;

    @BindView(R.id.password)
    EditText mPassword;

    @BindView(R.id.repassword)
    EditText mRepassword;

    @BindView(R.id.register)
    Button mRegister;

    @BindView(R.id.til_rpassword)
    TextInputLayout tilRpassword;
    @BindView(R.id.til_username)
    TextInputLayout tilUsername;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;

    private Context mContext;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = this;
        mUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()!=0 && s.length()<6){
                    tilUsername.setHelperText("用户名不合规范");
                }else {
                    tilUsername.setHelperText("");
                }
            }
        });

        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()!=0 && s.length()<6){
                    tilPassword.setHelperText("密码不合规范");
                }else {
                    tilPassword.setHelperText("");
                }
            }
        });

        mRepassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String ms = s.toString();
                if (ms.isEmpty()) return;
                if (!ms.equals(mPassword.getText().toString())) {
                    tilRpassword.setHelperText("两次密码输入不一致");
                }else {
                    tilRpassword.setHelperText("");
                }
            }
        });
    }

    @Override
    protected RegisterActivityPresenter createPresenter() {
        return new RegisterActivityPresenter();
    }

    @Override
    public void onRegister(Register result) {
        LogUtils.i();
        if (result != null) {
            if (result.getErrorCode() == 0) {
                ToastUtils.showShort(mContext.getString(R.string.register_success));
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
                finish();
            } else {
                ToastUtils.showShort(result.getErrorMsg());
            }
        }
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

    @OnClick(R.id.register)
    public void register() {
        if (TextUtils.isEmpty(mUsername.getText()) || TextUtils.isEmpty(mPassword.getText()) || TextUtils.isEmpty(mRepassword.getText())) {
            ToastUtils.showShort(mContext.getString(R.string.complete_info));
            return;
        }
        if (!TextUtils.equals(mPassword.getText(), mRepassword.getText())) {
            ToastUtils.showShort(mContext.getString(R.string.password_not_match));
            return;
        }
        mPresenter.register(mUsername.getText().toString(), mPassword.getText().toString());
    }

}
