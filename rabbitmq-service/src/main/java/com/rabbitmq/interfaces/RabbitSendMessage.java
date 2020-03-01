package com.rabbitmq.interfaces;


import java.io.IOException;

/**
 * User: lijinpeng
 * Created by Shanghai on 2019/6/1.
 */
public interface RabbitSendMessage<T> extends RabbitChannelInitService {
    /**
     * 往指定队列发送消息
     * @param queueName
     * @param message
     * @throws IOException
     */
    void sendMessage(String queueName, T message)  throws IOException;

}
