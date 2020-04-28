package com.xiaochen.module.dagger2.module;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/28
 */
@Module
public class ApplicationModule {
    @Singleton
    @Provides
    public Gson createGson() {
        return new Gson();
    }
}
