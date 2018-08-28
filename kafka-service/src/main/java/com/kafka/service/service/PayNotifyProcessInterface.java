package com.kafka.service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 交易终态通知服务 测试服务类
 * Created by lijinpeng on 2018/8/28.
 */
@Slf4j
public class PayNotifyProcessInterface implements BusinessProcessInterface<String> {
    public void doBusiness(String message) {
      log.info("拉取到得当前消息:{}",message);
      log.info("调用一系列业务处理方法......");
      log.info("消息消费成功.......");
    }
}
