package com.xiaochen.module.widget.ui.fragment;

import com.xiaochen.common.widget.viewgroup.RangeSeekBar;
import com.xiaochen.module.widget.R;

/**
 * Created by zlc on 2018/7/16.
 */

public class RangeSeekBarFragment extends BaseFragment{

    private RangeSeekBar mSeekbar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_range_seekbar;
    }

    @Override
    protected void initView() {
        super.initView();
        mSeekbar = findView(R.id.seekbar);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void initListener() {
        super.initListener();
    }
}
