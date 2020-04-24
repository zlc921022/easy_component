package com.xiaochen.common.bluetooth.classicble;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;


import com.xiaochen.common.bluetooth.utils.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.annotation.NonNull;


/**
 * 接收服务端过来的数据
 *
 * @author admin
 */
public class ConnectedThread extends Thread {
    private InputStream inputStream;
    private OutputStream outputStream;
    private BluetoothSocket mSocket;
    private ConnectState connectState;
    public final static int READ_DATA = 1;
    public final static int WRITE_DATA = 2;
    private Handler mHandler;

    public ConnectedThread(BluetoothSocket socket, @NonNull Handler handler) {
        this.mSocket = socket;
        this.mHandler = handler;
        this.connectState = ConnectState.INSTANCE;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            connectState.setMState(ConnectState.STATE_NONE);
        }
        connectState.setMState(ConnectState.STATE_CONNECTED);
    }

    @Override
    public void run() {
        super.run();
        if (inputStream == null) {
            return;
        }
        setName("ConnectedThread");
        byte[] buffer = new byte[1024];
        while (connectState.getMState() == ConnectState.STATE_CONNECTED) {
            try {
                LogUtil.e(getName(), "已经连接");
                int length = inputStream.read(buffer);
                byte[] data = new byte[length];
                System.arraycopy(buffer, 0, data, 0, data.length);
                sendMessage(READ_DATA, data);
            } catch (IOException e) {
                e.printStackTrace();
                connectState.setMState(ConnectState.STATE_NONE);
                break;
            }
        }
    }

    /**
     * 往远程设备写入数据
     *
     * @param bytes 数据
     */
    public void write(byte[] bytes) {
        if (outputStream != null) {
            try {
                outputStream.write(bytes);
                //发送消息到主线程
                sendMessage(WRITE_DATA, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 取消
     */
    public void cancel() {
        if (mSocket == null) {
            return;
        }
        try {
            connectState.setMState(ConnectState.STATE_NONE);
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}