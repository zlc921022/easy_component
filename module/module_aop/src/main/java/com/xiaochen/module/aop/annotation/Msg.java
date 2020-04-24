package com.xiaochen.module.aop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>{d}</p>
 *
 * @author zhenglecheng
 * @date 2020-02-28
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Msg {
    String DEFAULT_MSG = "msg";
    String msg() default DEFAULT_MSG;
}
