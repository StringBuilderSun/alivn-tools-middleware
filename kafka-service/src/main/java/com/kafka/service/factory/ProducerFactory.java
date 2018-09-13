package com.kafka.service.factory;

import com.kafka.service.model.TopicInfo;
import com.kafka.service.tools.IntegerCycle;
import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 生产者工厂
 * 功能点
 * 1、将生产者放入线程安全的hashMap中 通过topicName获取对应生产者集合
 * Created by lijinpeng on 2018/8/6.
 */
@Slf4j
@Service
public class ProducerFactory {
    /**
     * 通过该类原子性操作 准确获取 Topic下生产者下标
     */
    private static final AtomicInteger atomicInteger = new AtomicInteger(0);
    /**
     * 线程安全的 HashMap
     */
    private static final ConcurrentHashMap<String, List<Producer>> topicMap = new ConcurrentHashMap<String, List<Producer>>();

    public ProducerFactory() {
    }

    /**
     * 根据topicInfo 初始化生产者列表
     *
     * @param topicInfo
     */
    private static synchronized void initProducer(TopicInfo topicInfo) {
        //当前只保证 有一个 生产者  发送到 一个topic 上   暂时 不考虑多个生产者  发送到 一个topic上的情况
        ArrayList producerList = new ArrayList(1);
        if (null == topicMap.get(topicInfo.getTopicName()) || topicMap.get(topicInfo.getTopicName()).size() == 0) {
            producerList.add(new Producer(new ProducerConfig(getProperties(topicInfo))));
            log.debug("初始化生产者 :{}", topicInfo.getTopicName());
        }
        topicMap.put(topicInfo.getTopicName(), producerList);
    }

    /**
     * 获取指定topic下的生产者
     * @param topicInfo
     * @return
     */
    public static Producer getProducer(TopicInfo topicInfo) {
        if (null == topicMap.get(topicInfo.getTopicName()) || topicMap.get(topicInfo.getTopicName()).size() == 0) {
            initProducer(topicInfo);
        }
        //线程安全的 始终获取 到的 下标为0  这样写的 原因是 方便扩展 多个 生产者 写入同一个topic
        int index = IntegerCycle.getIndex(atomicInteger, 1);
        log.debug("连接器在hashMap中的位置 ：{}", Integer.valueOf(index));
        return topicMap.get(topicInfo.getTopicName()).get(index);
    }


    /**
     * 根据topic获取配置属性
     *
     * @param topicInfo
     * @return
     */
    private static Properties getProperties(TopicInfo topicInfo) {
        Properties properties = new Properties();
        properties.put("metadata.broker.list", topicInfo.getBrokerList());
        properties.put("request.required.acks", topicInfo.getRequiredAck().getAck());
        properties.put("producer.type", topicInfo.getSync().getSyncFlag());
        properties.put("serializer.class", "com.kafka.provider.serializer.MessageSerializer");
        properties.put("message.send.max.retries", topicInfo.isRetries() ? "1" : "0");
        properties.put("request.timeout.ms", "3000");
        properties.put("topic.metadata.refresh.interval.ms", topicInfo.getRefreshInterval().toString());
        return properties;

    }


}
