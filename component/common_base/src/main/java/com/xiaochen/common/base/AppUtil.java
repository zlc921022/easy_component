package com.xiaochen.common.base;

import android.app.Application;
import android.content.Context;

/**
 * <p>application管理类</p >
 *
 * @author zhenglecheng
 * @date 2020/4/16
 */
public class AppUtil {

    private static Application mContext;

    private AppUtil() {

    }

    static void init(Application context) {
        mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }

    public static String getString(int resId) {
        return mContext.getString(resId);
    }

    public static String getString(int resId, Object... formatArgs) {
        return mContext.getString(resId, formatArgs);
    }

    public static int getColor(int colorId) {
        return mContext.getResources().getColor(colorId);
    }

    public static int getDimens(int resId) {
        return mContext.getResources().getDimensionPixelSize(resId);
    }
}
