package com.rabbitmq.deliverCallbacks;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * User: lijinpeng
 * Created by Shanghai on 2019/6/1.
 */
@Slf4j
public class WorkQueueCallBacks extends DefaultConsumer {

    private Channel channel;

    private String name;

    public WorkQueueCallBacks(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    public WorkQueueCallBacks(Channel channel, String name) {
        super(channel);
        this.name = name;
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String consumerMessage = new String(body, "UTF-8");
        long start = System.currentTimeMillis();
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();

        log.info("队列:{} 当前线程:{} 已消费消息:{} 耗时:{}", name, Thread.currentThread().getName(), consumerMessage, (end - start));
        //实现手动提交
        channel.basicAck(envelope.getDeliveryTag(), false);
    }
}


