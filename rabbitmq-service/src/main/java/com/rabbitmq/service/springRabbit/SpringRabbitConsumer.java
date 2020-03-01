package com.rabbitmq.service.springRabbit;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

/**
 * 消息消费者
 * User: lijinpeng
 * Created by Shanghai on 2019/6/2.
 */
@Slf4j
@Service
public class SpringRabbitConsumer implements ChannelAwareMessageListener {

    public void onMessage(Message message, Channel channel) throws Exception {
        log.info("消费到消息:{}",message);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
