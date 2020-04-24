package com.xiaochen.common.base;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

import androidx.multidex.MultiDex;
import timber.log.Timber;

/**
 * 父类application
 * 负责context和apiManager对象的创建和获取
 *
 * @author admin
 */
public class BaseApplication extends Application {

    protected boolean isDebug = true;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (isDebug) {
            Timber.plant(new Timber.DebugTree());
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        AppUtil.init(this);
    }
}
