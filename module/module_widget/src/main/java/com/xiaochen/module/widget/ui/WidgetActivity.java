package com.xiaochen.module.widget.ui;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xiaochen.common.base.BaseActivity;
import com.xiaochen.common.sdk.RouterManager;
import com.xiaochen.module.widget.R;
import com.xiaochen.module.widget.ui.fragment.ViewFragment;
import com.xiaochen.module.widget.ui.fragment.ViewGroupFragment;
import com.xiaochen.common.sdk.RouterPathConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 自定义View Activity
 */
@Route(path = RouterPathConstant.WIDGET_ACTIVITY)
public class WidgetActivity extends BaseActivity {

    private RadioGroup mRgMain;
    private ViewFragment mFragment1;
    private ViewGroupFragment mFragment2;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RouterManager.inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_widget;
    }

    @Override
    public void initListener() {
        mRgMain.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.rb_view) {
                showIndexFragment(0);
            } else if (i == R.id.rb_viewgroup) {
                showIndexFragment(1);
            }
        });
    }

    @Override
    public void initView() {
        findViewById(R.id.back_button).setOnClickListener(v -> finish());
        TextView title = findViewById(R.id.title_text);
        title.setText("自定义控件");
        mRgMain = (RadioGroup) findViewById(R.id.rg_main);
    }

    @Override
    public void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(mFragment1 = new ViewFragment());
        mFragments.add(mFragment2 = new ViewGroupFragment());
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_main, mFragment1)
                .add(R.id.fl_main, mFragment2)
                .show(mFragment1)
                .hide(mFragment2)
                .commit();
    }

    public void showIndexFragment(int index) {
        getSupportFragmentManager()
                .beginTransaction()
                .show(mFragments.get(index))
                .hide(mFragments.get(mFragments.size() - 1 - index))
                .commit();
    }
}
