package com.xiaochen.common.bluetooth.fastble.device;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.clj.fastble.data.BleDevice;
import com.xiaochen.common.bluetooth.fastble.constant.DeviceConstants;
import com.xiaochen.common.bluetooth.fastble.service.BleGattCallBackListener;
import com.xiaochen.common.bluetooth.fastble.service.BleServiceManager;
import com.xiaochen.common.bluetooth.fastble.service.BluetoothLeService;
import com.xiaochen.common.bluetooth.utils.LogUtil;
import com.xiaochen.common.bluetooth.utils.SpUtil;

import java.util.Set;

/**
 * Created by zlc on 2018/12/13
 * Email: zlc921022@163.com
 * Desc: 设备抽象类
 */
@TargetApi(18)
@SuppressWarnings("all")
public abstract class AbsBaseBleDevice {

    //切换蓝牙调用方式
    private static final int type = DeviceConstants.SERVICE;
    // 已经连接过的设备
    private BluetoothDevice mConnectedDevice;
    // TAG
    protected final String TAG = getClass().getSimpleName();
    // 连接的设备
    protected BluetoothDevice mDevice;
    // 蓝牙操作回调
    private BleGattCallBackListener mBleCallBack;

    protected AbsBaseBleDevice() {
    }

    /**
     * 设置蓝牙服务回调
     * 必须设置
     */
    public void setBleCallBack(BleGattCallBackListener bleCallBack) {
        if (mBleCallBack == bleCallBack) {
            return;
        }
        if (type == DeviceConstants.SERVICE) {
            ServiceBleBaseDevice.INSTANCE.setBleCallBack(bleCallBack, getDeviceName());
        } else if (type == DeviceConstants.FAST_BLE) {
            FastbleBaseDevice.INSTANCE.setBleGattCallBack(bleCallBack);
        }
    }

    /**
     * 移除蓝牙服务回调
     * 必须设置
     */
    public void removeBleCallBack(BleGattCallBackListener bleCallBack) {
        if (mBleCallBack == bleCallBack) {
            mBleCallBack = null;
        }
        stopScan();
        if (type == DeviceConstants.SERVICE) {
            ServiceBleBaseDevice.INSTANCE.removeBleCallBack(bleCallBack);
        } else if (type == DeviceConstants.FAST_BLE) {
            FastbleBaseDevice.INSTANCE.removeBleCallBack(bleCallBack);
        }
    }

    /**
     * 设置蓝牙服务
     *
     * @param service 蓝牙服务
     */
    public void setService(BluetoothLeService service) {
        ServiceBleBaseDevice.INSTANCE.setService(service);
    }

    /**
     * @return 获取蓝牙服务
     */
    public BluetoothLeService getService() {
        BluetoothLeService service = ServiceBleBaseDevice.INSTANCE.getMService();
        if (service == null) {
            return BleServiceManager.getManager().getBleService();
        } else {
            return service;
        }
    }

    /**
     * @return 获取蓝牙适配器
     */
    public BluetoothAdapter getBluetoothAdapter() {
        if (type == DeviceConstants.SERVICE) {
            return ServiceBleBaseDevice.INSTANCE.getBluetoothAdapter();
        } else {
            return FastbleBaseDevice.INSTANCE.getBluetoothAdapter();
        }
    }

    /**
     * 切换设备
     *
     * @param device 切换的设备
     */
    public void setCurrentDevice(BluetoothDevice device) {
        this.mDevice = device;
        if (type == DeviceConstants.SERVICE) {
            ServiceBleBaseDevice.INSTANCE.setMDevice(device);
        } else if (type == DeviceConstants.FAST_BLE) {
            FastbleBaseDevice.INSTANCE.setMBleDevice(new BleDevice(device));
        }
    }

    /**
     * @return 判断设备是否支持ble  true 支持 false 反之
     */
    public boolean isSupportBle() {
        if (type == DeviceConstants.SERVICE) {
            return ServiceBleBaseDevice.INSTANCE.isSupportBle();
        } else {
            return FastbleBaseDevice.INSTANCE.isSupportBle();
        }
    }

    /**
     * 判断蓝牙是否能用 true 是 false 否
     */
    public boolean isBleEnable() {
        if (type == DeviceConstants.SERVICE) {
            return ServiceBleBaseDevice.INSTANCE.isBleEnable();
        } else {
            return FastbleBaseDevice.INSTANCE.isBleEnable();
        }
    }

    /**
     * 打开蓝牙
     */
    public void enableBluetooth() {
        if (type == DeviceConstants.SERVICE) {
            ServiceBleBaseDevice.INSTANCE.enableBluetooth();
        } else {
            FastbleBaseDevice.INSTANCE.enableBluetooth();
        }
    }

    /**
     * 关闭蓝牙
     */
    public void disableBluetooth() {
        if (type == DeviceConstants.SERVICE) {
            ServiceBleBaseDevice.INSTANCE.disableBluetooth();
        } else {
            FastbleBaseDevice.INSTANCE.disableBluetooth();
        }
    }

    /**
     * @return true 扫描到一个设备就连接,同时停止扫描; false 直到扫描结束
     */
    public boolean isNeedConnect() {
        return true;
    }

    /**
     * 开始扫描
     */
    public void startScan() {
        if (type == DeviceConstants.SERVICE) {
            ServiceBleBaseDevice.INSTANCE.startScan();
        } else if (type == DeviceConstants.FAST_BLE) {
            FastbleBaseDevice.INSTANCE.fastBleScan(getDeviceName());
        }
    }

    /**
     * 停止扫描
     */
    public void stopScan() {
        if (type == DeviceConstants.SERVICE) {
            ServiceBleBaseDevice.INSTANCE.stopScan();
        } else if (type == DeviceConstants.FAST_BLE) {
            FastbleBaseDevice.INSTANCE.stopScan();
        }
    }

    /**
     * 连接蓝牙
     *
     * @param bleDevice 蓝牙设备封装类
     */
    public void connect(@NonNull BleDevice bleDevice) {
        this.mDevice = bleDevice.getDevice();
        if (type == DeviceConstants.SERVICE) {
            ServiceBleBaseDevice.INSTANCE.connect(mDevice);
        } else if (type == DeviceConstants.FAST_BLE) {
            FastbleBaseDevice.INSTANCE.fastbleConnect(bleDevice);
        }
    }

    /**
     * 连接蓝牙
     *
     * @param device 蓝牙设备
     */
    public void connect(@NonNull BluetoothDevice device) {
        connect(new BleDevice(device));
    }

    /**
     * 连接蓝牙通过设备地址
     *
     * @param address
     */
    public void connect(@NonNull String address) {
        this.mDevice = getBleDevice(address);
        if (type == DeviceConstants.SERVICE) {
            ServiceBleBaseDevice.INSTANCE.connect(address);
        } else if (type == DeviceConstants.FAST_BLE) {
            FastbleBaseDevice.INSTANCE.fastbleConnect(address);
        }
    }

    /**
     * 连接已连接过设备
     */
    public void connect() {
        if (mConnectedDevice == null) {
            LogUtil.e(TAG, "已连接设备为null");
        } else {
            connect(new BleDevice(mConnectedDevice));
        }
    }

    /**
     * 判断当前设备是否是已连接状态 true 已连接 false 反之
     */
    public boolean isConnected() {
        String connectedAddress = SpUtil.getConnectedAddress();
        if (TextUtils.isEmpty(connectedAddress)) {
            return false;
        }
        BluetoothDevice connectedDevice = getBleDevice(connectedAddress);
        // 说明本地有记录上次连接过的设备
        if (connectedDevice != null) {
            return isConnected(new BleDevice(connectedDevice));
        } else if (type == DeviceConstants.SERVICE) {
            return ServiceBleBaseDevice.INSTANCE.isConnected();
        } else {
            return FastbleBaseDevice.INSTANCE.isConnected();
        }
    }

    /**
     * 判断特定设备是否是已连接状态 true 已连接 false 反之
     */
    private boolean isConnected(@NonNull BleDevice bleDevice) {
        if (type == DeviceConstants.SERVICE) {
            ServiceBleBaseDevice.INSTANCE.setService(getService());
            return ServiceBleBaseDevice.INSTANCE.isConnected(bleDevice.getDevice());
        } else {
            return FastbleBaseDevice.INSTANCE.isConnected(bleDevice);
        }
    }

    /**
     * 判断特定设备是否是已连接状态 true 已连接 false 反之
     */
    public boolean isConnected(@NonNull BluetoothDevice device) {
        return isConnected(new BleDevice(device));
    }

    /**
     * @param address 蓝牙地址
     * @return 根据蓝牙地址获取蓝牙设备
     */
    public BluetoothDevice getBleDevice(String address) {
        if (type == DeviceConstants.SERVICE) {
            return ServiceBleBaseDevice.INSTANCE.getBleDevice(address);
        } else {
            return FastbleBaseDevice.INSTANCE.getBleDevice(address);
        }
    }

    /**
     * @return 获取已经配对的蓝牙列表
     */
    public Set<BluetoothDevice> getBondedDevices() {
        if (getBluetoothAdapter() == null) {
            return null;
        }
        return getBluetoothAdapter().getBondedDevices();
    }

    /**
     * 获取上次连接过的设备
     */
    public BluetoothDevice getConnectedDevice() {
        mConnectedDevice = getBleDevice(SpUtil.getConnectedAddress());
        return mConnectedDevice;
    }

    /**
     * 判断当前设备是否是本地连接的设备
     */
    public boolean isConnectedDevice(BluetoothDevice device) {
        if (TextUtils.equals(device.getAddress(), SpUtil.getConnectedAddress())) {
            return true;
        }
        return false;
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        disconnect(mDevice);
    }

    /**
     * 断开连接
     *
     * @param device 断开特定设备
     */
    public void disconnect(@NonNull BluetoothDevice device) {
        if (type == DeviceConstants.SERVICE) {
            ServiceBleBaseDevice.INSTANCE.disconnect(device);
        } else if (type == DeviceConstants.FAST_BLE) {
            FastbleBaseDevice.INSTANCE.disconnect(new BleDevice(device));
        }
    }

    /**
     * 发送通知
     */
    public void enableNotification() {
        if (type == DeviceConstants.SERVICE) {
            ServiceBleBaseDevice.INSTANCE.enableNotification();
        } else if (type == DeviceConstants.FAST_BLE) {
            FastbleBaseDevice.INSTANCE.fastbleEnableNotification();
        }
    }

    /**
     * 取消通知
     */
    public void disableNotification() {
        if (type == DeviceConstants.SERVICE) {
            ServiceBleBaseDevice.INSTANCE.disableNotification();
        } else if (type == DeviceConstants.FAST_BLE) {
            FastbleBaseDevice.INSTANCE.disableNotification();
        }
    }

    /**
     * 从设备中读取数据
     */
    protected void readData() {
        if (type == DeviceConstants.SERVICE) {
            ServiceBleBaseDevice.INSTANCE.readData();
        } else if (type == DeviceConstants.FAST_BLE) {
            FastbleBaseDevice.INSTANCE.fastbleReadData();
        }
    }

    /**
     * 往设备中写入字符串数据
     *
     * @param cmd 写入指令
     */
    public void writeStrData(String cmd) {
        if (type == DeviceConstants.SERVICE) {
            ServiceBleBaseDevice.INSTANCE.writeData(cmd);
        } else if (type == DeviceConstants.FAST_BLE) {
            FastbleBaseDevice.INSTANCE.fastBleWriteData(cmd);
        }
    }

    /**
     * 往设备种写入字节数组
     *
     * @param bytes 字节数组
     */
    public void writeBytes(byte[] bytes) {
        if (type == DeviceConstants.SERVICE) {
            ServiceBleBaseDevice.INSTANCE.writeData(bytes);
        } else if (type == DeviceConstants.FAST_BLE) {
            FastbleBaseDevice.INSTANCE.fastBleWriteData(bytes);
        }
    }

    /**
     * @return 返回操作蓝牙的类型
     */
    public int getBleType() {
        return type;
    }

    /**
     * 获取设备名称
     */
    @NonNull
    protected abstract String getDeviceName();

    /**
     * 释放资源
     */
    public void onDestroy() {
        if (type == DeviceConstants.SERVICE) {
            ServiceBleBaseDevice.INSTANCE.onDestroy();
        } else if (type == DeviceConstants.FAST_BLE) {
            FastbleBaseDevice.INSTANCE.onDestroy();
        }
    }
}
