package com.xiaochen.module.dagger2.component;

import com.google.gson.Gson;
import com.xiaochen.module.dagger2.module.AModule;
import com.xiaochen.module.dagger2.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/28
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    /**
     * 创建gson对象
     *
     * @return
     */
    Gson createGson();

    /**
     * 添加声明
     */
    AComponent plus(AModule module);
}
