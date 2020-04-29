package com.xiaochen.module.router1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.xiaochen.common.base.BaseActivity;
import com.xiaochen.common.sdk.RouterManager;
import com.xiaochen.common.sdk.RouterPathConstant;
import com.xiaochen.common.utils.LogUtil;
import com.xiaochen.common.utils.ToastUtil;

import timber.log.Timber;

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
        findViewById(R.id.button1).setOnClickListener(v -> {
            RouterManager.navigation(this, RouterPathConstant.TEST_ACTIVITY, new NavCallback() {
                @Override
                public void onFound(Postcard postcard) {
                    LogUtil.e("MainActivity", "onFound");
                }

                @Override
                public void onLost(Postcard postcard) {
                    LogUtil.e("MainActivity", "onLost");
                }

                @Override
                public void onArrival(Postcard postcard) {
                    LogUtil.e("MainActivity", "onArrival");
                }

                @Override
                public void onInterrupt(Postcard postcard) {
                    super.onInterrupt(postcard);
                    LogUtil.e("MainActivity", "onInterrupt");
                }
            });

        });
        findViewById(R.id.button2).setOnClickListener(v -> {
            RouterManager.navigation(this, RouterPathConstant.TEST_ACTIVITY, requestCode);
        });
        findViewById(R.id.button3).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("work", "高大上的攻城狮");
            bundle.putInt("age", 27);
            bundle.putString("name", "zlc");
            bundle.putDouble("money", 15000);
            bundle.putString("userInfo", RouterManager.navigation(SerializationService.class)
                    .object2Json(new UserInfo("小城", "男", 27)));
            RouterManager.navigation(RouterPathConstant.TEST_ACTIVITY, bundle);
        });
        findViewById(R.id.button4).setOnClickListener(v -> {
            RouterManager.navigation(RouterPathConstant.TEST_ACTIVITY2);
        });
        findViewById(R.id.button6).setOnClickListener(v -> {
            Fragment fragment = RouterManager.getFragment(RouterPathConstant.TEST_FRAGMENT);
            LogUtil.e("MainActivity fragment", fragment + "");
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, fragment)
                        .commit();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String result = data.getStringExtra("result");
            Timber.tag("result").e(result);
            ToastUtil.showShortToast(this, "获取到返回值为：" + result);
        }
    }
}
