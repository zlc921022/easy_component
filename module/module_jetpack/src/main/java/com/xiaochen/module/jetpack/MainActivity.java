package com.xiaochen.module.jetpack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xiaochen.common.base.BaseActivity;
import com.xiaochen.common.sdk.RouterPathConstant;

@Route(path = RouterPathConstant.JETPACK_ACTIVITY)
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.jetpack_activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        TextView title = findViewById(R.id.title_text);
        title.setText("jetpack");
        findViewById(R.id.tv).setOnClickListener(v -> startActivity(LifeCyclerActivity.class));
        findViewById(R.id.room).setOnClickListener(v -> startActivity(RoomActivity.class));
        findViewById(R.id.con_layout).setOnClickListener(v -> startActivity(ConstraintLayoutActivity.class));
        findViewById(R.id.navigation).setOnClickListener(v -> startActivity(NavigationActivity.class));
        findViewById(R.id.paging).setOnClickListener(v -> startActivity(PagingActivity.class));
    }

    private void startActivity(Class clazz) {
        Intent intent = new Intent(MainActivity.this, clazz);
        startActivity(intent);
    }
}
