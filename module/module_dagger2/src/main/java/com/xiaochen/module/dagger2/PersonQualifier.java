package com.xiaochen.module.dagger2;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/28
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PersonQualifier {
    String value() default "";
}
