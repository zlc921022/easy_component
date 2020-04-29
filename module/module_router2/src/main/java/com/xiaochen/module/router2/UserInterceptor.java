package com.xiaochen.module.router2;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.xiaochen.common.utils.LogUtil;
import com.xiaochen.common.sdk.RouterPathConstant;

/**
 * <p>{d}</p>
 *
 * @author zhenglecheng
 * @date 2020/4/19
 */
@Interceptor(priority = 6)
public class UserInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if (RouterPathConstant.TEST_ACTIVITY2.equalsIgnoreCase(postcard.getPath())) {
            LogUtil.e("UserInterceptor process", "开始拦截操作");
           // callback.onInterrupt(new Exception("纯纯粹粹"));
        }
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {
        LogUtil.e("UserInterceptor init", "拦截器初始化");
    }
}
