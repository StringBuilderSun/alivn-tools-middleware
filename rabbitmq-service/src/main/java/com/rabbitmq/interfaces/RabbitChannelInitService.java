package com.rabbitmq.interfaces;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;


import java.io.IOException;

/**
 * User: lijinpeng
 * Created by Shanghai on 2019/6/1.
 */
public interface RabbitChannelInitService {
    /**
     * 初始化channel
     *
     * @param connection
     * @return
     * @throws IOException
     */
    Channel initChannel(Connection connection) throws IOException;

    /**
     * 获取Channel
     * @return
     */
    Channel getChannel() throws IOException;
}
