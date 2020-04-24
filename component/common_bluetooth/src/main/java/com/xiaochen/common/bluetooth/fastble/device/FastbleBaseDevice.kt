package com.xiaochen.common.bluetooth.fastble.device;

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.os.SystemClock
import android.text.TextUtils
import com.clj.fastble.BleManager
import com.clj.fastble.callback.*
import com.clj.fastble.data.BleDevice
import com.clj.fastble.exception.BleException
import com.clj.fastble.scan.BleScanRuleConfig
import com.xiaochen.common.bluetooth.utils.LogUtil
import com.xiaochen.common.bluetooth.fastble.constant.DeviceConstants
import com.xiaochen.common.bluetooth.fastble.constant.UUIDConstants
import com.xiaochen.common.bluetooth.fastble.service.BleGattCallBackListener
import java.util.*

/**
 * <p>fastble方式{d}</p>
 * @author    zhenglecheng
 * @date      2020/4/2
 */
object FastbleBaseDevice {

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
    //蓝牙设备对象
    var mBleDevice: BleDevice? = null
    private const val TAG = "FastbleDevice"
    //蓝牙回调监听类
    var mBleGattCallBack: BleGattCallBackListener? = null

    fun setBleGattCallBack(bleCallBack: BleGattCallBackListener) {
        this.mBleGattCallBack = bleCallBack
    }

    /**
     * 初始化
     */
    fun init(app: Application) {
        BleManager.getInstance().init(app)
    }

    /**
     * 获取蓝牙适配器
     */
    fun getBluetoothAdapter(): BluetoothAdapter? {
        return BleManager.getInstance().bluetoothAdapter
    }

    /**
     * 判断设备是否支持ble  true 支持 false 反之
     */
    fun isSupportBle(): Boolean {
        return BleManager.getInstance().isSupportBle
    }

    /**
     * 判断蓝牙是否能用
     */
    fun isBleEnable(): Boolean {
        return BleManager.getInstance().isBlueEnable
    }

    /**
     * 打开蓝牙
     */
    fun enableBluetooth() {
        BleManager.getInstance().enableBluetooth()
    }

    /**
     * fastble库开始扫描
     */
    fun fastBleScan(deviceName: String) {
        BleManager.getInstance().initScanRule(getScanRuleConfig(deviceName))
        BleManager.getInstance().scan(object : BleScanCallback() {
            override fun onScanning(bleDevice: BleDevice) {
                if (TextUtils.equals(
                                deviceName,
                                bleDevice.name
                        ) && mBleGattCallBack != null
                ) {
                    mBleGattCallBack?.onBleScan(bleDevice, true)
                }
            }

            override fun onScanFinished(scanResultList: MutableList<BleDevice>?) {
                if (scanResultList == null || scanResultList.isEmpty()) {
                    mBleGattCallBack?.onBleScanFinished(null)
                } else {
                    mBleGattCallBack?.onBleScanFinished(scanResultList)
                }
            }

            override fun onScanStarted(success: Boolean) {
                LogUtil.e(TAG, "开始扫描")
            }
        })
    }

    /**
     * 初始化扫描规则
     */
    private fun getScanRuleConfig(deviceName: String): BleScanRuleConfig? {
        return BleScanRuleConfig.Builder()
                .setServiceUuids(arrayOf(UUID.fromString(SERVICE_UUID)))
                .setDeviceName(true, deviceName)
                .setScanTimeOut(SCAN_TIME_OUT)
                .build()
    }


    /**
     * 停止扫描
     */
    fun stopScan() {
        BleManager.getInstance().cancelScan()
    }

    /**
     * 根据蓝牙地址连接蓝牙
     * @param address 蓝牙地址
     */
    fun fastbleConnect(address: String) {
        val device = getBleDevice(address)
        device?.run {
            fastbleConnect(BleDevice(this))
        }
    }

    /**
     * fastble库连接
     */
    fun fastbleConnect(bleDevice: BleDevice?) {
        if (mBleGattCallBack == null || bleDevice == null) {
            return
        }
        this.mBleDevice = bleDevice
        BleManager.getInstance().connect(bleDevice, object : BleGattCallback() {
            override fun onConnectFail(bleDevice: BleDevice, exception: BleException) {
                mBleGattCallBack?.onBleConnect(bleDevice, false)
            }

            override fun onStartConnect() {
                LogUtil.e(TAG, "开始连接")
            }

            override fun onDisConnected(isActiveDisConnected: Boolean, device: BleDevice?, gatt: BluetoothGatt?, status: Int) {
                LogUtil.e(TAG, "断开连接")
            }

            override fun onConnectSuccess(
                    bleDevice: BleDevice,
                    gatt: BluetoothGatt,
                    status: Int
            ) {
                mBleGattCallBack?.onBleConnect(bleDevice, true)
            }
        })
    }

    /**
     * 根据蓝牙地址获取蓝牙设备
     * @param address 蓝牙地址
     */
    fun getBleDevice(address: String): BluetoothDevice? {
        getBluetoothAdapter()?.run {
            return this.getRemoteDevice(address)
        }
        return null
    }

    /**
     * 设备是否已连接
     */
    fun isConnected(bleDevice: BleDevice?): Boolean {
        return bleDevice != null && BleManager.getInstance().isConnected(bleDevice)
    }

    /**
     * 设备是否已连接
     */
    fun isConnected(): Boolean {
        return isConnected(mBleDevice)
    }

    /**
     * 断开连接
     * @param device 断开特定设备
     */
    fun disconnect(device: BleDevice?) {
        if (device != null) {
            BleManager.getInstance().disconnect(device)
        }
    }

    /**
     * fastble发送通知
     */
    fun fastbleEnableNotification() {
        if (mBleGattCallBack == null || mBleDevice == null) {
            return
        }
        SystemClock.sleep(SLEEP_TIME)
        BleManager.getInstance()
                .notify(
                        mBleDevice, SERVICE_UUID, NOTIFY_CHAR_UUID,
                        object : BleNotifyCallback() {
                            override fun onNotifySuccess() {
                                mBleGattCallBack?.onBleNotification(true)
                            }

                            override fun onCharacteristicChanged(data: ByteArray) {
                                mBleGattCallBack?.onBleDataChanged(data)
                            }

                            override fun onNotifyFailure(exception: BleException?) {
                                LogUtil.e(TAG, "通知失败${exception.toString()}")
                            }
                        })
    }

    /**
     * fastble停止通知
     */
    fun disableNotification() {
        if (mBleDevice == null) {
            return
        }
        BleManager.getInstance().stopNotify(
                mBleDevice,
                SERVICE_UUID,
                NOTIFY_CHAR_UUID
        )
    }

    /**
     * fastble读取数据
     */
    fun fastbleReadData() {
        if (mBleGattCallBack == null || mBleDevice == null) {
            return
        }
        SystemClock.sleep(SLEEP_TIME)
        BleManager.getInstance()
                .read(
                        mBleDevice, SERVICE_UUID, READ_CHAR_UUID,
                        object : BleReadCallback() {
                            override fun onReadSuccess(data: ByteArray) {
                                mBleGattCallBack?.onBleReadAndWrite("read", data, true)
                            }

                            override fun onReadFailure(exception: BleException) {
                                mBleGattCallBack?.onBleReadAndWrite("read", null, false)
                            }
                        })
    }


    /**
     * 写入字符串数据
     *
     * @param cmd 指令
     */
    fun fastBleWriteData(cmd: String) {
        fastBleWriteData(cmd.toByteArray())
    }

    /**
     * 写入字节数组数据
     *
     * @param bytes 字节数组
     */
    fun fastBleWriteData(bytes: ByteArray) {
        if (mBleGattCallBack == null || mBleDevice == null) {
            return
        }
        BleManager.getInstance()
                .write(
                        mBleDevice, SERVICE_UUID, WRITE_CHAR_UUID,
                        bytes, object : BleWriteCallback() {
                    override fun onWriteSuccess(
                            current: Int,
                            total: Int,
                            write: ByteArray
                    ) {
                        mBleGattCallBack?.onBleReadAndWrite("write", write, true)
                    }

                    override fun onWriteFailure(exception: BleException) {
                        mBleGattCallBack?.onBleReadAndWrite("write", null, true)
                    }
                })
    }

    /**
     * 设备销毁方法
     */
    fun onDestroy() {
        BleManager.getInstance().destroy()
    }
}