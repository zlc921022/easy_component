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
     * @param postcard
     */
    void onFound(Postcard postcard);

    /**
     * 未找到
     * @param postcard
     */
    void onLost(Postcard postcard);

    /**
     * 成功跳转
     * @param postcard
     */
    void onArrival(Postcard postcard);

    /**
     * 中断了路由跳转
     * @author luoxiaohui
     * @createTime 2019-06-18 17:00
     */
    void onInterrupt(Throwable throwable);
}
