package com.xiaochen.module.mvp;


import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jaeger.library.StatusBarUtil;
import com.xiaochen.common.mvp.BaseMvpActivity;
import com.xiaochen.common.utils.LogUtil;
import com.xiaochen.common.utils.ToastUtil;
import com.xiaochen.module.mvp.presenter.TestPresenter;
import com.xiaochen.module.mvp.response.HomeArticleRespVO;
import com.xiaochen.module.mvp.view.ITestView;
import com.xiaocheng.common.sdk.PathConstant;

/**
 * <p>主页</p >
 *
 * @author zhenglecheng
 * @date 2019/12/26
 */
@Route(path = PathConstant.MVP_ACTIVITY)
public class MainActivity extends BaseMvpActivity<TestPresenter> implements ITestView {


    @Override
    protected int getLayoutResId() {
        return R.layout.mvp_activity_main;
    }

    @Override
    protected TestPresenter getPresenter() {
        return new TestPresenter(this, this);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected void initView() {
        super.initView();
        Button test = findViewById(R.id.button_1);
        test.setOnClickListener(v -> mPresenter.getHomeArticles(1));
        findViewById(R.id.back_button).setOnClickListener(v -> finish());
        TextView titleText = findViewById(R.id.title_text);
        titleText.setText("mvp测试");
    }

    @Override
    public void setData(HomeArticleRespVO.Data data) {
        LogUtil.e(TAG, data != null ? data.toString() : null);
    }

    @Override
    public void onError(String msg, String code) {
        ToastUtil.showShortToast(this, msg);
    }
}
