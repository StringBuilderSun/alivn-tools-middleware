package com.kafka.service.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 * Created by lijinpeng on 2018/8/6.
 */
public class ThreadPool {
    /**
     * 一天的时间常量
     */
    private static final long ONE_DAY_SEC = 86400L;
    /**
     * 允许空置线程存活的最大时间
     */
    private long keepAliveTime = 86400L;
    /**
     * 单例线程池
     */
    private static volatile ThreadPool instance;

    /**
     * 获取线程池
     *
     * @return
     */
    public ThreadPoolExecutor getPool() {
        return pool;
    }

    /**
     * 核心线程容量
     */
    private int coreSize=20;
    /**
     * 最大线程量
     */
    private int maxSize=200;
    /**
     * 可允许的队列数
     */
    private int queueSize=2000;
    /**
     * 线程池
     */
    private ThreadPoolExecutor pool;

    public ThreadPool() {
        this.init();
    }

    /**
     * 双重锁检查机制 保证单例
     *
     * @return
     */
    public static ThreadPool getInstance() {
        if (null == instance) {
            synchronized (instance) {
                if (null == instance) {
                    return new ThreadPool();
                }
            }
        }
        return instance;
    }

    /**
     * 线程安全的初始化线程池
     */
    public synchronized void init() {
        if (null == this.pool) {
            this.pool = new ThreadPoolExecutor(this.coreSize, this.maxSize, this.keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(this.queueSize), new ThreadPoolExecutor.CallerRunsPolicy());
        }
    }

    public void setCoreSize(int coreSize) {
        this.coreSize = coreSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }
}

