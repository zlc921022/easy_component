package com.xiaochen.module.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.xiaochen.common.base.BaseActivity;
import com.xiaochen.common.sdk.RouterManager;
import com.xiaochen.common.sdk.RouterPathConstant;
import com.xiaochen.common.service.CommonNameService;

/**
 * <p>{d}</p>
 *
 * @author zhenglecheng
 * @date 2020/4/21
 */
public class MainActivity extends BaseActivity {

    private Button mBtn0;
    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private Button mBtn4;
    private Button mBtn5;
    private Button mBtn6;

    @Autowired(name = RouterPathConstant.BLUETOOTH_SERVICE)
    CommonNameService mBluetoothInfoService;

    @Autowired(name = RouterPathConstant.DAGGER2_SERVICE)
    CommonNameService mDagger2InfoService;

    @Autowired(name = RouterPathConstant.JETPACK_SERVICE)
    CommonNameService mJetpackInfoService;

    @Autowired(name = RouterPathConstant.MVP_SERVICE)
    CommonNameService mMvpInfoService;

    @Autowired(name = RouterPathConstant.MVVM_SERVICE)
    CommonNameService mMvvmInfoService;

    @Autowired(name = RouterPathConstant.AROUTER_SERVICE)
    CommonNameService mArouterInfoService;

    @Autowired(name = RouterPathConstant.WIDGET_SERVICE)
    CommonNameService mWidgetInfoService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.app_activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        RouterManager.inject(this);

        find(R.id.back_button).setVisibility(View.GONE);
        TextView title = find(R.id.title_text);
        title.setText("easy_component");
        mBtn0 = find(R.id.btn_0);
        mBtn1 = find(R.id.btn_1);
        mBtn2 = find(R.id.btn_2);
        mBtn3 = find(R.id.btn_3);
        mBtn4 = find(R.id.btn_4);
        mBtn5 = find(R.id.btn_5);
        mBtn6 = find(R.id.btn_6);

        setModuleName();
    }

    private void setModuleName() {
        if (mMvpInfoService != null) {
            mBtn0.setText(mMvpInfoService.getModuleName());
        }
        if (mMvvmInfoService != null) {
            mBtn1.setText(mMvvmInfoService.getModuleName());
        }
        if (mJetpackInfoService != null) {
            mBtn2.setText(mJetpackInfoService.getModuleName());
        }
        if (mBluetoothInfoService != null) {
            mBtn3.setText(mBluetoothInfoService.getModuleName());
        }
        if (mWidgetInfoService != null) {
            mBtn4.setText(mWidgetInfoService.getModuleName());
        }
        if (mArouterInfoService != null) {
            mBtn5.setText(mArouterInfoService.getModuleName());
        }
        if (mDagger2InfoService != null) {
            mBtn6.setText(mDagger2InfoService.getModuleName());
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtn0.setOnClickListener(this);
        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);
        mBtn4.setOnClickListener(this);
        mBtn5.setOnClickListener(this);
        mBtn6.setOnClickListener(this);
    }

    private <T extends View> T find(int id) {
        return (T) findViewById(id);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.btn_0) {
            RouterManager.navigation(RouterPathConstant.MVP_ACTIVITY);
        } else if (id == R.id.btn_1) {
            RouterManager.navigation(RouterPathConstant.MVVM_ACTIVITY);
        } else if (id == R.id.btn_2) {
            RouterManager.navigation(RouterPathConstant.JETPACK_ACTIVITY);
        } else if (id == R.id.btn_3) {
            RouterManager.navigation(RouterPathConstant.BLUETOOTH_ACTIVITY);
        } else if (id == R.id.btn_4) {
            RouterManager.navigation(RouterPathConstant.WIDGET_ACTIVITY);
        } else if (id == R.id.btn_5) {
            RouterManager.navigation(RouterPathConstant.AROUTER_ACTIVITY);
        } else if (id == R.id.btn_6) {
            RouterManager.navigation(RouterPathConstant.DAGGER2_ACTIVITY);
        }
    }
}

