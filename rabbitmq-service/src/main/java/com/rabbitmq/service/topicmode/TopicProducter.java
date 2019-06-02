package com.rabbitmq.service.topicmode;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.interfaces.RabbitSendMessage;
import com.rabbitmq.model.MessageInfo;
import com.rabbitmq.service.AbstractRabbitService;
import com.rabbitmq.service.routemode.RouteProducter;
import com.rabbitmq.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *  # 表示1-n个单词
 *  * 表示一个单词
 * User: lijinpeng
 * Created by Shanghai on 2019/6/2.
 */
@Slf4j
public class TopicProducter extends AbstractRabbitService implements RabbitSendMessage<String> {

    private String exchange = "yhwg_message";

    private String routePattern = "acps.#";

    public void sendMessage(String queueName, String message) throws IOException {
        Channel channel = getChannel();
        channel.basicPublish(exchange, routePattern, null, message.getBytes());
        log.info("线程:{} 发布消息:{} 已成功", Thread.currentThread().getName(), message);
    }

    public Channel initChannel(Connection connection) throws IOException {
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange, "topic");
        return channel;
    }

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        TopicProducter producer = new TopicProducter();
        for (int i = 0; i < 1000; i++) {
            MessageInfo<String> messageInfo = new MessageInfo<String>();
            messageInfo.setResult(String.valueOf(i));
            messageInfo.setRspCode("0000");
            messageInfo.setRspDesc("人行大额支付报文通知");
            messageInfo.setSuccess(true);
            producer.sendMessage(null, JsonUtils.getJson(messageInfo));
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
