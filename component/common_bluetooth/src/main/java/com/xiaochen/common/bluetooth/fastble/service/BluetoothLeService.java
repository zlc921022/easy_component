
package com.xiaochen.common.bluetooth.fastble.service;

import android.annotation.TargetApi;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.clj.fastble.data.BleDevice;
import com.xiaochen.common.bluetooth.fastble.constant.UUIDConstants;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static android.bluetooth.BluetoothDevice.TRANSPORT_LE;

/**
 * Created by zlc on 2018/12/13
 * Email: zlc921022@163.com
 * Desc: 蓝牙服务类
 */
@TargetApi(18)
@SuppressWarnings("all")
public class BluetoothLeService extends Service {
    /**
     * 写描述的UUID
     */
    public static final UUID DESCRIPTOR_UUID = UUID.fromString(UUIDConstants.DESCRIPTOR_UUID);
    /**
     * 蓝牙相关
     */
    private BluetoothManager mBluetoothManager = null;
    private BluetoothAdapter mBluetoothAdapter = null;
    /**
     * 蓝牙服务回调
     */
    private BleGattCallBackListener mBleCallBack;
    /**
     * 设备名称 扫描指定名称的设备
     */
    private String mDeviceName;
    /**
     * 记录扫描到的设备列表
     */
    private List<BleDevice> mScannedDevices = new ArrayList<>();
    /**
     * 记录已连接的设备，管理多设备连接问题,最多只能同时连接7个设备
     */
    private final ConcurrentHashMap<BluetoothDevice, BluetoothGatt> mHashMap = new ConcurrentHashMap<>(8);
    /**
     * 蓝牙回调类
     */
    private final MyBluetoothGattCallback mGattCallback = new MyBluetoothGattCallback();
    /**
     * 扫描处理handler
     */
    private Handler mMainHandler = new Handler(Looper.getMainLooper());

    public BluetoothLeService() {
    }

    /**
     * 设置蓝牙服务回调
     */
    public void setBleGattCallBack(BleGattCallBackListener bleCallBack, String deviceName) {
        this.mBleCallBack = bleCallBack;
        this.mDeviceName = deviceName;
        mGattCallback.removeCallBackListener();
        mGattCallback.setMCallbackListener(bleCallBack);
    }

    /**
     * 移除服务回调
     *
     * @param bleCallBack 蓝牙服务回调
     */
    public void removeBleGattCallBack(BleGattCallBackListener bleCallBack) {
        if (mGattCallback != null && mGattCallback.getMCallbackListener() == bleCallBack) {
            mGattCallback.removeCallBackListener();
        }
    }

    /**
     * 蓝牙初始化
     */
    public boolean initialize() {
        mBluetoothManager = (BluetoothManager) this.getSystemService(Context.BLUETOOTH_SERVICE);
        if (mBluetoothManager != null && mBluetoothManager.getAdapter() != null) {
            mBluetoothAdapter = mBluetoothManager.getAdapter();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                BlePhySupportedManager.INSTANCE.le2PhySupport(mBluetoothManager.getAdapter());
            }
            return true;
        }
        return false;
    }

    /**
     * 获取蓝牙适配器
     */
    public BluetoothAdapter getBluetoothAdapter() {
        return mBluetoothAdapter;
    }

    /**
     * 蓝牙是否能用
     */
    public boolean isBleEnable() {
        return mBluetoothAdapter != null && mBluetoothAdapter.isEnabled();
    }

    /**
     * 打开蓝牙
     */
    public void enableBluetooth() {
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.enable();
        }
    }

    /**
     * 关闭蓝牙
     */
    public void disableBluetooth() {
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.disable();
        }
    }

    /**
     * @return true 扫描到一个设备就连接,同时停止扫描 false 直到扫描结束
     */
    public boolean isNeedConnect() {
        return true;
    }

    /**
     * 开始扫描
     *
     * @param scanTime 扫描时间
     */
    public void startScan(long scanTime) {
        if (getBluetoothAdapter() == null || BleScanManager.INSTANCE.getMIsScanning().get()) {
            return;
        }
        Log.e("蓝牙服务", "=====开始扫描====");
        BleScanManager.INSTANCE.setOnBleScanCallBack(new BleScanManager.OnBleScanCallBack() {
            @Override
            public void onBleScanFinished(@Nullable List<BleDevice> device) {
                if (mBleCallBack != null) {
                    mBleCallBack.onBleScanFinished(device);
                }
            }

            @Override
            public void onBleScan(@Nullable BleDevice device, boolean success) {
                if (mBleCallBack != null) {
                    mBleCallBack.onBleScan(device, success);
                }
            }
        });
        BleScanManager.INSTANCE.startScan(getBluetoothAdapter(), mDeviceName, scanTime);
    }

    /**
     * 停止扫描
     */
    public void stopScan() {
        Log.e("蓝牙服务", "=====停止扫描====");
        if (BleScanManager.INSTANCE.getMIsScanning().get()) {
            BleScanManager.INSTANCE.stopScan();
        }
    }

    /**
     * @param address 蓝牙地址
     * @return 根据蓝牙地址获得蓝牙设备
     */
    public BluetoothDevice getBleDevice(String address) {
        if (mBluetoothAdapter == null || TextUtils.isEmpty(address)) {
            return null;
        }
        return mBluetoothAdapter.getRemoteDevice(address);
    }

    /**
     * 蓝牙连接
     *
     * @param address 地址
     */
    public synchronized boolean connect(@NonNull String address) {
        return this.connect(getBleDevice(address));
    }

    /**
     * 蓝牙连接
     *
     * @param device 蓝牙设备
     */
    public synchronized boolean connect(@NonNull BluetoothDevice device) {
        if (this.mBluetoothAdapter == null || device == null) {
            return false;
        } else if (isConnected(device)) {
            return true;
        } else if (mHashMap.size() >= 8) {
            return false;
        } else {
            BluetoothGatt gatt;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && mBluetoothAdapter.isLe2MPhySupported()) {
                gatt = connectGatt(device, BluetoothDevice.PHY_LE_2M_MASK);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                gatt = device.connectGatt(this, false, this.mGattCallback, TRANSPORT_LE);
            } else {
                gatt = device.connectGatt(this, false, this.mGattCallback);
            }
            mHashMap.put(device, gatt);
            return true;
        }
    }

    public BluetoothGatt connectGatt(BluetoothDevice device, int phy) {
        return device.connectGatt(this, false, this.mGattCallback,
                BluetoothDevice.TRANSPORT_LE, phy);
    }

    /**
     * 当前设备是否是连接状态
     *
     * @param device
     */
    public boolean isConnected(BluetoothDevice device) {
        if (mBluetoothManager != null && device != null) {
            int state = this.mBluetoothManager.getConnectionState(device, BluetoothProfile.GATT);
            return state == BluetoothProfile.STATE_CONNECTED;
        }
        return false;
    }

    /**
     * 检查蓝牙对象BluetoothGatt能不能用
     */
    public boolean checkGatt(BluetoothGatt gatt) {
        if (this.mBluetoothAdapter == null) {
            return false;
        } else {
            return gatt != null;
        }
    }

    /**
     * 获取蓝牙设备支持的所有服务
     */
    public List<BluetoothGattService> getSupportedGattServices(BluetoothDevice device) {
        BluetoothGatt gatt = mHashMap.get(device);
        return gatt == null ? null : gatt.getServices();
    }

    /**
     * 发送一个通知
     *
     * @param enable true 开启通知 false 关闭通知
     */
    public synchronized boolean setCharacteristicNotification(BluetoothDevice device, BluetoothGattCharacteristic characteristic, boolean enable) {
        BluetoothGatt gatt = mHashMap.get(device);
        if (!this.checkGatt(gatt)) {
            return false;
        } else if (characteristic == null) {
            return false;
        } else if (!gatt.setCharacteristicNotification(characteristic, enable)) {
            return false;
        } else {
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(DESCRIPTOR_UUID);
            if (descriptor == null) {
                return false;
            } else {
                descriptor.setValue(enable ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE :
                        BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
                return gatt.writeDescriptor(descriptor);
            }
        }
    }

    /**
     * 从设备中读取数据
     */
    public synchronized void readCharacteristic(BluetoothDevice device, BluetoothGattCharacteristic characteristic) {
        BluetoothGatt gatt = mHashMap.get(device);
        if (this.checkGatt(gatt) && characteristic != null) {
            gatt.readCharacteristic(characteristic);
        }
    }

    /**
     * 往设备中写入数据
     *
     * @param bytes 字节数组
     */
    public synchronized boolean writeCharacteristic(BluetoothDevice device, BluetoothGattCharacteristic characteristic, byte[] bytes) {
        if (device == null || characteristic == null || bytes == null) {
            return false;
        }
        BluetoothGatt gatt = mHashMap.get(device);
        characteristic.setValue(bytes);
        return gatt != null && gatt.writeCharacteristic(characteristic);
    }

    public boolean writeCharacteristic(BluetoothDevice device, BluetoothGattCharacteristic characteristic, boolean b) {
        return writeCharacteristic(device, characteristic, new byte[]{(byte) (b ? 1 : 0)});
    }

    public boolean writeCharacteristic(BluetoothDevice device, BluetoothGattCharacteristic characteristic, @NonNull String value) {
        return writeCharacteristic(device, characteristic, value.getBytes());
    }


    /**
     * 断开连接
     *
     * @param address
     */
    public synchronized void disconnect(String address) {
        if (mBluetoothAdapter != null) {
            BluetoothDevice device = this.mBluetoothAdapter.getRemoteDevice(address);
            disconnect(device);
        }
    }

    /**
     * 断开连接
     */
    public synchronized void disconnect(@NonNull BluetoothDevice device) {
        if (mBluetoothManager == null) {
            return;
        }
        int state = this.mBluetoothManager.getConnectionState(device, BluetoothProfile.GATT);
        BluetoothGatt gatt = mHashMap.remove(device);
        if (gatt != null && state == BluetoothProfile.STATE_CONNECTED) {
            gatt.disconnect();
        } else {
            this.stopScan();
        }
    }

    /**
     * 关闭连接
     */
    public synchronized void close(BluetoothDevice device) {
        BluetoothGatt gatt = mHashMap.remove(device);
        if (gatt != null) {
            gatt.close();
            gatt = null;
        }
    }

    /**
     * 关闭所有已经连接的设备
     */
    public synchronized void closeAllDevice() {
        Iterator<Map.Entry<BluetoothDevice, BluetoothGatt>> it = mHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<BluetoothDevice, BluetoothGatt> entry = it.next();
            if (isConnected(entry.getKey())) {
                close(entry.getKey());
            }
        }
        if (mHashMap.size() > 0) {
            mHashMap.clear();
        }
    }

    /**
     * 获取蓝牙操作对象
     */
    public BluetoothGatt getBluetoothGatt(BluetoothDevice device) {
        return mHashMap.get(device);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent var1) {
        return new LocalBinder();
    }

    @Override
    public void onDestroy() {
        closeAllDevice();
        if (this.mBleCallBack != null) {
            this.mBleCallBack = null;
        }
        super.onDestroy();
    }

    /**
     * 定义binder类 获取蓝牙服务对象
     */
    public final class LocalBinder extends Binder {
        public BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }
}
