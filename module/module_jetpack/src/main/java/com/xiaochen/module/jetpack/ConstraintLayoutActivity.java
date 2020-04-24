package com.xiaochen.module.jetpack;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiaochen.common.base.BaseActivity;

/**
 * @类描述：
 * @作者： zhenglecheng
 * @创建时间： 2019/10/22 17:08
 */
public class ConstraintLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_constraintlayout_test;
    }

    @Override
    protected void initView() {
        super.initView();
        TextView title = findViewById(R.id.title_text);
        title.setText("ConstraintLayout");
        findViewById(R.id.back_button).setOnClickListener(v -> {
            finish();
        });
    }
}
