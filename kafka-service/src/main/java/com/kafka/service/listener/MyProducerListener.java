//package com.kafka.service.listener;
//
//import org.apache.kafka.clients.producer.RecordMetadata;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.support.ProducerListenerAdapter;
//import org.springframework.stereotype.Service;
//
///**
// * Created by chensuqiang on 2017/9/29.
// */
//@Service
//public class MyProducerListener extends ProducerListenerAdapter {
//    private final static Logger log = LoggerFactory.getLogger(MyProducerListener.class);
//
//    /**
//     * 发送消息成功后调用
//     */
//    @Override
//    public void onSuccess(String topic, Integer partition, Object key,
//                          Object value, RecordMetadata recordMetadata) {
//        super.onSuccess(topic, partition, key, value, recordMetadata);
//        log.debug("消息发送成功！");
//        log.debug("----topic:" + topic + ", partition:" + partition + ", key:" + key + ", value:" + value + ", RecordMetadata:" + recordMetadata.toString() + "----");
//    }
//
//    /**
//     * 发送消息错误后调用
//     */
//    @Override
//    public void onError(String topic, Integer partition, Object key,
//                        Object value, Exception exception) {
//        super.onError(topic, partition, key, value, exception);
//        log.debug("消息发送失败！");
//        log.debug("----topic:" + topic + ", partition:" + partition + ", key:" + key + ", value:" + value + ", Exception:" + exception + "----");
//        exception.printStackTrace();
//    }
//
//    /**
//     * 是否开启发送监听
//     *
//     * @return true开启，false关闭
//     */
//    @Override
//    public boolean isInterestedInSuccess() {
//        return true;
//    }
//}
