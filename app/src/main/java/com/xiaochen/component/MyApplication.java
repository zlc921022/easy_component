package com.xiaochen.component;


import com.xiaochen.common.base.BaseApplication;
import com.xiaochen.common.data.HttpManager;
import com.xiaochen.module.ApiConstants;

/**
 * <p>application</p >
 *
 * @author zhenglecheng
 * @date 2019/12/26
 */
public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpManager.createManager(getBaseUrl(), isDebug, this);
    }

    private String getBaseUrl() {
        return ApiConstants.BASE_URL;
    }
}
