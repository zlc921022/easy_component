package com.xiaochen.easy.core.template;

import com.xiaochen.easy.annotation.modle.RouteMeta;

import java.util.Map;

/**
 * Author: xiaochen
 * Create Date: 2020/05/16
 * Email: zlc921022@163.com
 */

public interface IRouteGroup {
    void loadInto(Map<String, RouteMeta> atlas);
}
