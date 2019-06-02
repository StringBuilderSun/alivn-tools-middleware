package com.rabbitmq.service.pubsubmode;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.deliverCallbacks.WorkQueueCallBacks;
import com.rabbitmq.interfaces.RabbitConsumeMessage;
import com.rabbitmq.service.AbstractRabbitService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * User: lijinpeng
 * Created by Shanghai on 2019/6/1.
 */
@Slf4j
@Getter
public class RabbitSubscribe extends AbstractRabbitService implements RabbitConsumeMessage {
    private String QUEUE_NAME = "log_record";

    private String exchange = "log_sys";

    public void consumerMessage(String queueName, Consumer consumer) throws IOException {
        Channel channel = getChannel();
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }

    public Channel initChannel(Connection connection) throws IOException {
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange, "fanout");
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        channel.queueBind(QUEUE_NAME, exchange, "");
        return channel;
    }

    public static void main(String[] args) throws Exception {
        RabbitSubscribe consumer = new RabbitSubscribe();
        WorkQueueCallBacks workQueueCallBacks = new WorkQueueCallBacks(consumer.getChannel(),consumer.getQUEUE_NAME());
        consumer.consumerMessage(consumer.getQUEUE_NAME(), workQueueCallBacks);
    }

}
