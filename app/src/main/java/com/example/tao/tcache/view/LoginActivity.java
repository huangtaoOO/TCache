package com.example.tao.tcache.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.tao.tcache.R;
import com.example.tao.tcache.base.BaseActivity;
import com.example.tao.tcache.bean.event.Event;
import com.example.tao.tcache.bean.model.Login;
import com.example.tao.tcache.contract.Contract;
import com.example.tao.tcache.presenter.LoginActivityPresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<Contract.LoginActivityView, LoginActivityPresenter>
        implements Contract.LoginActivityView {


    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.til_username)
    TextInputLayout tilUsername;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    private Context mContext;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = this;

        username.addTextChangedListener(new TextWatcher() {
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

        password.addTextChangedListener(new TextWatcher() {
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
    }

    @Override
    protected LoginActivityPresenter createPresenter() {
        return new LoginActivityPresenter();
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

    @Override
    public void onLogin(Login result) {
        LogUtils.i();
        if (result != null) {
            if (result.getErrorCode() == 0) {

                Event event = new Event();
                event.target = Event.TARGET_HOME;
                event.type = Event.TYPE_LOGIN;
                EventBus.getDefault().post(event);

                Event treeEvent = new Event();
                treeEvent.target = Event.TARGET_TREE;
                treeEvent.type = Event.TYPE_LOGIN;
                EventBus.getDefault().post(treeEvent);

                Event projectEvent = new Event();
                projectEvent.target = Event.TARGET_PROJECT;
                projectEvent.type = Event.TYPE_LOGIN;
                EventBus.getDefault().post(projectEvent);

                Event wxEvent = new Event();
                wxEvent.target = Event.TARGET_WX;
                wxEvent.type = Event.TYPE_LOGIN;
                EventBus.getDefault().post(wxEvent);

                Event menuEvent = new Event();
                menuEvent.target = Event.TARGET_MENU;
                menuEvent.type = Event.TYPE_LOGIN;
                menuEvent.data = username.getText().toString();
                EventBus.getDefault().post(menuEvent);

                finish();
            } else {
                ToastUtils.showShort(result.getErrorMsg());
            }
        }
    }

    @OnClick(R.id.back)
    public void back() {
        finish();
    }

    @OnClick(R.id.login)
    public void login() {
        if (TextUtils.isEmpty(username.getText()) || TextUtils.isEmpty(password.getText())) {
            ToastUtils.showShort(mContext.getString(R.string.complete_info));
            return;
        }
        mPresenter.login(username.getText().toString(), password.getText().toString());
    }

    @OnClick(R.id.go_register)
    public void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        mContext.startActivity(intent);
        finish();
    }

}
