package com.xiaochen.common.utils;

import timber.log.Timber;

/**
 * @author zlc
 * email : zlc921022@163.com
 * desc : APP日志
 */
public final class AppLogger {

    private AppLogger(){}
    /**
     * 初始化日志
     * @param isDebug 是否是调试模式
     */
    public static void init(final boolean isDebug) {
        if (isDebug) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
