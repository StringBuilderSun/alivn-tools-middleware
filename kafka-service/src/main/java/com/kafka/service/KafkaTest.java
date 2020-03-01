package com.kafka.service;

import com.kafka.service.enums.ProducerType;
import com.kafka.service.enums.RequiredAck;
import com.kafka.service.model.TopicInfo;
import com.kafka.service.service.BusinessProcessInterface;
import com.kafka.service.manager.ConsumerServiceImpl;
import com.kafka.service.manager.ProducerNoSpringService;
import com.kafka.service.service.PayNotifyProcessInterface;

import java.util.UUID;

/**
 * Created by lijinpeng on 2018/8/8.
 */
public class KafkaTest {

    public static void main(String[] args) throws InterruptedException, IllegalAccessException, InstantiationException {

        ConsumerServiceImpl consumerService = new ConsumerServiceImpl();
        BusinessProcessInterface businessProcess =new PayNotifyProcessInterface();
        consumerService.consumerMessage(getTopic(), businessProcess, UUID.randomUUID().toString());
        while (true) {
            Thread.sleep(2000);
            String message="交易成功";
            ProducerNoSpringService.sendMessage(getTopic(),message, UUID.randomUUID().toString());
        }
    }

    /**
     * 获取kafka主题
     *
     * @return
     */
    public static TopicInfo getTopic() {
        TopicInfo topicInfo = new TopicInfo();
        topicInfo.setBrokerList("10.0.0.99:9092");
        topicInfo.setGroupName("manyConsumers");
        topicInfo.setRetries(false);
        topicInfo.setTopicName("eatFood");
        topicInfo.setZkAdress("10.0.0.99:2181");
        topicInfo.setSync(ProducerType.SYNC);
        topicInfo.setRequiredAck(RequiredAck.LEADER_ACK);
        return topicInfo;
    }


}
