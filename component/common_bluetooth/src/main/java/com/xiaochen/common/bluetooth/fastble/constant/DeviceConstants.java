package com.xiaochen.common.bluetooth.fastble.constant;

/**
 * @author zlc
 * email : zlc921022@163.com
 * desc : 切换蓝牙操作方式类
 */
public final class DeviceConstants {
    /**
     * 用服务形式操作蓝牙
     */
    public static final int SERVICE = 0;
    /**
     * fastble库操作蓝牙
     */
    public static final int FAST_BLE = 1;
    /**
     * 指令直接间隔发送时间
     */
    public static final long SLEEP_TIME = 100;
    /**
     * 扫描时间
     */
    public static final long SCAN_TIME_OUT = 60 * 1000;
}
