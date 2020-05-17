package com.xiaochen.module.easy.router1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.xiaochen.common.base.BaseActivity;
import com.xiaochen.common.sdk.RouterPathConstant;
import com.xiaochen.easy.annotation.Route;
import com.xiaochen.easy.core.EasyRouter;

import androidx.annotation.Nullable;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/17
 */
@Route(path = RouterPathConstant.AROUTER_ACTIVITY)
public class Router1MainActivity extends BaseActivity {

    private int requestCode = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        findViewById(R.id.back_button).setOnClickListener(v -> finish());
        TextView title = findViewById(R.id.title_text);
        title.setText("arouter学习");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.router1_activity_main;
    }

    @Override
    protected void initListener() {
        super.initListener();
        findViewById(R.id.button3).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("work", "高大上的攻城狮");
            bundle.putInt("age", 27);
            bundle.putString("name", "zlc");
            bundle.putDouble("money", 15000);
            EasyRouter.getInstance()
                    .build(RouterPathConstant.TEST_ACTIVITY)
                    .with(bundle)
                    .navigation();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
