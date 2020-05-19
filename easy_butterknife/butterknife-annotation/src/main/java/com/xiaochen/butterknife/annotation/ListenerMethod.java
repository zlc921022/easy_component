package com.xiaochen.butterknife.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/5/18
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface ListenerMethod {
    String name();

    String[] parameters() default {};

    String returnType() default "void";

    String defaultReturn() default "null";
}
