package com.shubo.bis.util;

import com.shubo.bis.constants.ErrorCode;
import com.shubo.bis.util.exception.SystemException;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ruanshubo
 * @Copyright: Copyright (c) 兆日科技股份有限公司  2020
 * @date 2020/6/10,14:38
 * 线程池管理
 */
public class ThreadPoolManager {
    // 饿汉式，多线程情况下线程安全，且性能比饱汉式低
    private static ThreadPoolManager threadPoolManager = new ThreadPoolManager();
    /**
     * 最小线程数
     */
    private static final int CORE_POOL_SIZE = 3;
    /**
     * 最大线程数
     * 目前设计为事务密集型，业务少，占CPU少，所以不需要那么多
     */
    private static final int MAX_POOL_SIZE = 15;
    /**
     * 维护线程池队列大小
     */
    private static final int WORK_QUEUE_SIZE = 3000;

    /**
     * 线程池允许空闲时间(ms)
     */
    private static final long KEEP_LIVE_TIME = 5000;

    /**
     * 构造方法私有，禁止随意实例化
     */
    private ThreadPoolManager() {
    }

    ;

    public static ThreadPoolManager newInstance() {
        return threadPoolManager;
    }

    /**
    * @param
    * @return
    * @Description: 拒绝策略，目前只做了抛出异常
    * @author ruanshubo
    * @date 2020/6/15 14:29
    */
    private final RejectedExecutionHandler mHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            throw new SystemException(ErrorCode.THREAD_POOL_FULL_ERROR.getCode()+":"+ErrorCode.THREAD_POOL_FULL_ERROR.getDesc());
        }
    };
    /**
    * @param
    * @return
    * @Description:创建线程池
    * @author ruanshubo
    * @date 2020/6/15 14:32
    */
    private final ThreadPoolExecutor mThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAX_POOL_SIZE, KEEP_LIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(WORK_QUEUE_SIZE), mHandler);

    /**
     * 向线程池添加task
     * @param task
     */
    public void addTask(Runnable task) {
        if (task != null) {
            mThreadPool.execute(task);
        }
    }

    public void perpare() {
        if (mThreadPool.isShutdown() && !mThreadPool.prestartCoreThread()) {
            mThreadPool.prestartAllCoreThreads();
        }
    }

    public void shutDown() {
        mThreadPool.shutdown();
    }
}
