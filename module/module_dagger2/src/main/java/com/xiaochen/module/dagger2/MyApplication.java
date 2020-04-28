package com.xiaochen.module.dagger2;

import com.xiaochen.common.base.BaseApplication;
import com.xiaochen.module.dagger2.component.AComponent;
import com.xiaochen.module.dagger2.component.ApplicationComponent;
import com.xiaochen.module.dagger2.component.DaggerApplicationComponent;
import com.xiaochen.module.dagger2.module.AModule;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/28
 */
public class MyApplication extends BaseApplication {

    private static MyApplication mApplication;
    private ApplicationComponent mApplicationComponent;
    private AComponent mAComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mApplicationComponent = DaggerApplicationComponent.builder().build();
    }

    public static MyApplication getApplication() {
        return mApplication;
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public AComponent getAComponent() {
        if (mAComponent == null) {
            mAComponent = mApplicationComponent.plus(new AModule());
        }
        return mAComponent;
    }

}
