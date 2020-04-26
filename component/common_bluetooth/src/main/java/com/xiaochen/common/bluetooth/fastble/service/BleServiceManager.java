package com.xiaochen.common.bluetooth.fastble.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;


/**
 * Created by zlc on 2018/12/13
 * Email: zlc921022@163.com
 * Desc: 蓝牙服务帮助类
 *
 * @author zlc
 */
public class BleServiceManager {

    private BluetoothLeService mBluetoothLeService;
    private boolean mIsBind;
    private Handler mHandler = new Handler();

    private BleServiceManager() {
    }

    public static BleServiceManager getManager() {
        return ServiceManagerHolder.MANAGER;
    }

    private static class ServiceManagerHolder {
        private static final BleServiceManager MANAGER = new BleServiceManager();
    }

    /**
     * 开启服务
     */
    public void startService(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, BluetoothLeService.class);
            context.startService(intent);
        }
    }

    /**
     * 停止服务
     */
    public void stopService(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, BluetoothLeService.class);
            context.stopService(intent);
        }
    }

    /**
     * 绑定服务
     *
     * @param context
     */
    public void bindService(Context context) {
        if (context != null && !mIsBind) {
            Intent intent = new Intent(context, BluetoothLeService.class);
            mIsBind = context.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    /**
     * 解绑服务
     *
     * @param context
     */
    public void unBindService(Context context) {
        if (context != null && mIsBind) {
            mIsBind = false;
            context.unbindService(mServiceConnection);
        }
    }

    /**
     * 获取蓝牙连接服务对象
     */
    public BluetoothLeService getBleService() {
        return mBluetoothLeService;
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) iBinder).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e("BleServiceManager", "蓝牙没有初始化");
            }
            if (onBleServiceListener != null) {
                onBleServiceListener.onBleBindSuccess(mBluetoothLeService);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    /**
     * 打开蓝牙
     */
    public void enableBluetooth() {
        if (mBluetoothLeService != null) {
            mBluetoothLeService.enableBluetooth();
            mHandler.postDelayed(mBleRunnable, 1000);
        }
    }

    /**
     * 蓝牙打开状态runnable
     */
    private final Runnable mBleRunnable = new Runnable() {
        @Override
        public void run() {
            if (mBluetoothLeService.isBleEnable()) {
                mHandler.removeCallbacks(this);
                if (onBleServiceListener != null) {
                    onBleServiceListener.onBleOpen();
                }
            } else {
                mHandler.postDelayed(this, 1000);
            }
        }
    };


    /**
     * 服务绑定结果回调
     */
    public interface OnBleServiceListener {
        /**
         * 服务绑定的监听
         *
         * @param bluetoothLeService 服务对象
         */
        default void onBleBindSuccess(BluetoothLeService bluetoothLeService) {
        }

        /**
         * 蓝牙设备打开的回调方法
         */
        default void onBleOpen() {
        }
    }

    private OnBleServiceListener onBleServiceListener;

    public void setOnBleServiceListener(OnBleServiceListener onBleServiceListener) {
        this.onBleServiceListener = onBleServiceListener;
    }

    /**
     * 销毁服务
     */
    public void onDestroy() {
        if (onBleServiceListener != null) {
            onBleServiceListener = null;
        }
        if (mBluetoothLeService != null) {
            mBluetoothLeService.onDestroy();
        }
    }
}
