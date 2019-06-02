package com.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Properties;

/**
 * User: lijinpeng
 * Created by Shanghai on 2019/6/1.
 */
@Slf4j
public class RabbitUtils {

    private static ConnectionFactory factory;

    static {
        InputStream in = null;
        //读取属性文件a.properties
        try {
            Properties pro = new Properties();
            in = RabbitUtils.class.getClassLoader().getResourceAsStream("config/config.properties");
            pro.load(in);
            factory = new ConnectionFactory();
            factory.setHost(pro.getProperty("rabbit.host"));
            factory.setPort(Integer.valueOf(pro.getProperty("rabbit.port")));
            factory.setUsername(pro.getProperty("rabbit.userName"));
            factory.setPassword(pro.getProperty("rabbit.password"));
        } catch (Exception e) {
            log.error("初始化属性文件失败！", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public static Connection createNewConnection() {
        if (factory != null) {
            try {
                return factory.newConnection();
            } catch (Exception e) {
                log.error("创建rabbit连接失败", e);
            }
        }
        return null;
    }
}
