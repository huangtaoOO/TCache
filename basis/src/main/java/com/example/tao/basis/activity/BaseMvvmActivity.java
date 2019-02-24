package com.example.tao.basis.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.tao.basis.utils.BR;
import com.example.tao.basis.viewmodel.BaseViewModel;

/**
 * @author: tao
 * @time: 2019/2/24
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public abstract class BaseMvvmActivity <VM extends BaseViewModel,VDB extends
        ViewDataBinding> extends AppCompatActivity {

    protected VDB binding;
    protected VM viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = setViewModel();
        doBeforeBinding();
        onBinding();
        initUI();
        setDefaultObservers();
    }

    protected void setDefaultObservers() {
    }

    protected abstract int getLayoutId();

    protected void doBeforeBinding(){}

    protected void onBinding(){
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setLifecycleOwner(this);
        binding.setVariable(BR.viewModel,viewModel);
    }

    protected abstract VM setViewModel();

    protected abstract void initUI();

    public void changeIntoLightStatusBar(boolean isLight){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (isLight){
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }else{
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
    }
}
