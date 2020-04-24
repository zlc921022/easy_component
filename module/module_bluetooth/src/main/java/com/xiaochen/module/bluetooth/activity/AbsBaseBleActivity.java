package com.xiaochen.module.bluetooth.activity;


import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.clj.fastble.data.BleDevice;
import com.xiaochen.common.base.BaseActivity;
import com.xiaochen.common.base.CommonDialog;
import com.xiaochen.common.bluetooth.fastble.constant.DeviceConstants;
import com.xiaochen.common.bluetooth.fastble.device.AbsBaseBleDevice;
import com.xiaochen.common.bluetooth.fastble.service.BleConnectEvent;
import com.xiaochen.common.bluetooth.fastble.service.BleGattCallBackListener;
import com.xiaochen.common.bluetooth.fastble.service.BleServiceManager;
import com.xiaochen.common.bluetooth.fastble.service.BluetoothLeService;
import com.xiaochen.common.bluetooth.utils.LogUtil;
import com.xiaochen.common.utils.SpUtil;
import com.xiaochen.common.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * @author zlc
 * email : zlc921022@163.com
 * desc : 蓝牙的父类
 */
public abstract class AbsBaseBleActivity<V extends AbsBaseBleDevice> extends BaseActivity implements
        BleServiceManager.OnBleServiceListener, BleGattCallBackListener {

    protected V mDevice;
    protected Activity mActivity;
    /**
     * 重连次数
     */
    private int mConnectTimes = 0;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private CommonDialog mDialog;
    private static final String TAG = "AbsBleBaseActivity";
    private BleServiceManager mServiceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mDevice = getDevice();
        super.onCreate(savedInstanceState);
        this.mActivity = this;
    }

    @Override
    public void initData() {
        super.initData();
        mServiceManager = BleServiceManager.getManager();
        mHandler.postDelayed(this::initBluetooth, 50);
    }

    /**
     * 蓝牙初始化
     */
    public void initBluetooth() {
        if (!mDevice.isBleEnable()) {
            showBleOpenDialog();
        } else if (!mDevice.isSupportBle()) {
            ToastUtil.showShortToast(this, "当前手机不支持低功耗蓝牙!");
        } else if (mDevice.isConnected()) {
            mDevice.setBleCallBack(this);
            writeCmdOnConnected();
        } else {
            connect();
        }
    }

    /**
     * 连接蓝牙 服务形式或者fastble方式
     */
    private void connect() {
        if (mDevice.getBleType() == DeviceConstants.SERVICE) {
            mServiceManager.setOnBleServiceListener(this);
            mServiceManager.startService(getApplicationContext());
            mServiceManager.bindService(this);
        } else {
            scanConnectBle();
        }
    }

    /**
     * 蓝牙扫描或者连接
     */
    private void scanConnectBle() {
        mDevice.setBleCallBack(this);
        if (mDevice.getConnectedDevice() != null) {
            mDevice.connect();
        } else {
            mDevice.startScan();
        }
    }

    /**
     * 服务绑定成功
     */
    @Override
    public void onBleBindSuccess(BluetoothLeService service) {
        mDevice.setService(service);
        scanConnectBle();
    }

    /**
     * 蓝牙打开之后调用连接方法
     */
    @Override
    public void onBleOpen() {
        connect();
    }

    /**
     * 蓝牙扫描回调
     */
    @Override
    public void onBleScan(BleDevice device, boolean success) {
        if (success) {
            if (mDevice.isNeedConnect()) {
                mDevice.stopScan();
                mDevice.connect(device);
            } else {
                // todo 可以不断添加到扫描到的设备
            }
        } else {
            dismissLoadingDialog();
            ToastUtil.showShortToast(this, "设备扫描失败!");
        }
    }

    @Override
    public void onBleScanFinished(List<BleDevice> device) {
        if (device == null) {
            ToastUtil.showShortToast(this, "设备扫描失败!");
        } else {
            // todo 拿到扫描的数据集合
        }
    }

    /**
     * 蓝牙连接回调
     */
    @Override
    public void onBleConnect(BleDevice device, boolean success) {
        try {
            if (success) {
                SpUtil.saveBleDeviceAddress(device.getDevice());
                ToastUtil.showShortToast(this, "连接成功");
                mDevice.enableNotification();
                dismissLoadingDialog();
                EventBus.getDefault().post(new BleConnectEvent(true));
            } else if (++mConnectTimes < 3) {
                mDevice.connect(device);
                showReconnectLoading();
                ToastUtil.showShortToast(this, "连接失败");
            } else {
                dismissLoadingDialog();
                ToastUtil.showShortToast(this, "连接失败");
                EventBus.getDefault().post(new BleConnectEvent(false));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, "蓝牙连接error : " + e.getMessage());
        }
    }

    /**
     * 断开连接
     *
     * @param device 蓝牙设备
     */
    @Override
    public void onBleDisconnect(BluetoothDevice device) {
        //断开连接发送一个消息
        EventBus.getDefault().post(new BleConnectEvent(false));
    }

    /**
     * 通知写入回调
     *
     * @param success true 成功 false 失败
     */
    @Override
    public void onBleNotification(boolean success) {
        if (success) {
            // 发送指令
        }
    }

    /**
     * 蓝牙设备读写回调
     *
     * @param action  read 读成功 write 写入成功
     * @param bytes   写入成功返回的数据
     * @param success true 成功 false 失败
     */
    @Override
    public void onBleReadAndWrite(String action, byte[] bytes, boolean success) {
        if (TextUtils.equals(action, "write")) {
            LogUtil.e(TAG + " 数据写入成功", success + "");
            if (success) {
                String s = new String(bytes);
                writeCmdSuccess(s);
            } else {
                writeCmdFailure(bytes, new Exception("指令写入失败"));
            }
        }
    }

    /**
     * 蓝牙设备数据改变回调  获取设备返回的数据
     *
     * @param bytes 返回的字节数组
     */
    @Override
    public void onBleDataChanged(byte[] bytes) {
        getDataFromDevice(bytes);
    }

    /**
     * @return 获取具体要操作的蓝牙设备
     */
    @NonNull
    protected abstract V getDevice();

    /**
     * @param bytes 获取设备返回的数据
     */
    protected void getDataFromDevice(byte[] bytes) {

    }

    /**
     * 显示蓝牙打开对话框
     */
    protected void showBleOpenDialog() {
        if (mActivity != null) {
            mDialog = CommonDialog.getDialog(mActivity);
            mDialog.setContent("请先打开蓝牙设备").showDialog();
            mDialog.setOnDialogBtnClickListener(() -> mServiceManager.enableBluetooth());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (mDevice != null) {
            if (isDisconnectOnDestroy()) {
                mDevice.onDestroy();
            }
            if (mDevice.getBleType() == DeviceConstants.SERVICE) {
                BleServiceManager.getManager().unBindService(this);
            }
        }
    }

    /**
     * 退出页面是否断开蓝牙连接
     */
    protected boolean isDisconnectOnDestroy() {
        return false;
    }

    /**
     * 显示重连对话框
     */
    private void showReconnectLoading() {
        // todo
    }

    /**
     * 关闭loading
     */
    protected void dismissLoadingDialog() {
        // todo
    }

    /**
     * 指令写入成功方法
     *
     * @param cmd 写入成功的指令名称
     */
    protected void writeCmdSuccess(String cmd) {

    }

    /**
     * 已连接情况下写入指令
     */
    protected void writeCmdOnConnected() {

    }

    /**
     * 数据写入失败
     */
    protected void writeCmdFailure(byte[] bytes, Exception e) {

    }
}
