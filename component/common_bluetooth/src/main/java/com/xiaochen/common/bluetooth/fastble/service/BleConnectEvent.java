package com.xiaochen.common.bluetooth.fastble.service;

/**
 * @author zlc
 * email : zlc921022@163.com
 * desc : 蓝牙连接
 */
public class BleConnectEvent {
    private boolean isSuccess;

    public BleConnectEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
