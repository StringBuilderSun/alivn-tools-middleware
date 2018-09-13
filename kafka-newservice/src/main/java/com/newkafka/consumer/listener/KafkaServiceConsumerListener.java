package com.newkafka.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

/**
 * 消费消息监听器
 * Created by lijinpeng on 2018/9/13.
 */
@Slf4j
public class KafkaServiceConsumerListener implements MessageListener<String, String> {

    public void onMessage(ConsumerRecord<String, String> stringStringConsumerRecord) {
        log.info("消息已被成功消费:{}", stringStringConsumerRecord.toString());
        //doBusiness() 做一些业务处理
        log.info("消费消费完成，跟踪记录已被处理！");
    }
}
