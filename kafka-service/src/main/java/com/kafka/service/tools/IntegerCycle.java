package com.kafka.service.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lijinpeng on 2018/8/6.
 */
public class IntegerCycle {
    private static final Logger log = LoggerFactory.getLogger(IntegerCycle.class);

    public IntegerCycle() {
    }

    /**
     * 依靠Unsafe这个类的原子性保证线程安全   目前一直返回0
     *
     * @param atomicInteger
     * @param endNum
     * @return
     */
    public static synchronized int getIndex(AtomicInteger atomicInteger, int endNum) {
        //这个方法得作用是 将endNum 与 初始值value比较， 如果value与endNum相同，则将value更新位update的值 返回true 否则返回false
        atomicInteger.compareAndSet(endNum, 0);
        //将value自动加1
        return atomicInteger.getAndIncrement();

    }
}
