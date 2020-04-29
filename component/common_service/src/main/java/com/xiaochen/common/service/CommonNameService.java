package com.xiaochen.common.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/4/29
 */
public interface CommonNameService extends IProvider {
    /**
     * 获取模块名字
     * @return 模块名字
     */
    String getModuleName();
}
