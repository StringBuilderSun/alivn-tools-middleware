package com.rabbitmq.service.springRabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Service;

/**
 * 消息确认回调
 * User: lijinpeng
 * Created by Shanghai on 2019/6/2.
 */
@Slf4j
@Service("messageConfirmCallBack")
public class MessageConfirmCallBack implements RabbitTemplate.ConfirmCallback {

    public void confirm(CorrelationData correlationData, boolean b, String s) {
        log.info("消息确认:{}", correlationData);
    }
}
