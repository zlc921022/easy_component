package com.xiaochen.common.bluetooth.fastble.service;

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.LeScanCallback
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import com.clj.fastble.data.BleDevice
import java.util.*

/**
 * <p>蓝牙扫描帮助类{d}</p>
 * @author    zhenglecheng
 * @date      2020/4/4
 */
object BleScanManager {

    /**
     * 记录扫描到的设备列表
     */
    private val mScannedDevices: MutableList<BleDevice> by lazy {
        ArrayList<BleDevice>()
    }
    /**
     * handler
     */
    private val mMainHandler by lazy {
        Handler(Looper.getMainLooper())
    }
    /**
     * 蓝牙适配器
     */
    private var bluetoothAdapter: BluetoothAdapter? = null

    /**
     * 扫描判断
     */
    private var mIsScanning: Boolean = true
    /**
     * 扫描设备名称
     */
    private var mDeviceName: String? = null

    /**
     * 5.0 以上扫描设置
     */
    private val mScanSettings: ScanSettings? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val builder = ScanSettings.Builder()
                //SCAN_MODE_LOW_LATENCY 高功耗模式
                // SCAN_MODE_LOW_POWER 低功耗模式 默认
                .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                builder.setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
                builder.setMatchMode(ScanSettings.MATCH_MODE_STICKY)
            }
            builder.build()
        } else {
            null
        }
    }


    /**
     * 开始扫描
     * @param bluetoothAdapter 蓝牙适配器
     * @param deviceName 需要扫描的设备
     * @param scanTime 扫描时间
     */
    fun startScan(
        bluetoothAdapter: BluetoothAdapter, deviceName: String,
        scanTime: Long
    ) {
        //超出扫描时间则停止扫描
        this.mDeviceName = deviceName
        this.bluetoothAdapter = bluetoothAdapter
        mIsScanning = true
        mMainHandler.postDelayed(mScanRunnable, scanTime)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bluetoothAdapter.startLeScan(mLeScanCallback)
        } else {
            bluetoothAdapter.bluetoothLeScanner.startScan(null, mScanSettings, mScanCallBack)
        }
    }

    /**
     * 停止扫描
     */
    fun stopScan() {
        mIsScanning = false
        mMainHandler.removeCallbacks(mScanRunnable)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bluetoothAdapter?.stopLeScan(mLeScanCallback)
        } else {
            bluetoothAdapter?.bluetoothLeScanner?.stopScan(mScanCallBack)
        }
    }

    /**
     * 蓝牙扫描回调 5.0 以下
     */
    private val mLeScanCallback =
        LeScanCallback { device, rssi, scanRecord ->
            // 确保在主线程
            mMainHandler.post(Runnable {
                if (device != null && mDeviceName != null && mDeviceName.equals(
                        device.name,
                        ignoreCase = true
                    )
                ) {
                    scanAddDevice(device)
                }
            })
        }

    /**
     * 蓝牙扫描回调 5.0 以上
     */
    private val mScanCallBack: ScanCallback =
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        object : ScanCallback() {
            override fun onScanResult(
                callbackType: Int,
                result: ScanResult
            ) {
                super.onScanResult(callbackType, result)
                // 确保在主线程
                mMainHandler.post(Runnable {
                    if (mDeviceName != null && mDeviceName.equals(
                            result.device.name,
                            ignoreCase = true
                        )
                    ) {
                        scanAddDevice(result.device)
                    }
                })
            }
        }

    /**
     * 扫描添加设备
     */
    private fun scanAddDevice(device: BluetoothDevice) {
        if (mIsScanning) {
            val bleDevice = BleDevice(device)
            mScannedDevices.add(bleDevice)
            onBleScanCallBack?.onBleScan(bleDevice, true)
        }
    }

    /**
     * 扫描的runnable
     */
    private val mScanRunnable = Runnable {
        if (mIsScanning) {
            stopScan()
            if (mScannedDevices.isEmpty()) {
                onBleScanCallBack?.onBleScanFinished(null)
            } else {
                onBleScanCallBack?.onBleScanFinished(mScannedDevices)
            }
        }
    }

    var onBleScanCallBack: OnBleScanCallBack? = null
        set(value) {
            field = value
        }

    /**
     * 蓝牙扫描回调
     */
    interface OnBleScanCallBack {
        /**
         * 蓝牙扫描回调
         *
         * @param device 扫描到的设备集合
         */
        fun onBleScanFinished(device: MutableList<BleDevice>?) {}

        /**
         * 蓝牙扫描回调
         *
         * @param device  返回第一个扫描到的对象
         * @param success true 成功 false 失败
         */
        fun onBleScan(device: BleDevice?, success: Boolean)
    }
}