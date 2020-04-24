package com.xiaochen.module.bluetooth.activity;

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.xiaochen.common.bluetooth.utils.LogUtil
import com.xiaochen.module.bluetooth.R
import com.xiaochen.module.bluetooth.device.StethoscopeDevice
import kotlinx.android.synthetic.main.activity_bangle.*

/**
 * <p>手环连接和写入指令测试页面</p>
 * @author    zhenglecheng
 * @date      2020/4/4
 */
class BangleActivity : AbsBaseBleActivity<StethoscopeDevice>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_bangle)
        super.onCreate(savedInstanceState)
    }

    override fun initData() {
        super.initData()
        findViewById<ImageView>(R.id.back_button).setOnClickListener { finish() }
        val text = findViewById<TextView>(R.id.title_text)
        text.text = "低功耗蓝牙"
    }

    override fun initListener() {
        super.initListener()
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)
        button6.setOnClickListener(this)
        button7.setOnClickListener(this)
        button8.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.button1 -> {
            }
            R.id.button2 -> {
            }
            R.id.button3 -> {
            }
            R.id.button4 -> {
            }
            R.id.button5 -> {
            }
            R.id.button6 -> {
            }
            R.id.button7 -> {
            }
            R.id.button8 -> {
            }
        }
    }

    override fun getDevice(): StethoscopeDevice {
        return StethoscopeDevice
    }

    override fun onBleNotification(success: Boolean) {
        super.onBleNotification(success)
        LogUtil.e(TAG, "通知写入成功${success}")
    }

    override fun writeCmdSuccess(cmd: String?) {
        super.writeCmdSuccess(cmd)
        LogUtil.e("TAG : writeCmdSuccess", cmd + "")
    }

    override fun writeCmdOnConnected() {
        super.writeCmdOnConnected()
    }

    override fun getDataFromDevice(bytes: ByteArray) {
        super.getDataFromDevice(bytes)
        LogUtil.e("$TAG : getDataFromDevice", String(bytes))
    }
}