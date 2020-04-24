package com.xiaochen.module.bluetooth.device
import com.xiaochen.common.bluetooth.fastble.device.AbsBaseBleDevice
import kotlin.experimental.and

/**
 * <p>手环{d}</p>
 * @author    zhenglecheng
 * @date      2020/4/4
 */
object BangleDevice : AbsBaseBleDevice() {

    override fun getDeviceName(): String {
        return "J-Style GOGO"
    }

    /**
     * 返回字节数组
     * 针对步数 卡路里 距离 睡眠
     * @param cmd 指令名称
     * @param day 第几天
     */
    fun getBytes(cmd: String, day: String): ByteArray? {
        val b1 = cmd.toByte(16)
        val b2 = day.toByte(16)
        val bytes = ByteArray(16)
        var b: Byte = 0
        for (i in 0..14) {
            if (i == 0) {
                bytes[0] = b1
            } else if (i == 1) {
                bytes[1] = b2
            } else {
                bytes[i] = 0
            }
            b = (b + bytes[i]).toByte()
        }
        bytes[15] = (b and 0xFF.toByte())
        return bytes
    }
}