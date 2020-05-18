package com.xiaochen.easy.core.template;

import java.util.Map;

/**
 * Author: xiaochen
 * Create Date: 2020/05/16
 * Email: zlc921022@163.com
 */
public interface IRouteRoot {
    void loadInto(Map<String, Class<? extends IRouteGroup>> routes);
}
