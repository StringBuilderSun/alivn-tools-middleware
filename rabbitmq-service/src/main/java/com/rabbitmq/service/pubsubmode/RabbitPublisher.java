package com.rabbitmq.service.pubsubmode;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.interfaces.RabbitSendMessage;
import com.rabbitmq.model.MessageInfo;
import com.rabbitmq.service.AbstractRabbitService;
import com.rabbitmq.service.workqueuemode.RabbitMqProducer;
import com.rabbitmq.utils.JsonUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 消息发布者
 * 消息发布者不关心消息发布到哪个队列， 只负责将消息发布到交换器
 * 交换器决定消息发送到哪个队列
 * User: lijinpeng
 * Created by Shanghai on 2019/6/1.
 */
@Slf4j
@Getter
public class RabbitPublisher extends AbstractRabbitService implements RabbitSendMessage<String> {

    private String QUEUE_NAME = "log_record";

    private String exchange = "log_sys";

    public void sendMessage(String queueName, String message) throws IOException {
        Channel channel = this.getChannel();
        channel.basicPublish(exchange, "", null, message.getBytes());
        log.info("线程:{} 发布消息:{} 已成功", Thread.currentThread().getName(), message);
    }

    public Channel initChannel(Connection connection) throws IOException {
        Channel channel = connection.createChannel();
        //生命一个log_sys交换器  类型是 fanout 每个发送到交换器的消息都会转发到绑定的队列上
        channel.exchangeDeclare(exchange, "fanout");
        return channel;
    }

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        RabbitPublisher producer = new RabbitPublisher();
        for (int i = 0; i < 1000; i++) {
            MessageInfo<String> messageInfo = new MessageInfo<String>();
            messageInfo.setResult(String.valueOf(i));
            messageInfo.setRspCode("0000");
            messageInfo.setRspDesc("日志更新");
            messageInfo.setSuccess(true);
            producer.sendMessage(producer.getQUEUE_NAME(), JsonUtils.getJson(messageInfo));
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
