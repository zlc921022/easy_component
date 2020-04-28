package com.xiaochen.module.dagger2.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.xiaochen.common.base.AppUtil;
import com.xiaochen.common.utils.SpUtil;

import dagger.Module;
import dagger.Provides;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/28
 */
@Module
public class MainModule {
    @Provides
    public SharedPreferences createSp(Context context) {
        return SpUtil.getSp(context);
    }

    @Provides
    public Context getContext() {
        return AppUtil.getContext();
    }
}
