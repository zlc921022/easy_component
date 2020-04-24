package com.xiaochen.common.bluetooth.classicble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;


import com.xiaochen.common.bluetooth.utils.LogUtil;

import java.io.IOException;

/**
 * 服务端
 *
 * @author admin
 */
public class ServiceAcceptThread extends Thread {

    private BluetoothServerSocket mServerSocket;
    private ConnectedThread connectedThread;
    private ConnectState mConnectState;
    private Handler mHandler;

    public ServiceAcceptThread(Handler handler) {
        try {
            this.mHandler = handler;
            mConnectState = ConnectState.INSTANCE;
            mServerSocket = BluetoothAdapter.getDefaultAdapter().listenUsingRfcommWithServiceRecord(
                    DeviceConstants.device_name, DeviceConstants.Companion.getService_uuid());
            mConnectState.setMState(ConnectState.STATE_LISTEN);
        } catch (IOException e) {
            mConnectState.setMState(ConnectState.STATE_NONE);
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        setName("AcceptThread");
        if (mServerSocket == null) {
            return;
        }
        while (mConnectState.getMState() != ConnectState.STATE_CONNECTED) {
            BluetoothSocket socket;
            try {
                socket = mServerSocket.accept();
            } catch (IOException e) {
                break;
            }
            if (socket != null) {
                synchronized (ServiceAcceptThread.class) {
                    switch (mConnectState.getMState()) {
                        case ConnectState.STATE_LISTEN:
                        case ConnectState.STATE_CONNECTING:
                            createConnectedThread(socket);
                            writeData("hello,我是服务端".getBytes());
                            break;
                        case ConnectState.STATE_CONNECTED:
                            try {
                                mServerSocket.close();
                                LogUtil.e(getName(), "服务端的socket关闭");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        default:
                    }
                }
            }
        }
    }

    /**
     * 创建连接线程
     *
     * @param socket
     */
    private void createConnectedThread(BluetoothSocket socket) {
        if (connectedThread == null) {
            connectedThread = new ConnectedThread(socket, mHandler);
            connectedThread.start();
        }
    }

    /**
     * 往客户端写入数据
     *
     * @param bytes 数据
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
            connectedThread = null;
        }
    }

}