package com.xiaochen.module.dagger2.component;


import com.xiaochen.module.dagger2.Dagger2MainActivity;
import com.xiaochen.module.dagger2.module.MainModule;
import com.xiaochen.module.dagger2.module.PersonModule;
import com.xiaochen.module.dagger2.scope.PersonScope;

import dagger.Component;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/28
 */
@PersonScope
@Component(dependencies = ApplicationComponent.class, modules = {MainModule.class, PersonModule.class})
public interface MainComponent {
    /**
     * 注入MainActivity对象
     *
     * @param activity
     */
    void inject(Dagger2MainActivity activity);
}
