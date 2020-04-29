package com.xiaochen.module.router2;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.xiaochen.common.sdk.RouterManager;
import com.xiaochen.common.utils.LogUtil;

/**
 * <p>{d}</p>
 *
 * @author zhenglecheng
 * @date 2020/4/19
 */
public class UrlSchemeActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = getIntent().getData();
        if (uri != null) {
            RouterManager.navigation(this, uri, new NavCallback() {
                @Override
                public void onFound(Postcard postcard) {
                    LogUtil.e("UrlSchemeActivity", "onFound");
                }

                @Override
                public void onLost(Postcard postcard) {
                    LogUtil.e("UrlSchemeActivity", "onLost");
                }

                @Override
                public void onArrival(Postcard postcard) {
                    LogUtil.e("UrlSchemeActivity", "onArrival");
                }
            });
            finish();
        }
    }

}
