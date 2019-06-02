package com.rabbitmq.interfaces;

import com.rabbitmq.client.Consumer;
import java.io.IOException;

/**
 * User: lijinpeng
 * Created by Shanghai on 2019/6/1.
 */
public interface RabbitConsumeMessage extends RabbitChannelInitService{
    /**
     * 从指定队列消费消息
     * @param queueName 队列
     * @param consumer 消息到达时的回调函数
     */
    void consumerMessage(String queueName, Consumer consumer) throws IOException;
}
