package com.xiaochen.module.router2;

import android.content.Intent;
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
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/17
 */
@Route(path = PathConstant.TEST_ACTIVITY)
public class MainActivity2 extends BaseActivity {

    @Autowired
    public String name;
    @Autowired
    public int age;
    @Autowired
    public double money;
    @Autowired(name = "bundle")
    public Bundle mBundle;
    @Autowired(name = "userInfo")
    public Object userInfo;
    private TextView mText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.router2_activity_main2);
//        initView();
//        initData();
//        initListener();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.router2_activity_main2;
    }

    @Override
    protected void initView() {
        super.initView();
        findViewById(R.id.back_button).setOnClickListener(v -> finish());
        TextView title = findViewById(R.id.title_text);
        mText = findViewById(R.id.tv);
        title.setText("arouter学习");
    }

    @Override
    protected void initData() {
        super.initData();
        if (!isEmpty()) {
            StringBuffer sb = new StringBuffer();
            sb.append("名字：").append(name).append("\n");
            sb.append("年龄：").append(age).append("\n");
            sb.append("金钱：").append(money).append("\n");
            sb.append("工作：").append(mBundle.getString("work")).append("\n");
            sb.append("用户信息：").append(userInfo.toString());
            mText.setText(sb.toString());
        }
    }

    private boolean isEmpty() {
        return TextUtils.isEmpty(name) || age == 0
                || money == 0 || TextUtils.isEmpty(mBundle.getString("work"))
                || userInfo == null;
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("result", "你好，小弟");
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
