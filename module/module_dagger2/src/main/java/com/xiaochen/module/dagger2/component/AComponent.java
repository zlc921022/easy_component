package com.xiaochen.module.dagger2.component;

import com.xiaochen.module.dagger2.MainActivity;
import com.xiaochen.module.dagger2.module.AModule;
import com.xiaochen.module.dagger2.scope.AScope;

import dagger.Component;
import dagger.Subcomponent;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/28
 */
@AScope
@Subcomponent(modules = AModule.class)
public interface AComponent {
    void inject(MainActivity activity);
}
