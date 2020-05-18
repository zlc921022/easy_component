package com.xiaochen.easy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: xiaochen
 * Create Date: 2020/05/16
 * Email: zlc921022@163.com
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Route {
    /**
     * 路由的路径
     */
    String path();

    /**
     * 将路由节点进行分组，可以实现动态加载
     */
    String group() default "";

}
