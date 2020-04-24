package com.xiaochen.common.mvp;


import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * <p>presenter基类</p >
 *
 * @author zhenglecheng
 * @date 2019/11/20
 */
public class BasePresenter implements LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreateView(LifecycleOwner lifecycleOwner) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDetachView(LifecycleOwner lifecycleOwner) {
    }

}
