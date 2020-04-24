package com.xiaochen.common.bluetooth.fastble.service;

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.os.Handler
import android.os.Looper
import com.clj.fastble.data.BleDevice

/**
 * <p>蓝牙回调类</p >
 * @author     zhenglecheng
 * @date       2020/4/3
 */
class MyBluetoothGattCallback(listener: BleGattCallBackListener) :
    BluetoothGattCallback() {

    private val mCallbackListener = listener
    private val mHandler by lazy {
        Handler(Looper.getMainLooper())
    }

    //连接回调
    override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
        when (newState) {
            BluetoothGatt.STATE_CONNECTED -> {
                // 连接到开启服务加一个延迟时间
                mHandler.postDelayed({
                    gatt.discoverServices()
                }, 500)
            }
            BluetoothGatt.STATE_DISCONNECTED -> {
                runOnUiThread {
                    mCallbackListener.onBleDisconnect(gatt.device)
                }
            }
        }
    }

    //服务发现回调
    override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
        runOnUiThread {
            mCallbackListener.onBleConnect(BleDevice(gatt.device), isGattSuccess(status))
        }
    }

    //数据改变回调
    override fun onCharacteristicChanged(
        gatt: BluetoothGatt,
        characteristic: BluetoothGattCharacteristic?
    ) {
        characteristic?.run {
            runOnUiThread {
                mCallbackListener.onBleDataChanged(this.value)
            }
        }
    }

    //读取数据回调
    override fun onCharacteristicRead(
        gatt: BluetoothGatt,
        characteristic: BluetoothGattCharacteristic?,
        status: Int
    ) {
        characteristic?.run {
            runOnUiThread {
                mCallbackListener.onBleReadAndWrite(
                    "read",
                    this.value,
                    isGattSuccess(status)
                )
            }
        }
    }

    //写入数据回调
    override fun onCharacteristicWrite(
        gatt: BluetoothGatt,
        characteristic: BluetoothGattCharacteristic?,
        status: Int
    ) {
        characteristic?.run {
            runOnUiThread {
                mCallbackListener.onBleReadAndWrite(
                    "write",
                    this.value,
                    isGattSuccess(status)
                )
            }
        }
    }

    //通知描述符读取回调
    override fun onDescriptorRead(
        gatt: BluetoothGatt,
        descriptor: BluetoothGattDescriptor,
        status: Int
    ) {

    }

    //通知描述符写入回调
    override fun onDescriptorWrite(
        gatt: BluetoothGatt,
        descriptor: BluetoothGattDescriptor,
        status: Int
    ) {
        runOnUiThread {
            mCallbackListener.onBleNotification(isGattSuccess(status))
        }
    }

    /**
     * return 是否是成功状态
     */
    private fun isGattSuccess(status: Int): Boolean {
        return status == BluetoothGatt.GATT_SUCCESS;
    }

    /**
     * 使运行到主线程
     */
    private fun runOnUiThread(block: () -> Unit) {
        mHandler.post {
            block()
        }
    }
}