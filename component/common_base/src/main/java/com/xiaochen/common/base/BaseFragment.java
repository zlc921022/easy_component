package com.xiaochen.common.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * 父类fragment
 *
 * @author admin
 */
public class BaseFragment extends Fragment implements View.OnClickListener {

    protected Context mContext;
    protected View mView;
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
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
