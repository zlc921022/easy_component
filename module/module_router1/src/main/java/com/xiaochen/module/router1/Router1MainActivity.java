package com.xiaochen.module.router1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xiaochen.common.base.BaseActivity;
import com.xiaochen.common.utils.LogUtil;
import com.xiaochen.common.utils.ToastUtil;
import com.xiaocheng.common.sdk.PathConstant;

import timber.log.Timber;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/17
 */
@Route(path = PathConstant.AROUTER_ACTIVITY)
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
            ARouter.getInstance()
                    .build(PathConstant.TEST_ACTIVITY)
                    .navigation(this, new NavCallback() {

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
            ARouter.getInstance()
                    .build(PathConstant.TEST_ACTIVITY)
                    .navigation(this, requestCode);
        });
        findViewById(R.id.button3).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("work", "高大上的攻城狮");
            ARouter.getInstance()
                    .build(PathConstant.TEST_ACTIVITY)
                    .withInt("age", 27)
                    .withString("name", "zlc")
                    .withDouble("money", 20000.0)
                    .withBundle("bundle", bundle)
                    .withObject("userInfo", new UserInfo("小城", "男", 27))
                    .navigation();
        });
        findViewById(R.id.button4).setOnClickListener(v -> {
            ARouter.getInstance()
                    .build(PathConstant.TEST_ACTIVITY2)
                    .navigation(this);
        });
        findViewById(R.id.button6).setOnClickListener(v -> {
            Fragment fragment = (Fragment) ARouter.getInstance().build(PathConstant.TEST_FRAGMENT).navigation();
            LogUtil.e("MainActivity fragment", fragment + "");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame, fragment)
                    .commit();
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
