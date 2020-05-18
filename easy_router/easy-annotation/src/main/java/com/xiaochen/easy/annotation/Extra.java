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
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.CLASS)
public @interface Extra {
    String name() default "";
}
