package com.xiaochen.common.bluetooth.classicble
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message

/**
 * <p>经典蓝牙管理类{d}</p>
 * @author    zhenglecheng
 * @date      2020/4/7
 */
object ClassicBleManager {

    private var mDevice: BluetoothDevice? = null
    private var mServiceAcceptThread: ServiceAcceptThread? = null
    private var mClientConnectThread: ClientConnectThread? = null

    private val mHandler by lazy {
        object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when (msg.what) {
                    // 连接成功
                    ClientConnectThread.CONNECT_SUCCESS -> {
                        mOnBleHfpCallBack?.onConnectSuccess(msg.obj as BluetoothDevice)
                    }
                    // 读取数据成功
                    ConnectedThread.READ_DATA -> {
                        mOnBleHfpCallBack?.onReadData(msg.obj as ByteArray)
                    }
                    // 写入数据成功
                    ConnectedThread.WRITE_DATA -> {
                        mOnBleHfpCallBack?.onWriteData(msg.obj as ByteArray)
                    }
                }
            }
        }
    }

    private val receiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.let {
                    if (BluetoothDevice.ACTION_FOUND == it.action) {
                        val device =
                            it.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE) as BluetoothDevice
                        mOnBleHfpCallBack?.onScanning(device)
                    }
                }
            }
        }
    }


    /**
     * 注册扫描广播
     */
    fun registerScanReceiver(context: Context) {
        // 注册扫描的广播
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(receiver, filter)
    }

    /**
     * 开始扫描
     */
    fun startDiscovery() {
        BluetoothAdapter.getDefaultAdapter().startDiscovery()
    }

    /**
     * 停止扫描
     */
    fun canDiscovery() {
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
    }

    /**
     * 开启蓝牙服务端
     */
    fun startService() {
        if (mServiceAcceptThread == null) {
            mServiceAcceptThread =
                ServiceAcceptThread(mHandler)
            mServiceAcceptThread?.start()
        }
    }

    /**
     * 开始连接
     */
    fun connect(device: BluetoothDevice) {
        if (mClientConnectThread == null) {
            mClientConnectThread =
                ClientConnectThread(
                    device,
                    mHandler
                )
            mClientConnectThread?.start()
        }
    }

    /**
     * 写入数据
     * @param type server 服务端写入数据 client 客户端写入数据
     * @param bytes 写入的字节数组
     */
    fun writeData(type: String, bytes: ByteArray) {
        if (type == "server") {
            mServiceAcceptThread?.writeData(bytes)
        } else {
            mClientConnectThread?.writeData(bytes)
        }
    }

    /**
     * 配对
     */
    fun createBond(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mDevice?.createBond() ?: false
        } else {
            false
        }
    }

    /**
     * 解除配对
     */
    fun removeBond() {
        try {
            val clasz = mDevice?.javaClass
            val method = clasz?.getMethod("removeBond")
            method?.invoke(mDevice)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 是否连接成功
     */
    fun isConnected(): Boolean {
        return mClientConnectThread?.state ?: 0 == ConnectState.STATE_CONNECTED
    }

    /**
     * 断开连接
     */
    fun cancel() {
        if (mClientConnectThread != null) {
            mClientConnectThread?.cancel()
            mClientConnectThread = null
        }
        if (mServiceAcceptThread != null) {
            mServiceAcceptThread?.cancel()
            mServiceAcceptThread = null
        }
    }

    fun onDestroy(context: Context) {
        cancel()
        context.unregisterReceiver(receiver)
    }

    interface OnBleHfpCallBack {
        /**
         * 扫描
         */
        fun onScanning(device: BluetoothDevice)

        /**
         * 连接成功
         */
        fun onConnectSuccess(device: BluetoothDevice)

        /**
         * 数据读取回调
         */
        fun onReadData(bytes: ByteArray)

        /**
         * 数据写入回调
         */
        fun onWriteData(bytes: ByteArray)
    }

    var mOnBleHfpCallBack: OnBleHfpCallBack? = null
    fun setOnBleHfpCallBack(onBleHfpCallBack: OnBleHfpCallBack) {
        this.mOnBleHfpCallBack = onBleHfpCallBack
    }

}