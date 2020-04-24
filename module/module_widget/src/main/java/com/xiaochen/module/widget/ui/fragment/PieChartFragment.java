package com.xiaochen.module.widget.ui.fragment;

import com.xiaochen.common.widget.view.PieChartView;
import com.xiaochen.module.widget.R;

/**
 * Created by zlc on 2018/7/16.
 */

public class PieChartFragment extends BaseFragment{

    private PieChartView mChartView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_piechart;
    }

    @Override
    protected void initView() {
        super.initView();
        mChartView = findView(R.id.pieChart);
    }

    @Override
    protected void initData() {
    }

    public void chartAnimation(){
        mChartView.setProgressAnimation(PieChartView.DURATION);
    }
}
