package com.xiaochen.module.jetpack;

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
@Route(path = RouterPathConstant.JETPACK_SERVICE,name = "jetpack服务")
public class JetpackInfoServiceImpl implements CommonNameService {
    private Context mContext;
    @Override
    public String getModuleName() {
        return mContext.getString(R.string.module_jetpack);
    }

    @Override
    public void init(Context context) {
        this.mContext = context;
    }
}
