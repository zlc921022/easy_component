package com.xiaochen.module.dagger2;

import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiaochen.common.base.BaseActivity;

import javax.inject.Inject;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/28
 */
public class OtherActivity extends BaseActivity {

    @Inject
    Person mPerson;

    @Inject
    Gson mGson;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        TextView tv = findViewById(R.id.tv);
//        DaggerMainComponent.builder().build().inject(this);
//        DaggerMainComponent.getInstance().inject(this);
//        mPerson.setName("大哥，你好");
        String json = mGson.toJson(mPerson);
        tv.setText(json);
        Log.e("OtherActivity",mPerson+"");
    }
}
