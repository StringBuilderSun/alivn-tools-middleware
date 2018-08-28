package com.kafka.service.manager;

import com.kafka.service.model.TopicInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lijinpeng on 2018/8/8.
 */
@Service
public class ProviderServiceImpl {
    private final Logger log = LoggerFactory.getLogger(ProviderServiceImpl.class);

    /**
     * 同步发送消息
     *
     * @param topicInfo  主题
     * @param message    要发送的消息
     * @param traceLogId 日志追踪号
     * @param <T>
     */
    public static <T> void sendMessage(TopicInfo topicInfo, T message, String traceLogId) {
        ProducerNoSpringService.sendMessage(topicInfo, message, traceLogId);
    }

    /**
     * 同步发送消息集合
     *
     * @param topicInfo  主题
     * @param messages   消息集合
     * @param traceLogId 日志追踪号
     * @param <T>
     */
    public static <T> void sendMessage(TopicInfo topicInfo, List<T> messages, String traceLogId) {
        ProducerNoSpringService.sendMessage(topicInfo, messages, traceLogId);
    }

    /**
     * 同步发送key value 消息
     *
     * @param topicInfo  主题
     * @param key        ?键
     * @param message    消息
     * @param traceLogId 日志
     * @param <T>
     */
    public static <T> void sendMessageWithKey(TopicInfo topicInfo, int key, T message, String traceLogId) {
        ProducerNoSpringService.sendMessageWithKey(topicInfo, key, message, traceLogId);
    }

    /**
     * 异步发送消息
     *
     * @param topicInfo  主题
     * @param message    要发送的消息
     * @param traceLogId 日志追踪号
     * @param <T>
     */
    public static <T> void sendAsyncMessage(TopicInfo topicInfo, T message, String traceLogId) {
        ProducerNoSpringService.sendAsyncMessage(topicInfo, message, traceLogId);
    }
}
