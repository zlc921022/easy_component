package com.xiaochen.common.sdk

import android.app.Activity
import android.app.Application
import android.os.Bundle
import timber.log.Timber

/**
 * <p>页面生命周期监听</p >
 * @author     zhenglecheng
 * @date       2020/4/29
 */
class ActivityLifecycleCallBacksImpl : Application.ActivityLifecycleCallbacks {

    private val tag = "ActivityLifecycle"

    override fun onActivityPaused(activity: Activity?) {
        Timber.tag(tag).e("onActivityPaused : ${activity?.javaClass?.simpleName}")
    }

    override fun onActivityResumed(activity: Activity?) {
        Timber.tag(tag).e("onActivityResumed : ${activity?.javaClass?.simpleName}")
    }

    override fun onActivityStarted(activity: Activity?) {
        Timber.tag(tag).e("onActivityStarted : ${activity?.javaClass?.simpleName}")
    }

    override fun onActivityDestroyed(activity: Activity?) {
        Timber.tag(tag).e("onActivityDestroyed : ${activity?.javaClass?.simpleName}")
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        Timber.tag(tag).e("onActivitySaveInstanceState : ${activity?.javaClass?.simpleName}")
    }

    override fun onActivityStopped(activity: Activity?) {
        Timber.tag(tag).e("onActivityStopped : ${activity?.javaClass?.simpleName}")
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        Timber.tag(tag).e("onActivityCreated : ${activity?.javaClass?.simpleName}")
    }
}