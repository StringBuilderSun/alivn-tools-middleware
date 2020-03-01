package com.rabbitmq.service.topicmode;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.deliverCallbacks.WorkQueueCallBacks;
import com.rabbitmq.interfaces.RabbitConsumeMessage;
import com.rabbitmq.service.AbstractRabbitService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * User: lijinpeng
 * Created by Shanghai on 2019/6/2.
 */
@Slf4j
@Setter
@Getter
public class TopicConsumer extends AbstractRabbitService implements RabbitConsumeMessage {

    private String ACPS_QUEUE_NAME = "ACPS";

    private String routekey = "acps.yy";

    private String exchange = "yhwg_message";

    public void consumerMessage(String queueName, Consumer consumer) throws IOException {
        Channel channel = getChannel();
        channel.basicConsume(queueName, false, consumer);
    }

    public Channel initChannel(Connection connection) throws IOException {
        Channel channel = connection.createChannel();
        channel.queueDeclare(ACPS_QUEUE_NAME, true, false, false, null);
        channel.queueBind(ACPS_QUEUE_NAME, exchange, routekey);
        return channel;
    }

    public static void main(String[] args) throws Exception {
        TopicConsumer consumer = new TopicConsumer();
        WorkQueueCallBacks workQueueCallBacks = new WorkQueueCallBacks(consumer.getChannel(), consumer.ACPS_QUEUE_NAME);
        consumer.consumerMessage(consumer.ACPS_QUEUE_NAME, workQueueCallBacks);
    }
}
