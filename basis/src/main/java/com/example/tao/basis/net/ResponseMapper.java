package com.example.tao.basis.net;

import com.example.tao.basis.bean.BeanResponse;
import com.example.tao.basis.utils.LogUtil;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author: tao
 * @time: 2019/1/12
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class ResponseMapper<T> implements Function<BeanResponse<T>, T> {
    private static ResponseMapper INSTANCE = new ResponseMapper();

    private ResponseMapper() {
    }

    public static ResponseMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public T apply(@NonNull BeanResponse<T> tBeanResponse)throws Exception{
        if (tBeanResponse.isSuccess()){
            return tBeanResponse.getData();
        }else {
            LogUtil.e("网络错误","ResponseMapper：status = " + tBeanResponse.getStatus());
            throw new Exception();
        }
    }
}
