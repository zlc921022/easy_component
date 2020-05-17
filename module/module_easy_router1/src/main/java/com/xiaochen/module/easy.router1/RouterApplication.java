package com.xiaochen.module.easy.router1;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xiaochen.common.base.AppUtil;
import com.xiaochen.common.base.BaseApplication;
import com.xiaochen.easy.core.EasyRouter;

import androidx.multidex.MultiDex;
import timber.log.Timber;

/**
 * 父类application
 * 负责context和apiManager对象的创建和获取
 *
 * @author admin
 */
public class RouterApplication extends BaseApplication {

    protected boolean isDebug = true;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EasyRouter.init(this);
    }
}
