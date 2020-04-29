package com.xiaochen.module.dagger2;

import android.widget.TextView;

import com.google.gson.Gson;
import com.xiaochen.common.base.BaseActivity;
import com.xiaochen.common.utils.LogUtil;
import com.xiaochen.module.dagger2.component.DaggerApplicationComponent;
import com.xiaochen.module.dagger2.module.AModule;

import javax.inject.Inject;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/28
 */
public class Dagger2OtherActivity extends BaseActivity {

    @PersonQualifier("w")
    @Inject
    Person1 mPerson;

    @Inject
    Gson mGson;

    @Override
    protected int getLayoutResId() {
        return R.layout.dagger2_activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        TextView tv = findViewById(R.id.tv);
        findViewById(R.id.back_button).setOnClickListener(v -> finish());
        TextView title = findViewById(R.id.title_text);
        title.setText("dagger2学习");
        DaggerApplicationComponent.builder().build().plus(new AModule()).inject(this);
        String json = mGson.toJson(mPerson);
        tv.setText(json);
        LogUtil.e("OtherActivity", mPerson + "");
    }
}
