package com.xiaochen.common.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;


/**
 * 父类activity
 *
 * @author admin
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutResId() != 0) {
            setContentView(getLayoutResId());
            setStatusBar();
            initView();
            initData();
            initListener();
        }
    }

    /**
     * 设置沉浸式 状态栏
     */
    protected void setStatusBar() {
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setColorNoTranslucent(this, AppUtil.getColor(R.color.colorPrimary));
    }

    /**
     * @return 返回布局资源id
     */
    protected int getLayoutResId() {
        return 0;
    }

    /**
     * 初始化控件
     */
    protected void initView() {

    }

    /**
     * 加载数据
     */
    protected void initData() {

    }

    /**
     * 点击事件处理
     */
    protected void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
