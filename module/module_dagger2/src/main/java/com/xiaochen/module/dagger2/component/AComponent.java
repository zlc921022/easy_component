package com.xiaochen.module.dagger2.component;

import com.xiaochen.module.dagger2.Dagger2OtherActivity;
import com.xiaochen.module.dagger2.module.AModule;
import com.xiaochen.module.dagger2.scope.AScope;

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
    void inject(Dagger2OtherActivity activity);
}
