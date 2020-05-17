package com.xiaochen.easy.core;

import com.xiaochen.easy.annotation.modle.RouteMeta;
import com.xiaochen.easy.core.template.IRouteGroup;
import com.xiaochen.easy.core.template.IService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: 夏胜明
 * Date: 2018/4/24 0024
 * Email: xiasem@163.com
 * Description:
 */

public class Warehouse {

    // root 映射表 保存分组信息
    static Map<String, Class<? extends IRouteGroup>> groupsIndex = new HashMap<>();

    // group 映射表 保存组中的所有数据
    static Map<String, RouteMeta> routes = new HashMap<>();

    // group 映射表 保存组中的所有数据
    static Map<Class, IService> services = new HashMap<>();
}
