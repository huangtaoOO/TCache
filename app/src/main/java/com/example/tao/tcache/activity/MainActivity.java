package com.example.tao.tcache.activity;

import android.view.View;

import com.example.tao.tcache.R;
import com.example.tao.tcache.basis.activity.BaseMvvmActivity;
import com.example.tao.tcache.databinding.ActivityMainBinding;
import com.example.tao.tcache.viewmodel.ViewModelOfMain;

/**
 * @author: tao
 * @time: 2019/3/10
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class MainActivity extends BaseMvvmActivity<ViewModelOfMain, ActivityMainBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected ViewModelOfMain setViewModel() {
        return new ViewModelOfMain();
    }

    @Override
    protected void initUI() {
        binding.tvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onClick(v);
            }
        });
    }
}
