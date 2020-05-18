package com.xiaochen.butterknife.annotation;

import androidx.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>给控件设置点击事件</p >
 *
 * @author zhenglecheng
 * @date 2020/5/18
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ListenerClass(
        targetType = "android.view.View",
        setter = "setOnClickListener",
        method = @ListenerMethod(
                name = "onClick",
                parameters = "android.view.View"
        )
)
public @interface OnClick {
    @IdRes int[] value() default {
            -1
    };
}
