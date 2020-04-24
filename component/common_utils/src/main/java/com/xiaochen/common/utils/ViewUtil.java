package com.xiaochen.common.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author zlc
 * email : zlc921022@163.com
 * desc : view操作工具类
 */
public class ViewUtil {

    private ViewUtil() {
    }

    /**
     * 显示或者隐藏多个控件
     *
     * @param visible
     * @param views
     */
    public static void showViews(int visible, View... views) {
        for (View v : views) {
            if (v != null) {
                v.setVisibility(visible);
            }
        }
    }


    public static <T> void startActivity(Context context, Class<T> tClass) {
        startActivity(context, tClass.getName());
    }

    /**
     * 开启主页
     */
    public static <T> void startMainActivity(Context context, Class<T> tClass){
        Intent intent = new Intent(context,tClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * 开启一个不带参数的activity
     *
     * @param context
     * @param className
     */
    public static void startActivity(Context context, String className) {
        Intent intent = new Intent();
        intent.setClassName(context, className);
        context.startActivity(intent);
    }

    /**
     * 开启一个传递对象参数的activity
     *
     * @param context
     * @param tClass
     * @param q
     * @param <T>
     * @param <Q>
     */
    public static <T, Q extends Parcelable> void startActivity(Context context, Class<T> tClass, Q q) {
        Intent intent = new Intent();
        intent.setClass(context, tClass);
        intent.putExtra(q.getClass().getSimpleName(), q);
        context.startActivity(intent);
    }

    /**
     * 开启一个传递集合对象参数的activity
     *
     * @param context
     * @param tClass
     * @param qList
     * @param <T>
     * @param <Q>
     */
    public static <T, Q extends Parcelable> void startActivity(Context context, Class<T> tClass, ArrayList<Q> qList) {
        Intent intent = new Intent();
        intent.setClass(context, tClass);
        intent.putParcelableArrayListExtra("listInfo", qList);
        context.startActivity(intent);
    }

    /**
     * 开启一个传递bundle对象参数的activity
     *
     * @param context
     * @param tClass
     * @param bundle
     * @param <T>
     */
    public static <T> void startActivity(Context context, Class<T> tClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, tClass);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    /**
     * 给textview设置部分字体颜色
     *
     * @param tv
     */
    public static void setTextColor(TextView tv, String text, int color, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(color),
                start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannableString);
    }

    /**
     * 测量控件宽和高
     *
     * @param view
     */
    public static void measureWidthAndHeight(View view) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
    }

    //获取虚拟按键的高度
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

    @SuppressLint("PrivateApi")
    public static int getStatusHeight(Activity activity) {
        if (activity == null) {
            return -1;
        }
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            return activity.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void hideBottomUIMenu(Activity activity) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT < 19) { // lower api
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            //for new api versions.
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static void forbidHideBottomUIMenu(Activity activity) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT < 19) { // lower api
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            Window _window = activity.getWindow();
            WindowManager.LayoutParams params = _window.getAttributes();
            params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
            _window.setAttributes(params);
        }
    }

}
