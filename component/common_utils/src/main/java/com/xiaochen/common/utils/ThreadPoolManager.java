package com.xiaochen.common.utils;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zlc
 * email : zlc921022@163.com
 * desc : 线程池管理类
 */
public class ThreadPoolManager {

    private ThreadPoolExecutor sThreadPool;

    private ThreadPoolManager() {
    }

    public static ThreadPoolManager getInstance() {
        return SingHolder.POOL_MANAGER;
    }

    private final static class SingHolder {
        private static final ThreadPoolManager POOL_MANAGER = new ThreadPoolManager();
    }

    public ThreadPoolManager createPoolExecutor() {
        return createPoolExecutor(1, 1);
    }

    /**
     * 创建线程池对象
     *
     * @param corePoolSize
     * @param maxPollSize
     * @return
     */
    public ThreadPoolManager createPoolExecutor(int corePoolSize, int maxPollSize) {
        if (sThreadPool == null) {
            sThreadPool = new ThreadPoolExecutor(corePoolSize,
                    maxPollSize, 0, TimeUnit.MILLISECONDS,
                    new LinkedBlockingDeque<>(), new ThreadPoolExecutor.AbortPolicy());
        }
        return this;
    }

    /**
     * 执行线程操作
     * 提交任务无返回值
     *
     * @param runnable
     * @return
     */
    public ThreadPoolManager execute(Runnable runnable) {
        if (sThreadPool != null) {
            sThreadPool.execute(runnable);
        }
        return this;
    }

    /**
     * 执行线程操作
     * 提交任务有返回值
     *
     * @param runnable
     * @return
     */
    public ThreadPoolManager sumbit(Runnable runnable) {
        if (sThreadPool != null) {
            sThreadPool.submit(runnable);
        }
        return this;
    }

    /**
     * 取消线程
     *
     * @param runnable
     * @return
     */
    public ThreadPoolManager cancel(Runnable runnable) {
        if (sThreadPool != null && !sThreadPool.isShutdown()) {
            sThreadPool.remove(runnable);
        }
        return this;
    }

    /**
     * 关闭线程池
     *
     * @return
     */
    public ThreadPoolManager shutdown() {
        if (sThreadPool != null) {
            sThreadPool.shutdownNow();
            sThreadPool = null;
        }
        return this;
    }
}
