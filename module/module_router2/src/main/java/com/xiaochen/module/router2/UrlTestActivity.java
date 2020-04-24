package com.xiaochen.module.router2;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xiaochen.common.base.BaseActivity;
import com.xiaocheng.common.sdk.PathConstant;

/**
 * <p>{d}</p>
 *
 * @author zhenglecheng
 * @date 2020/4/19
 */
@Route(path = PathConstant.TEST_ACTIVITY3)
public class UrlTestActivity extends BaseActivity {

    @Autowired
    public String name;
    @Autowired
    public int age;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.router2_activity_main2;
    }

    @Override
    protected void initData() {
        super.initData();
        findViewById(R.id.back_button).setOnClickListener(v -> finish());
        TextView title = findViewById(R.id.title_text);
        TextView tv = findViewById(R.id.tv);
        title.setText("arouter学习");
        if (!TextUtils.isEmpty(name) && age != 0) {
            tv.setText("来自url: " + "名字==" + name + "；年纪==" + age);
        }
    }
}
