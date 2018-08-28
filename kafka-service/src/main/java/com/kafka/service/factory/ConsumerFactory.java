package com.kafka.service.factory;

import com.kafka.service.tools.IPUtil;
import com.kafka.service.model.TopicInfo;
import kafka.consumer.ConsumerConfig;
import kafka.javaapi.consumer.ConsumerConnector;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消费者工厂
 * 功能点
 * 1、将消费者放入线程安全的hashMap集合中  通过TopicName+标识 获取对应消费者
 * <p>
 * Created by lijinpeng on 2018/8/8.
 */
@Slf4j
public class ConsumerFactory {
    /**
     * 存放消费者的线程安全的hashMap
     */
    private static ConcurrentHashMap<String, ConsumerConnector> consumerMap = new ConcurrentHashMap<String, ConsumerConnector>();

    private synchronized static void initConsumer(TopicInfo topicInfo, String consumerConnectorId) {
        if (consumerMap.get(consumerConnectorId) == null) {
            ConsumerConnector consumerConnector = kafka.consumer.Consumer.createJavaConsumerConnector(new ConsumerConfig(getProperty(topicInfo)));
            consumerMap.put(consumerConnectorId, consumerConnector);
        }
    }

    /**
     * 获取消费者连接器的名字
     *
     * @param topicInfo 主题
     * @return
     */
    private static String generatorConnectorName(TopicInfo topicInfo) {
        return topicInfo.getTopicName() + Thread.currentThread().getId();
    }

    /**
     * 根据topic获取消费者
     *
     * @param topicInfo 主题
     * @return
     */
    public static ConsumerConnector getConnector(TopicInfo topicInfo) {
        String consumerConnectorId = generatorConnectorName(topicInfo);
        if (consumerMap.get(consumerConnectorId) == null) {
            initConsumer(topicInfo, consumerConnectorId);
        }
        log.debug("find ConsumerConnector for topicName:{} consumerConnectorId:{}", topicInfo.getTopicName(), consumerConnectorId);
        return consumerMap.get(consumerConnectorId);
    }

    public static ConsumerConnector getConnector(TopicInfo topicInfo, String traceLogId) {
        log.debug("find ConsumerConnector  traceLogId:{} topicName:{}", traceLogId, topicInfo.getTopicName());
        return getConnector(topicInfo);
    }

    /**
     * 根据topic 获取 消费者需要的配置信息
     *
     * @param topicInfo 主题
     * @return
     */
     public static Properties getProperty(TopicInfo topicInfo) {
        Properties props = new Properties();
        props.put("zookeeper.connect", topicInfo.getZkAdress());
        props.put("group.id", topicInfo.getGroupName());
        String flag = IPUtil.getLocalIP() + "_" + topicInfo.getTopicName() + "_" + UUID.randomUUID().toString();
        props.put("client.id", flag);
        props.put("consumer.id", flag);
         props.put("zookeeper.connection.timeout.ms", "6000");
         props.put("auto.commit.interval.ms", "1000");
         props.put("rebalance.max.retries", "3");
         props.put("rebalance.backoff.ms", "5000");
//         props.put("zookeeper.session.timeout.ms", "5000");
        log.debug("consumer Properties:{}", props);
        return props;
    }
}
