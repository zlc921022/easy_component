package com.xiaochen.common.sdk

import org.greenrobot.eventbus.EventBus

/**
 * <p>eventbus二次封装</p >
 * @author     zhenglecheng
 * @date       2020/4/29
 */
class BusManager private constructor() {

    companion object {

        @JvmStatic
        fun register(subscriber: Any) {
            EventBus.getDefault().register(subscriber)
        }

        @JvmStatic
        fun post(event: Any) {
            EventBus.getDefault().post(event)
        }

        @JvmStatic
        fun postSticky(event: Any) {
            EventBus.getDefault().postSticky(event)
        }

        @JvmStatic
        fun unregister(subscriber: Any) {
            EventBus.getDefault().unregister(subscriber)
        }
    }
}