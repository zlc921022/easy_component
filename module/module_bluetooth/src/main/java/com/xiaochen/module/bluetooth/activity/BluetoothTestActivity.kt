package com.xiaochen.module.bluetooth.activity;

import android.bluetooth.BluetoothDevice
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.xiaochen.common.base.BaseActivity
import com.xiaochen.common.bluetooth.classicble.ClassicBleManager
import com.xiaochen.common.utils.LogUtil
import com.xiaochen.common.utils.ToastUtil
import com.xiaochen.module.bluetooth.R
import com.xiaocheng.common.sdk.PathConstant
import kotlinx.android.synthetic.main.activity_bluetooth_test.*


/**
 * <p>传统蓝牙测试类</p >
 * @author     zhenglecheng
 * @date       2020/4/7
 */
@Route(path = PathConstant.BLUETOOTH_ACTIVITY)
class BluetoothTestActivity : BaseActivity(), View.OnClickListener {

    private val tag: String = "BluetoothTestActivity"

    private val mSearchAdapter by lazy {
        ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
    }

    private val mDevices by lazy {
        ArrayList<BluetoothDevice>()
    }

    private val mActivity by lazy {
        this
    }


    override fun getLayoutResId(): Int {
        return R.layout.activity_bluetooth_test
    }

    override fun initData() {
        findViewById<ImageView>(R.id.back_button).setOnClickListener { finish() }
        val text = findViewById<TextView>(R.id.title_text)
        text.text = "经典蓝牙"
        listView.adapter = mSearchAdapter
        ClassicBleManager.registerScanReceiver(this)
        ClassicBleManager.setOnBleHfpCallBack(object : ClassicBleManager.OnBleHfpCallBack {

            override fun onScanning(device: BluetoothDevice) {
                if (device.name == null) {
                    return
                }
                mDevices.add(device)
                mSearchAdapter.add(device.name + "\n" + device.address)
                mSearchAdapter.notifyDataSetChanged()
            }

            override fun onConnectSuccess(device: BluetoothDevice) {
                LogUtil.e(tag, "设备连接成功")
            }

            override fun onReadData(bytes: ByteArray) {
                LogUtil.e(tag, "读取成功的数据 : ${String(bytes)}")
                ToastUtil.showShortToast(mActivity, String(bytes))
            }

            override fun onWriteData(bytes: ByteArray) {
                LogUtil.e(tag, "写入成功的数据 : ${String(bytes)}")
            }
        })
    }

    override fun initListener() {
        start_scan.setOnClickListener(this)
        start_service.setOnClickListener(this)
        service_send.setOnClickListener(this)
        client_send.setOnClickListener(this)
        cancel.setOnClickListener(this)
        listView.setOnItemClickListener { _, _, position, _ ->
            val device = mDevices[position]
            ClassicBleManager.connect(device)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.start_scan -> {
                ClassicBleManager.startDiscovery()
            }
            R.id.start_service -> {
                ClassicBleManager.startService()
            }
            R.id.service_send -> {
                ClassicBleManager.writeData("server", "客户端，你好".toByteArray())
            }
            R.id.client_send -> {
                ClassicBleManager.writeData("client", "服务端，你好".toByteArray())
            }
            R.id.cancel -> {
                ClassicBleManager.cancel()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ClassicBleManager.onDestroy(this)
    }
}