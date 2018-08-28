package com.kafka.service.manager;

import com.kafka.service.thread.SendMsgThread;
import com.kafka.service.factory.ProducerFactory;
import com.kafka.service.model.TopicInfo;
import com.kafka.service.thread.ThreadPool;
import kafka.producer.KeyedMessage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * kafka服务发送服务类
 * Created by lijinpeng on 2018/8/7.
 */
@Slf4j
@Service
public class ProducerNoSpringService {

    public ProducerNoSpringService() {
    }

    /**
     * 同步发送消息
     *
     * @param topicInfo  主题
     * @param message    要发送的消息
     * @param traceLogId 日志追踪号
     * @param <T>
     */
    public static <T> void sendMessage(TopicInfo topicInfo, T message, String traceLogId) {
        log.debug("topic：{} 要发送得消息 :{}", new Object[]{traceLogId, topicInfo, message});
        ProducerFactory.getProducer(topicInfo).send(new KeyedMessage(topicInfo.getTopicName(), message));
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
        log.debug("topic：{} 要发送得消息 :{}", new Object[]{traceLogId, topicInfo, messages});
        ProducerFactory.getProducer(topicInfo).send(new KeyedMessage(topicInfo.getTopicName(), messages));
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
        log.debug("topic：{} 要发送得消息:{}", new Object[]{traceLogId, topicInfo, message});
        ProducerFactory.getProducer(topicInfo).send(new KeyedMessage(topicInfo.getTopicName(), Integer.valueOf(key), message));
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
        log.debug("topic：{} 要发送得消息 :{}", new Object[]{traceLogId, topicInfo, message});
        SendMsgThread sendMsgThread = new SendMsgThread(topicInfo, message, traceLogId);
        ThreadPool.getInstance().getPool().execute(sendMsgThread);
    }
}
