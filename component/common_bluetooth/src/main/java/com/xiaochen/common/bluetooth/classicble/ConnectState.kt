package com.xiaochen.common.bluetooth.classicble
/**
 * <p>蓝牙连接状态类</p >
 * @author     zhenglecheng
 * @date       2020/4/8
 */
object ConnectState {
    // 初始状态
    const val STATE_NONE = 0
    // 服务端监听状态
    const val STATE_LISTEN = 1
    // 连接状态
    const val STATE_CONNECTING = 2
    // 连接成功状态
    const val STATE_CONNECTED = 3
    // 状态
    var mState = STATE_NONE
}