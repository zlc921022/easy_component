package com.xiaochen.common.sdk

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.facade.service.SerializationService
import com.alibaba.android.arouter.launcher.ARouter

/**
 * <p>arouter封装类</p >
 * @author     zhenglecheng
 * @date       2020/4/29
 */
class RouterManager private constructor() {

    companion object {

        @JvmStatic
        fun inject(thiz: Any) {
            ARouter.getInstance().inject(thiz)
        }

        @JvmStatic
        fun <T> navigation(service: Class<out T>?): T {
            return ARouter.getInstance().navigation(service)
        }

        @JvmStatic
        fun navigation(path: String) {
            ARouter.getInstance().build(path).navigation()
        }

        @JvmStatic
        fun navigation(path: String, group: String) {
            ARouter.getInstance().build(path, group).navigation()
        }

        @JvmStatic
        fun navigation(url: Uri) {
            ARouter.getInstance().build(url).navigation()
        }

        @JvmStatic
        fun navigation(context: Context, url: Uri, navCallback: NavCallback) {
            ARouter.getInstance().build(url).navigation(context, navCallback)
        }

        @JvmStatic
        fun navigation(context: Context, path: String, navCallback: NavCallback) {
            ARouter.getInstance().build(path).navigation(context, navCallback)
        }

        @JvmStatic
        fun navigation(context: Activity, path: String, requestCode: Int) {
            ARouter.getInstance().build(path).navigation(context, requestCode)
        }

        @JvmStatic
        fun navigation(path: String, bundle: Bundle) {
            ARouter.getInstance()
                    .build(path)
                    .with(bundle)
                    .navigation()
        }

        @JvmStatic
        fun navigation(path: String, key: String, value: Any) {
            ARouter.getInstance()
                    .build(path)
                    .withObject(key, value)
                    .navigation()
        }

        @JvmStatic
        fun getFragment(path: String): Fragment? {
            val navigation = ARouter.getInstance().build(path).navigation()
            if (navigation is Fragment) {
                return navigation
            }
            return null
        }
    }
}