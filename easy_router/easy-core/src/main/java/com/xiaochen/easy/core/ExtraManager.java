package com.xiaochen.easy.core;

import android.app.Activity;
import android.util.LruCache;

import com.xiaochen.easy.core.template.IExtra;

/**
 * Author: xiaochen
 * Create Date: 2020/05/16
 * Email: zlc921022@163.com
 */
public class ExtraManager {
    public static final String SUFFIX_AUTOWIRED = "$$Extra";
    private volatile static ExtraManager instance;
    private LruCache<String, IExtra> classCache;

    public static ExtraManager getInstance() {
        if (instance == null) {
            synchronized (ExtraManager.class) {
                if (instance == null) {
                    instance = new ExtraManager();
                }
            }
        }
        return instance;
    }


    public ExtraManager() {
        classCache = new LruCache<>(66);
    }


    /**
     * 注入
     */
    public void loadExtras(Activity instance) {
        //查找对应activity的缓存
        String className = instance.getClass().getName();
        IExtra extra = classCache.get(className);
        try {
            if (null == extra) {
                extra = (IExtra) Class.forName(instance.getClass().getName() +
                        SUFFIX_AUTOWIRED).getConstructor().newInstance();
            }
            extra.loadExtra(instance);
            classCache.put(className, extra);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
