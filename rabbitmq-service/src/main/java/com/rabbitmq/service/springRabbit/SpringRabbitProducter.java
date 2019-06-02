package com.rabbitmq.service.springRabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息生产者
 * User: lijinpeng
 * Created by Shanghai on 2019/6/2.
 */
@Slf4j
@Service
public class SpringRabbitProducter {


    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendMessage(String message) {
        log.info("发送消息:{}",message);
        rabbitTemplate.convertAndSend(message);
    }

}
