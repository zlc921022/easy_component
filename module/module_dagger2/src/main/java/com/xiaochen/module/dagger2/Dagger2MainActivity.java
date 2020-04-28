package com.xiaochen.module.dagger2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.xiaochen.common.base.BaseActivity;
import com.xiaochen.module.dagger2.component.ApplicationComponent;
import com.xiaochen.module.dagger2.component.DaggerApplicationComponent;
import com.xiaochen.module.dagger2.component.DaggerMainComponent;
import com.xiaochen.module.dagger2.module.ApplicationModule;
import com.xiaocheng.common.sdk.PathConstant;

import javax.inject.Inject;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/28
 */
@Route(path = PathConstant.DAGGER2_ACTIVITY)
public class Dagger2MainActivity extends BaseActivity {

    @Inject
    Person mPerson;

    @Inject
    Gson mGson;

    @Inject
    SharedPreferences mSp;

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
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule())
                .build();
        DaggerMainComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .inject(this);
        String json = mGson.toJson(mPerson);
        tv.setText(json);
        Log.e("MainActivity person", mPerson.getName());
        Log.e("MainActivity sp", mSp + "");
        tv.setOnClickListener(v -> {
            Intent intent = new Intent(this, Dagger2OtherActivity.class);
            startActivity(intent);
        });
    }
}
