package com.xiaochen.common.bluetooth.fastble.service;


import android.bluetooth.BluetoothDevice;

import com.clj.fastble.data.BleDevice;

import java.util.List;

/**
 * Created by zlc on 2018/12/25
 * Email: zlc921022@163.com
 * Desc: 蓝牙服务回调类
 */
public interface BleGattCallBackListener {

    /**
     * 蓝牙扫描回调
     *
     * @param device 扫描到的设备集合
     */
    default void onBleScanFinished(List<BleDevice> device) {
    }

    /**
     * 蓝牙扫描回调
     *
     * @param device  返回第一个扫描到的对象
     * @param success true 成功 false 失败
     */
    void onBleScan(BleDevice device, boolean success);

    /**
     * 蓝牙连接回调
     *
     * @param device  设备
     * @param success true 连接成功 false 连接失败
     */
    void onBleConnect(BleDevice device, boolean success);

    /**
     * 蓝牙发送通知回调
     *
     * @param success true 成功 false 失败
     */
    default void onBleNotification(boolean success) {
    }

    /**
     * 蓝牙读写回调
     *
     * @param action  read 读去数据 write 写入数据
     * @param bytes   读写的字节数组
     * @param success true 成功 false 失败
     */
    default void onBleReadAndWrite(String action, byte[] bytes, boolean success) {
    }

    /**
     * 蓝牙数据改变回调
     *
     * @param bytes 字节数组
     */
    default void onBleDataChanged(byte[] bytes) {
    }

    /**
     * 断开连接
     *
     * @param device 蓝牙设备
     */
    default void onBleDisconnect(BluetoothDevice device) {
    }
}
