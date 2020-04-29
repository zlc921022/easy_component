package com.xiaochen.module.mvvm;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xiaochen.common.sdk.RouterPathConstant;
import com.xiaochen.common.service.CommonNameService;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/29
 */
@Route(path = RouterPathConstant.MVVM_SERVICE,name = "mvvm服务")
public class MvvmInfoServiceImpl implements CommonNameService {
    private Context mContext;
    @Override
    public String getModuleName() {
        return mContext.getString(R.string.module_mvvm);
    }

    @Override
    public void init(Context context) {
        this.mContext = context;
    }
}
