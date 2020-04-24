package com.xiaochen.module.bluetooth.device

import com.xiaochen.common.bluetooth.fastble.device.AbsBaseBleDevice

/**
 * <p>听诊仪</p >
 * @author     zhenglecheng
 * @date       2020/4/13
 */
object StethoscopeDevice : AbsBaseBleDevice() {

    override fun getDeviceName(): String {
        return ""
    }
}