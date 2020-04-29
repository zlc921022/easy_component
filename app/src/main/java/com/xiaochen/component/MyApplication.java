package com.xiaochen.component;


import com.xiaochen.common.base.BaseApplication;
import com.xiaochen.common.data.HttpManager;
import com.xiaochen.common.sdk.ActivityLifecycleCallBacksImpl;
import com.xiaochen.module.ApiConstants;

/**
 * <p>application</p >
 *
 * @author zhenglecheng
 * @date 2019/12/26
 */
public class MyApplication extends BaseApplication {

    private ActivityLifecycleCallBacksImpl mLifecycleCallBacks;

    @Override
    public void onCreate() {
        super.onCreate();
        HttpManager.createManager(getBaseUrl(), isDebug, this);
        mLifecycleCallBacks = new ActivityLifecycleCallBacksImpl();
        registerActivityLifecycleCallbacks(mLifecycleCallBacks);
    }

    private String getBaseUrl() {
        return ApiConstants.BASE_URL;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        unregisterActivityLifecycleCallbacks(mLifecycleCallBacks);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterActivityLifecycleCallbacks(mLifecycleCallBacks);
    }
}
