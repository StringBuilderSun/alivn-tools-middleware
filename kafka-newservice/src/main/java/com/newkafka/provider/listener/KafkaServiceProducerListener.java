package com.newkafka.provider.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListenerAdapter;

/**
 * Created by lijinpeng on 2018/9/10.
 */
@Slf4j
public class KafkaServiceProducerListener extends ProducerListenerAdapter {
    @Override
    public void onSuccess(String topic, Integer partition, Object key, Object value, RecordMetadata recordMetadata) {
        super.onSuccess(topic, partition, key, value, recordMetadata);
        log.info("消息发送成功！");
        log.info("----topic:" + topic + ", partition:" + partition + ", key:" + key + ", value:" + value + ", RecordMetadata:" + recordMetadata.toString() + "----");
    }


    @Override
    public void onError(String topic, Integer partition, Object key, Object value, Exception exception) {
        super.onError(topic, partition, key, value, exception);
        log.info("消息发送失败！");
        log.info("----topic:" + topic + ", partition:" + partition + ", key:" + key + ", value:" + value + ", Exception:" + exception + "----");
        exception.printStackTrace();
    }

    /**
     * 是否开启监听发送
     * true  是  false 否
     *
     * @return
     */
    @Override
    public boolean isInterestedInSuccess() {
        return true;
    }
}
