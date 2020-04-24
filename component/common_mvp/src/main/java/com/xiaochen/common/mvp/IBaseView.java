package com.xiaochen.common.mvp;


/**
 * 父类View接口 原则上子类View接口都应该实现该接口
 *
 * @author zhenglecheng
 */
public interface IBaseView<T> {

    /**
     * 显示loading
     */
    default void showLoading() {

    }

    /**
     * 关闭loading
     */
    default void dismissLoading() {

    }

    /**
     * 请求成功回调
     *
     * @param data 接口数据
     */
    void setData(T data);

    /**
     * 请求错误回调
     *
     * @param msg  错误信息
     * @param code 错误码
     */
    void onError(String msg, String code);
}
