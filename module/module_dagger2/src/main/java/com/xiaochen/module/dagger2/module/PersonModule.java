package com.xiaochen.module.dagger2.module;

import com.xiaochen.module.dagger2.Person;
import com.xiaochen.module.dagger2.scope.PersonScope;

import dagger.Module;
import dagger.Provides;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/28
 */

@Module
public class PersonModule {

    @PersonScope
    @Provides
    public Person createPerson(String name) {
        return new Person(name);
    }

    @Provides
    public String createName() {
        return "小成";
    }
}
