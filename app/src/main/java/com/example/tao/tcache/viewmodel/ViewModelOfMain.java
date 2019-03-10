package com.example.tao.tcache.viewmodel;

import android.view.View;

import com.example.tao.tcache.basis.net.NetworkCallBack;
import com.example.tao.tcache.basis.utils.LogUtil;
import com.example.tao.tcache.basis.viewmodel.BaseViewModel;
import com.example.tao.tcache.model.ModelOfMain;

import static com.example.tao.tcache.model.ModelOfMain.TEXT;

/**
 * @author: tao
 * @time: 2019/2/24
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class ViewModelOfMain extends BaseViewModel<ModelOfMain> implements NetworkCallBack {

    @Override
    protected ModelOfMain setModel() {
        return new ModelOfMain(this);
    }

    public void onClick(View v){
        LogUtil.i("ViewModelOfMain","1111111111111111111111");
        model.getData(TEXT);
    }

    @Override
    public void onSuccess(int tag, Object o) {
        switch (tag){
            case TEXT:
                LogUtil.i("ViewModelOfMain",o.toString());
                break;
        }
    }

    @Override
    public void onFailure(int tag, Object o) {
        LogUtil.i("ViewModelOfMain",o.toString());
    }
}
