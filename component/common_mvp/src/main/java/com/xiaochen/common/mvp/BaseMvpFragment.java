package com.xiaochen.common.mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

/**
 * 所有使用mvp模式的fragment的父类
 *
 * @param <T>
 * @author zlc
 */
public abstract class BaseMvpFragment<T extends AbsBasePresenter> extends Fragment {

    protected View mView;
    protected T mPresenter;
    protected final String TAG = this.getClass().getSimpleName();


    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (getLayoutResId() == 0) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        return mView = inflater.inflate(getLayoutResId(), container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mView == null) {
            return;
        }
        initView();
        initData();
        initListener();
    }

    /**
     * 控件初始化
     */
    protected void initView() {

    }

    /**
     * 数据加载
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
    public void onDestroyView() {
        getLifecycle().removeObserver(mPresenter);
        super.onDestroyView();
    }

}
