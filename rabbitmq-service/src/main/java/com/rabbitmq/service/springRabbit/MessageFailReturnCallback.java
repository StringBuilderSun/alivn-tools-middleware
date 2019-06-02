package com.rabbitmq.service.springRabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * 消息发送失败后回调
 * User: lijinpeng
 * Created by Shanghai on 2019/6/2.
 */
@Slf4j
@Service("messageFailReturnCallback")
public class MessageFailReturnCallback implements RabbitTemplate.ReturnCallback {
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
      log.error("消息发送失败:{} {} {} {} {}",message,i,s,s1,s2);
    }
}
