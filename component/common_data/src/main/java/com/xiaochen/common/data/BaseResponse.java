package com.xiaochen.common.data;

/**
 * @author admin
 */
public class BaseResponse<T> {
    public int errorCode;
    public String errorMsg;
    public T data;
}
