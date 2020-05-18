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
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListenerClass {

    String targetType();

    String setter();

    ListenerMethod[] method() default {};
}
