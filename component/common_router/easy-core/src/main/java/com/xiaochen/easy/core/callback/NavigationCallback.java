package com.xiaochen.easy.core.callback;

import com.xiaochen.easy.core.Postcard;

/**
 * Author: xiaochen
 * Create Date: 2020/05/16
 * Email: zlc921022@163.com
 */

public interface NavigationCallback {

    /**
     * 找到跳转页面
     */
    void onFound(Postcard postcard);

    /**
     * 未找到
     */
    void onLost(Postcard postcard);

    /**
     * 成功跳转
     */
    void onArrival(Postcard postcard);
}
