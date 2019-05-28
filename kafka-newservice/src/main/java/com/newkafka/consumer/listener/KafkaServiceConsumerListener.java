package com.newkafka.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import sun.nio.ch.IOUtil;

import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * 消费消息监听器
 * Message
 * Created by lijinpeng on 2018/9/13.
 */
@Slf4j
public class KafkaServiceConsumerListener implements MessageListener<String, String> {

    public void onMessage(ConsumerRecord<String, String> stringStringConsumerRecord) {
        try {
            FileWriter writer=new FileWriter("d:/test.txt",true);
            writer.append(stringStringConsumerRecord.value());
            writer.append("\r\n");
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("消息已被成功消费:{}", stringStringConsumerRecord.toString());
        try {
            TimeUnit.MICROSECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //doBusiness() 做一些业务处理
    }
}
