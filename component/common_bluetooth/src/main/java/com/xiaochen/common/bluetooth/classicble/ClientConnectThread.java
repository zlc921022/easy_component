package com.xiaochen.common.bluetooth.classicble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;

import com.xiaochen.common.bluetooth.utils.LogUtil;

import java.io.IOException;


/**
 * 客户端连接线程
 *
 * @author admin
 */
public class ClientConnectThread extends Thread {

    private BluetoothSocket mSocket;
    private final BluetoothAdapter mBluetoothAdapter;
    private ConnectedThread connectedThread;
    private final ConnectState connectState;
    public final static int CONNECT_SUCCESS = 3;
    private Handler mHandler;
    private final BluetoothDevice mDevice;

    public ClientConnectThread(BluetoothDevice device, Handler handler) {
        this.mDevice = device;
        mHandler = handler;
        connectState = ConnectState.INSTANCE;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            mSocket = device.createRfcommSocketToServiceRecord(DeviceConstants.Companion.getService_uuid());
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectState.setMState(ConnectState.STATE_CONNECTING);
    }

    @Override
    public void run() {
        if (mSocket == null) {
            return;
        }
        setName("ConnectThread");
        mBluetoothAdapter.cancelDiscovery();
        try {
            mSocket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e(getName() + " connect error", e.getMessage());
            try {
                mSocket.close();
                LogUtil.e(getName(), "客户端的socket关闭");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        sendMessage(CONNECT_SUCCESS, mDevice);
        createConnectedThread(mSocket);
    }

    private void createConnectedThread(BluetoothSocket socket) {
        connectedThread = new ConnectedThread(socket, mHandler);
        connectedThread.start();
    }

    /**
     * 发送消息
     *
     * @param what
     * @param obj
     */
    private void sendMessage(int what, Object obj) {
        Message msg = mHandler.obtainMessage();
        msg.what = what;
        msg.obj = obj;
        mHandler.sendMessage(msg);
    }

    /**
     * 数据写入
     */
    public void writeData(byte[] bytes) {
        if (connectedThread != null) {
            connectedThread.write(bytes);
        }
    }

    /**
     * 取消
     */
    public void cancel() {
        if (connectedThread != null) {
            connectedThread.cancel();
        }
    }
}