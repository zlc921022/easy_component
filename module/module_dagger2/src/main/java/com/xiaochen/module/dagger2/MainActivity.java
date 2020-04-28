package com.xiaochen.module.dagger2;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiaochen.common.base.BaseActivity;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/28
 */
public class MainActivity extends BaseActivity {

    @Named("w")
    @Inject
    Person mPerson;

    @Named( "m")
    @Inject
    Person mPerson2;

    @Inject
    Gson mGson;
//
//    @Inject
//    SharedPreferences mSp;

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
        MyApplication.getApplication().getAComponent().inject(this);
        String json = mGson.toJson(mPerson);
        tv.setText(json);
        Log.e("MainActivity person", mPerson.getName());
        Log.e("MainActivity person2", mPerson2.getName());
//        Log.e("MainActivity sp", mSp + "");
        tv.setOnClickListener(v -> {
            Intent intent = new Intent(this, OtherActivity.class);
            startActivity(intent);
        });
    }
}
