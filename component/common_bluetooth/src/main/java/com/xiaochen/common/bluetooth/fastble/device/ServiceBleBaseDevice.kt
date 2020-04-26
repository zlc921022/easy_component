package com.xiaochen.common.bluetooth.fastble.device;

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import android.content.pm.PackageManager
import android.os.Build
import android.os.SystemClock
import android.text.TextUtils
import com.xiaochen.common.bluetooth.fastble.constant.DeviceConstants
import com.xiaochen.common.bluetooth.fastble.constant.UUIDConstants
import com.xiaochen.common.bluetooth.fastble.service.BleGattCallBackListener
import com.xiaochen.common.bluetooth.fastble.service.BluetoothLeService
import java.util.*

/**
 * <p>服务形式{d}</p>
 * @author    zhenglecheng
 * @date      2020/4/2
 */
object ServiceBleBaseDevice {

    //服务UUID
    private const val SERVICE_UUID = UUIDConstants.SERVICE_UUID
    //通知UUID
    private const val NOTIFY_CHAR_UUID = UUIDConstants.NOTIFY_CHAR_UUID
    //读UUID
    private const val READ_CHAR_UUID = UUIDConstants.READ_CHAR_UUID
    //写UUID
    private const val WRITE_CHAR_UUID = UUIDConstants.WRITE_CHAR_UUID
    //扫描时间
    private const val SCAN_TIME_OUT = DeviceConstants.SCAN_TIME_OUT
    //指令直接间隔发送时间
    private const val SLEEP_TIME = DeviceConstants.SLEEP_TIME
    //蓝牙服务
    var mService: BluetoothLeService? = null
    //蓝牙设备
    var mDevice: BluetoothDevice? = null

    /**
     * 设置蓝牙服务
     *
     * @param service
     */
    fun setService(service: BluetoothLeService) {
        this.mService = service
    }


    /**
     * 设置蓝牙服务回调
     * 必须设置
     */
    fun setBleCallBack(bleCallBack: BleGattCallBackListener?, deviceName: String) {
        mService?.setBleGattCallBack(bleCallBack, deviceName)
    }

    /**
     * 移除蓝牙服务回调
     * 必须设置
     */
    fun removeBleCallBack(bleCallBack: BleGattCallBackListener) {
        mService?.removeBleGattCallBack(bleCallBack)
    }

    /**
     * 获取蓝牙适配器
     */
    fun getBluetoothAdapter(): BluetoothAdapter? {
        return BluetoothAdapter.getDefaultAdapter()
    }

    /**
     * 判断设备是否支持ble  true 支持 false 反之
     *
     */
    fun isSupportBle(): Boolean {
        mService?.applicationContext?.let {
            return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
                    && it.packageManager.hasSystemFeature(
                    PackageManager.FEATURE_BLUETOOTH_LE
            ))
        }
        return false
    }

    /**
     * 判断蓝牙是否能用
     */
    fun isBleEnable(): Boolean {
        return getBluetoothAdapter()?.isEnabled ?: false
    }

    /**
     * 打开蓝牙
     */
    fun enableBluetooth() {
        mService?.enableBluetooth()
    }

    /**
     * 关闭蓝牙
     */
    fun disableBluetooth() {
        mService?.disableBluetooth()
    }

    /**
     * 开始扫描
     */
    fun startScan() {
        SystemClock.sleep(SLEEP_TIME)
        mService?.startScan(SCAN_TIME_OUT)
    }

    /**
     * 停止扫描
     */
    fun stopScan() {
        mService?.stopScan()
    }

    /**
     * 根据蓝牙地址连接蓝牙
     * @param address 蓝牙地址
     */
    fun connect(address: String) {
        val device = getBleDevice(address)
        device?.run {
            connect(this)
        }
    }

    /**
     * 连接蓝牙
     */
    fun connect(device: BluetoothDevice?) {
        this.mDevice = device
        device?.let {
            mService?.connect(device)
        }
    }

    /**
     * 根据蓝牙地址获取蓝牙设备
     * @param address 蓝牙地址
     */
    fun getBleDevice(address: String): BluetoothDevice? {
        return mService?.getBleDevice(address)
    }

    /**
     * 设备是否已经连接
     */
    fun isConnected(device: BluetoothDevice?): Boolean {
        return mService?.isConnected(device) ?: false
    }

    /**
     * 设备是否已经连接
     */
    fun isConnected(): Boolean {
        return mDevice != null && isConnected(mDevice)
    }

    /**
     * 断开连接
     */
    fun disconnect(device: BluetoothDevice) {
        this.mService?.disconnect(device)
    }

    /**
     * 发送通知
     */
    fun enableNotification() {
        mDevice?.run {
            val characteristic: BluetoothGattCharacteristic? =
                    getCharacteristic(NOTIFY_CHAR_UUID)
            mService?.setCharacteristicNotification(this, characteristic, true)
        }
    }

    /**
     * 取消通知
     */
    fun disableNotification() {
        mDevice?.run {
            val characteristic =
                    getCharacteristic(NOTIFY_CHAR_UUID)
            mService?.setCharacteristicNotification(this, characteristic, false)
        }
    }

    /**
     * 从设备中读取数据
     */
    fun readData() {
        mDevice?.run {
            SystemClock.sleep(SLEEP_TIME)
            mService?.readCharacteristic(this, getCharacteristic(READ_CHAR_UUID))
        }
    }

    /**
     * 往设备中写入字符串数据
     *
     * @param cmd 写入指令
     */
    fun writeData(cmd: String) {
        writeData(cmd.toByteArray())
    }

    /**
     * 往设备中写入字节数组
     *
     * @param bytes 字节数组
     */
    fun writeData(bytes: ByteArray) {
        mDevice?.run {
            val characteristic =
                    getCharacteristic(WRITE_CHAR_UUID)
            mService?.writeCharacteristic(this, characteristic, bytes)
        }
    }

    /**
     * 获取读写的特征值
     *
     * @param uuidString uuid
     */
    private fun getCharacteristic(uuidString: String): BluetoothGattCharacteristic? {
        val gattService: BluetoothGattService? = getGattService()
        return if (gattService != null && !TextUtils.isEmpty(uuidString)) {
            gattService.getCharacteristic(UUID.fromString(uuidString))
        } else null
    }

    /**
     * 获取特定UUID对应的服务
     *
     */
    private fun getGattService(): BluetoothGattService? {
        mService?.let {
            val gattServices = it.getSupportedGattServices(mDevice) ?: return null
            for (service in gattServices) {
                if (service.uuid == UUID.fromString(SERVICE_UUID)) {
                    return service
                }
            }
        }
        return null
    }

    /**
     * 设备销毁方法
     */
    fun onDestroy() {
        this.mService?.onDestroy()
    }
}