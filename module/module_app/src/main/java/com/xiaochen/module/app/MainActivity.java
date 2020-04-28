package com.xiaochen.module.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xiaochen.common.base.BaseActivity;
import com.xiaocheng.common.sdk.PathConstant;

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
            ARouter.getInstance().build(PathConstant.MVP_ACTIVITY).navigation();
        } else if (id == R.id.btn_1) {
            ARouter.getInstance().build(PathConstant.MVVM_ACTIVITY).navigation();
        } else if (id == R.id.btn_2) {
            ARouter.getInstance().build(PathConstant.JETPACK_ACTIVITY).navigation();
        } else if (id == R.id.btn_3) {
            ARouter.getInstance().build(PathConstant.BLUETOOTH_ACTIVITY).navigation();
        } else if (id == R.id.btn_4) {
            ARouter.getInstance().build(PathConstant.WIDGET_ACTIVITY).navigation();
        } else if (id == R.id.btn_5) {
            ARouter.getInstance().build(PathConstant.AROUTER_ACTIVITY).navigation();
        }else if(id == R.id.btn_6){
            ARouter.getInstance().build(PathConstant.DAGGER2_ACTIVITY).navigation();
        }
    }
}

