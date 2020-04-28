package com.xiaochen.module.dagger2.module;

import com.xiaochen.module.dagger2.Person;
import com.xiaochen.module.dagger2.Person1;
import com.xiaochen.module.dagger2.PersonQualifier;
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

    @PersonQualifier( "m")
    @AScope
    @Provides
    public Person1 getPerson() {
        return new Person1("小城");
    }

    @PersonQualifier( "w")
    @AScope
    @Provides
    public Person1 getGirlFriend() {
        return new Person1("小艳");
    }

}
