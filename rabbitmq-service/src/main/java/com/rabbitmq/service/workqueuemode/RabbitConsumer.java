package com.rabbitmq.service.workqueuemode;

import com.rabbitmq.client.*;
import com.rabbitmq.deliverCallbacks.WorkQueueCallBacks;
import com.rabbitmq.interfaces.RabbitConsumeMessage;
import com.rabbitmq.service.AbstractRabbitService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


/**
 * 简单消费 一对一消费
 * 可以开启多个消费者 并行消费消息 提供性能
 * User: lijinpeng
 * Created by Shanghai on 2019/6/1.
 */
@Slf4j
@Getter
public class RabbitConsumer extends AbstractRabbitService implements RabbitConsumeMessage {

    private String QUEUE_NAME = "bestpay";

    public void consumerMessage(String queueName, Consumer consumer) throws IOException {
        final Channel channel = getChannel();
        channel.basicConsume(QUEUE_NAME, false, consumer);

    }

    public Channel initChannel(Connection connection) throws IOException {
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //每次拉取一个消息 可以实现公平派遣，消费者消费完成后再拉取新消息
        channel.basicQos(1);
        return channel;
    }

    public static void main(String[] args) throws Exception {
        RabbitConsumer consumer = new RabbitConsumer();
        WorkQueueCallBacks workQueueCallBacks = new WorkQueueCallBacks(consumer.getChannel(),consumer.getQUEUE_NAME());
        consumer.consumerMessage(consumer.getQUEUE_NAME(), workQueueCallBacks);
    }
}
