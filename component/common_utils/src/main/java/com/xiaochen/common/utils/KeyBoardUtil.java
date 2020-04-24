package com.xiaochen.common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by zlc on 2018/10/8
 * Email: zlc921022@163.com
 * Desc: 软键盘工具类
 */
public class KeyBoardUtil {

    private KeyBoardUtil(){}

    //显示软键盘
    public static void showKeyboard(Activity activity) {
        if(activity == null)    return;
        InputMethodManager imManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imManager != null) {
            imManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //隐藏软键盘
    public static void hideKeyboard(Activity activity) {
        if(activity == null)    return;
        InputMethodManager imManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        View view = activity.getCurrentFocus();
        if(view != null && imManager != null) {
            imManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //强制隐藏软键盘
    public static void forbidKeyboard(Activity activity) {
        if(activity == null)    return;
        InputMethodManager imManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        View view = activity.getCurrentFocus();
        if(view != null && imManager != null) {
            imManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
