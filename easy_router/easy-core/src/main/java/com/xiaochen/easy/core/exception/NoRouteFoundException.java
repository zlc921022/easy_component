package com.xiaochen.easy.core.exception;

/**
 * Author: xiaochen
 * Create Date: 2020/05/16
 * Email: zlc921022@163.com
 */

public class NoRouteFoundException extends RuntimeException {

    public NoRouteFoundException(String detailMessage) {
        super(detailMessage);
    }
}
