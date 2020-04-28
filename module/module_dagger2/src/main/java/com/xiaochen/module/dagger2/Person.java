package com.xiaochen.module.dagger2;


import javax.inject.Inject;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/28
 */
public class Person {
    private String name;

//    @Inject
    public Person() {
        name = "郑乐成";
    }

    @Inject
    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
