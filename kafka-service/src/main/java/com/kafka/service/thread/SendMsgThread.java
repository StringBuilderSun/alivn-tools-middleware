package com.kafka.service.thread;

import com.kafka.service.factory.ProducerFactory;
import com.kafka.service.model.TopicInfo;
import kafka.producer.KeyedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 发送异步消息线程
 * Created by lijinpeng on 2018/8/7.
 */
public class SendMsgThread implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(SendMsgThread.class);
    /**
     * 消息主题
     */
    private TopicInfo topicInfo;
    /**
     * 要发送的消息
     */
    private Object message;
    /**
     * 日志追踪号
     */
    private String traceLogId;

    public SendMsgThread(TopicInfo topicInfo, Object message, String traceLogId) {
        this.topicInfo = topicInfo;
        this.message = message;
        this.traceLogId = traceLogId;
    }

    public void run() {
        try {
            ProducerFactory.getProducer(topicInfo).send(new KeyedMessage(topicInfo.getTopicName(), message));
        } catch (Exception var2) {
            logger.error("failed to kafka build message, parameter:{} {}, cause：{}", this.message, var2);
        }
    }
}
