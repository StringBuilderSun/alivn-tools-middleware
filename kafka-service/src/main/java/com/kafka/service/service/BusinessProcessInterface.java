package com.kafka.service.service;

/**
 * kafka消费者抽象类 消费业务接口
 * Created by lijinpeng on 2018/8/8.
 */
public interface BusinessProcessInterface<T> {
    void doBusiness(T message);
}
