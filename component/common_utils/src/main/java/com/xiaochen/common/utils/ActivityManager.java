package com.xiaochen.common.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * <p>activity管理类{d}</p>
 *
 * @author zhenglecheng
 * @date 2019-12-18
 */
public class ActivityManager {

    private ActivityManager() {

    }

    public static ActivityManager getManager() {
        return SingleHolder.INSTANCE;
    }

    static class SingleHolder {
        final static ActivityManager INSTANCE = new ActivityManager();
    }

    private Stack<Activity> mStack = new Stack<>();

    /**
     * 往stack里面添加activity
     *
     * @param activity activity对象
     */
    public void pushActivity(Activity activity) {
        if (mStack == null) {
            return;
        }
        if (!mStack.contains(activity)) {
            mStack.push(activity);
        }
    }

    /**
     * 出栈一个具体的activity
     *
     * @param activity activity对象
     */
    public void popActivity(Activity activity) {
        if (mStack == null) {
            return;
        }
        if (mStack.contains(activity)) {
            activity.finish();
        }
    }

    /**
     * 出栈所有的activity
     */
    public void popAllActivity() {
        if (mStack == null) {
            return;
        }
        for (Activity activity : mStack) {
            activity.finish();
        }
    }
}
