package com.xiaochen.module.router2;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xiaochen.common.base.BaseActivity;
import com.xiaocheng.common.sdk.PathConstant;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/17
 */
@Route(path = PathConstant.TEST_ACTIVITY2)
public class MainActivity3 extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.router2_activity_test);
        WebView webView = findViewById(R.id.webview);
        webView.loadUrl("file:////android_asset/test.html");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.router2_activity_test;
    }

    @Override
    protected void initView() {
        super.initView();
        findViewById(R.id.back_button).setOnClickListener(v -> finish());
        TextView title = findViewById(R.id.title_text);
        title.setText("arouter学习");
    }
}
