package com.xiaochen.module.widget.ui;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xiaochen.common.sdk.RouterPathConstant;
import com.xiaochen.common.service.CommonNameService;
import com.xiaochen.module.widget.R;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/29
 */
@Route(path = RouterPathConstant.WIDGET_SERVICE,name = "widget服务")
public class WidgetInfoServiceImpl implements CommonNameService {
    private Context mContext;
    @Override
    public String getModuleName() {
        return mContext.getString(R.string.module_widget);
    }

    @Override
    public void init(Context context) {
        this.mContext = context;
    }
}
