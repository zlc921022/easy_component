package com.xiaochen.module.bluetooth;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xiaochen.common.base.AppUtil;
import com.xiaochen.common.sdk.RouterPathConstant;
import com.xiaochen.common.service.CommonNameService;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/29
 */
@Route(path = RouterPathConstant.BLUETOOTH_SERVICE,name = "蓝牙服务")
public class BluetoothInfoServiceImpl implements CommonNameService {
    private Context mContext;
    @Override
    public String getModuleName() {
        return mContext.getString(R.string.module_bluetooth);
    }

    @Override
    public void init(Context context) {
        this.mContext = context;
    }
}
