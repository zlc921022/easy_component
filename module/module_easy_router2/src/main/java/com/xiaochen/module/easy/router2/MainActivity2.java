package com.xiaochen.module.easy.router2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import com.xiaochen.common.base.BaseActivity;
import com.xiaochen.common.sdk.RouterManager;
import com.xiaochen.common.sdk.RouterPathConstant;
import com.xiaochen.common.utils.LogUtil;
import com.xiaochen.easy.annotation.Extra;
import com.xiaochen.easy.annotation.Route;
import com.xiaochen.easy.core.EasyRouter;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/17
 */
@Route(path = RouterPathConstant.TEST_ACTIVITY)
public class MainActivity2 extends BaseActivity {

    @Extra
    public String name;
    @Extra
    public int age;
    @Extra
    public double money;
    @Extra
    public String work;
    public Bundle bundle;
    private TextView mText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EasyRouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.easy_router2_activity_main2;
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
        bundle = getIntent().getExtras();
        if (isUserInfoEmpty()) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("名字：").append(name).append("\n");
        sb.append("年龄：").append(age).append("\n");
        sb.append("月薪：").append(money).append("\n");
        sb.append("工作：").append(work).append("\n");
        mText.setText(sb.toString());
        LogUtil.e(TAG, "name:=" + name);
    }

    private boolean isUserInfoEmpty() {
        return TextUtils.isEmpty(name) || age == 0 || money == 0
                || TextUtils.isEmpty(work);
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
