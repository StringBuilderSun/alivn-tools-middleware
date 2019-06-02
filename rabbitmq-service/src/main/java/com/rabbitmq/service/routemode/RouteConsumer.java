package com.rabbitmq.service.routemode;

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
public class RouteConsumer extends AbstractRabbitService implements RabbitConsumeMessage {
    //添加库存队列
    private String ADD_QUEUE_NAME = "addStockNum";
    //操作库存日志队列
    private String OPERATOR_QUEUE_NAME = "operateLogs";
    //交换器
    private String exchange = "stockManager";
    //路由key
    private String routeKey= "addStock";

    public void consumerMessage(String queueName, Consumer consumer) throws IOException {
        Channel channel = getChannel();
        channel.basicConsume(queueName, false,consumer);
    }

    public Channel initChannel(Connection connection) throws IOException {
        Channel channel = connection.createChannel();
        channel.queueDeclare(ADD_QUEUE_NAME, true, false, false, null);
        channel.queueDeclare(OPERATOR_QUEUE_NAME, true, false, false, null);
        channel.queueBind(ADD_QUEUE_NAME, exchange, routeKey);
        channel.queueBind(OPERATOR_QUEUE_NAME, exchange, routeKey);
        return channel;
    }

    public static void main(String[] args) throws Exception {
        RouteConsumer consumer = new RouteConsumer();
        WorkQueueCallBacks workQueueCallBacks1 = new WorkQueueCallBacks(consumer.getChannel(), consumer.ADD_QUEUE_NAME);
        consumer.consumerMessage(consumer.ADD_QUEUE_NAME, workQueueCallBacks1);
        WorkQueueCallBacks workQueueCallBacks2 = new WorkQueueCallBacks(consumer.getChannel(), consumer.OPERATOR_QUEUE_NAME);
        consumer.consumerMessage(consumer.OPERATOR_QUEUE_NAME, workQueueCallBacks2);
    }

}
