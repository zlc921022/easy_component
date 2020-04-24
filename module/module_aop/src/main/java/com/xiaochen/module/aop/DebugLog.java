package com.xiaochen.module.aop;

import android.util.Log;

/**
 * <p>{d}</p>
 *
 * @author zhenglecheng
 * @date 2020-02-28
 */
public class DebugLog {
    private DebugLog() {
    }

    public static void log(String tag, String message) {
        Log.e(tag, message);
    }
}
