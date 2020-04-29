package com.xiaochen.module.dagger2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.xiaochen.common.base.BaseActivity;
import com.xiaochen.common.sdk.RouterManager;
import com.xiaochen.common.sdk.RouterPathConstant;
import com.xiaochen.common.service.CommonNameService;
import com.xiaochen.common.utils.LogUtil;
import com.xiaochen.module.dagger2.component.ApplicationComponent;
import com.xiaochen.module.dagger2.component.DaggerApplicationComponent;
import com.xiaochen.module.dagger2.component.DaggerMainComponent;
import com.xiaochen.module.dagger2.module.ApplicationModule;

import javax.inject.Inject;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/28
 */
@Route(path = RouterPathConstant.DAGGER2_ACTIVITY)
public class Dagger2MainActivity extends BaseActivity {

    @Inject
    Person mPerson;

    @Inject
    Gson mGson;

    @Inject
    SharedPreferences mSp;

    /**
     * 测试调取别的模块服务的方法
     */
    @Autowired(name = RouterPathConstant.WIDGET_SERVICE)
    CommonNameService mWidgetInfoService;

    @Override
    protected int getLayoutResId() {
        return R.layout.dagger2_activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        RouterManager.inject(this);
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
        LogUtil.e("MainActivity person", mPerson.getName());
        LogUtil.e("MainActivity sp", mSp + "");
        if (mWidgetInfoService != null) {
            LogUtil.e("MainActivity widget name", mWidgetInfoService.getModuleName());
        }
        tv.setOnClickListener(v -> {
            Intent intent = new Intent(this, Dagger2OtherActivity.class);
            startActivity(intent);
        });
    }
}
