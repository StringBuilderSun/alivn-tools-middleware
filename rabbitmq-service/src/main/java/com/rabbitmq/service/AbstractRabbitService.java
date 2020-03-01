package com.rabbitmq.service;


import com.rabbitmq.client.Channel;
import com.rabbitmq.interfaces.RabbitChannelInitService;
import com.rabbitmq.utils.RabbitUtils;

import java.io.IOException;


/**
 * User: lijinpeng
 * Created by Shanghai on 2019/6/1.
 */
public abstract class AbstractRabbitService implements RabbitChannelInitService {

    private Channel channel;

    public Channel getChannel() throws IOException {
        if (channel == null) {
            this.channel= this.initChannel(RabbitUtils.createNewConnection());
        }
        return channel;
    }
}
