package com.newkafka.consumer.api;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by lijinpeng on 2019/5/27.
 */
public class ConsumerHandCommit {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.0.0.31:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("my-topic", "bestpay-topic"));
        final int minBatchSize = 200;
        List<ConsumerRecord<String, String>> buffer = new ArrayList<ConsumerRecord<String, String>>();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
            }
            if (buffer.size() >= minBatchSize) {
                consumerBatch(buffer);
                consumer.commitSync();
                buffer.clear();
            }
        }
    }

    public static void consumerBatch(List<ConsumerRecord<String, String>> records) {
        System.out.println("----------------开启消费批次数据-------------------------");
        for (ConsumerRecord<String, String> record : records) {
            System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
        }
        System.out.println("----------------结束该批次数据(" + records.size() + "条)-------------------------");
    }
}
