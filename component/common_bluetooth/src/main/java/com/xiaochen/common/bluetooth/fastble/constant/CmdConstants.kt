package com.xiaochen.common.bluetooth.fastble.constant;

/**
 * <p>透传蓝牙-常用命令{d}</p>
 * @author    zhenglecheng
 * @date      2020/4/2
 */
class CmdConstants {
    companion object {
        private const val start = "zktt"
        // 0001-9999 设备号设置
        const val num_cmd = "$start+NAME"
        // 0 从机模式 （音频连接手机） 1 主机模式 音频连接蓝牙耳机
        const val mode_cmd = "$start+ROLE"
        // 心音模式
        const val heart_cmd = "$start+HEART"
        // 肺音模式
        const val lung_cmd = "$start+LUNG"
        // 一般模式
        const val common_cmd = "$start+ALL"
        // 音频模式查询
        const val search_cmd = "$start+QMODE"
        // 声音加
        const val voice_add_cmd = "$start+VOLU"
        // 声音减
        const val voice_sub_cmd = "$start+VOLD"
    }
}
