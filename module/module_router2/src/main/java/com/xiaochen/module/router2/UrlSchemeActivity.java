package com.xiaochen.module.router2;

import android.net.Uri;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xiaochen.common.utils.LogUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
        if(uri != null) {
            ARouter.getInstance().build(uri).navigation(this, new NavCallback() {
                @Override
                public void onFound(Postcard postcard) {
                    LogUtil.e("UrlSchemeActivity","onFound");
                }

                @Override
                public void onLost(Postcard postcard) {
                    LogUtil.e("UrlSchemeActivity","onLost");
                }

                @Override
                public void onArrival(Postcard postcard) {
                    LogUtil.e("UrlSchemeActivity","onArrival");
                }
            });
            finish();
        }
    }

}
