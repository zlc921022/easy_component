package com.xiaochen.common.mvp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;


/**
 * 所有使用mvp模式的activity的父类
 *
 * @param <T>
 * @author admin
 */
public abstract class BaseMvpActivity<T extends AbsBasePresenter> extends AppCompatActivity {

    protected T mPresenter;
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutResId() == 0) {
            return;
        }
        setContentView(getLayoutResId());
        setStatusBar();
        initView();
        initData();
        initListener();
    }

    /**
     * 设置沉浸式 状态栏
     */
    protected void setStatusBar() {
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setTranslucent(this);
    }

    /**
     * 控件初始化
     */
    protected void initView() {

    }


    /**
     * 数据处理
     */
    protected void initData() {
        mPresenter = getPresenter();
        getLifecycle().addObserver(mPresenter);
    }

    /**
     * 事件监听
     */
    protected void initListener() {

    }

    /**
     * 获取布局id
     *
     * @return id
     */
    protected int getLayoutResId() {
        return 0;
    }

    /**
     * 获取Presenter对象
     *
     * @return presenter
     */
    @NonNull
    protected abstract T getPresenter();

    @Override
    protected void onDestroy() {
        getLifecycle().removeObserver(mPresenter);
        super.onDestroy();
    }
}
