package com.rabbitmq.service.routemode;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.interfaces.RabbitSendMessage;
import com.rabbitmq.model.MessageInfo;
import com.rabbitmq.service.AbstractRabbitService;
import com.rabbitmq.service.pubsubmode.RabbitPublisher;
import com.rabbitmq.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 不关心发送到哪个队列 只会关心发送到哪个交换器 和  路由key
 * 下面案例是库存管理
 * 消息发送到库存管理交换机
 * 路由有更新库存操作
 * User: lijinpeng
 * Created by Shanghai on 2019/6/2.
 */
@Slf4j
@Setter
@Getter
public class RouteProducter extends AbstractRabbitService implements RabbitSendMessage<String> {

    private String exchange = "stockManager";

    private String routeKey = "addStock";

    public void sendMessage(String queueName, String message) throws IOException {
        Channel channel = getChannel();
        channel.basicPublish(exchange, routeKey, null, message.getBytes());
        log.info("线程:{} 发布消息:{} 已成功", Thread.currentThread().getName(), message);
    }

    public Channel initChannel(Connection connection) throws IOException {
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange, "direct");
        return channel;
    }
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        RouteProducter producer = new RouteProducter();
        for (int i = 0; i < 1000; i++) {
            MessageInfo<String> messageInfo = new MessageInfo<String>();
            messageInfo.setResult(String.valueOf(i));
            messageInfo.setRspCode("0000");
            messageInfo.setRspDesc("添加库存通知");
            messageInfo.setSuccess(true);
            producer.sendMessage(null, JsonUtils.getJson(messageInfo));
            TimeUnit.SECONDS.sleep(1);
        }
    }

}
