package com.xiaochen.module.dagger2.component;


import com.xiaochen.module.dagger2.MainActivity;
import com.xiaochen.module.dagger2.MyApplication;
import com.xiaochen.module.dagger2.OtherActivity;
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
public abstract class MainComponent {
    /**
     * 注入MainActivity对象
     *
     * @param activity
     */
    public abstract void inject(MainActivity activity);

    /**
     * 注入MainActivity对象
     *
     * @param activity
     */
    public abstract void inject(OtherActivity activity);

    private static MainComponent sComponent;

    public static MainComponent getInstance() {
        if (sComponent == null) {
            sComponent = DaggerMainComponent.builder()
                    .applicationComponent(MyApplication.getApplication()
                            .getApplicationComponent())
                    .build();
        }
        return sComponent;
    }
}
