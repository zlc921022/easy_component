package com.xiaochen.module.mvvm;

import com.xiaochen.common.base.BaseApplication;
import com.xiaochen.common.data.HttpManager;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/22
 */
public class MvvmApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        HttpManager.createManager("https://www.wanandroid.com", isDebug, this);
    }
}
