package com.xiaochen.module.dagger2;

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
@Route(path = RouterPathConstant.DAGGER2_SERVICE,name = "dagger2服务")
public class Dagger2InfoServiceImpl implements CommonNameService {
    private Context mContext;
    @Override
    public String getModuleName() {
        return mContext.getString(R.string.module_dagger2);
    }

    @Override
    public void init(Context context) {
        this.mContext = context;
    }
}
