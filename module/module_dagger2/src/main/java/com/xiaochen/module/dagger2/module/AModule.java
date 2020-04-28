package com.xiaochen.module.dagger2.module;

import com.xiaochen.module.dagger2.Person;
import com.xiaochen.module.dagger2.scope.AScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/28
 */
@Module
public class AModule {

    @Named( "m")
    @AScope
    @Provides
    public Person getPerson() {
        return new Person("小城");
    }

    @Named( "w")
    @AScope
    @Provides
    public Person getGirlFriend() {
        return new Person("小艳");
    }

}
