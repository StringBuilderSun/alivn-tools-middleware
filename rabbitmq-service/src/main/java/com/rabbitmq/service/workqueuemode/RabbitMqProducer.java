package com.rabbitmq.service.workqueuemode;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.interfaces.RabbitSendMessage;
import com.rabbitmq.model.MessageInfo;
import com.rabbitmq.service.AbstractRabbitService;
import com.rabbitmq.utils.JsonUtils;
import com.rabbitmq.utils.RabbitUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 简单模式， 一对一生产
 * User: lijinpeng
 * Created by Shanghai on 2019/5/31.
 */
@Slf4j
public class RabbitMqProducer extends AbstractRabbitService implements RabbitSendMessage<String> {

    private static String QUEUE_NAME = "bestpay";

    public void sendMessage(String queueName, String message) throws IOException {
        Channel channel = getChannel();
        channel.basicPublish("", queueName, null, message.getBytes());
        log.info("线程:{} 发布消息:{} 已成功", Thread.currentThread().getName(), message);
    }

    public Channel initChannel(Connection connection) throws IOException {
        Channel channel = connection.createChannel();
        //durable:true 开启消息持久化  服务重启后消息不丢失
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        return channel;

    }

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        RabbitMqProducer producer = new RabbitMqProducer();
        for (int i = 0; i < 1000; i++) {
            MessageInfo<String> messageInfo = new MessageInfo<String>();
            messageInfo.setResult(String.valueOf(i));
            messageInfo.setRspCode("0000");
            messageInfo.setRspDesc("扣费1次");
            messageInfo.setSuccess(true);
            producer.sendMessage(QUEUE_NAME, JsonUtils.getJson(messageInfo));
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
