package com.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * User: lijinpeng
 * Created by Shanghai on 2019/6/1.
 */
@Setter
@Getter
@ToString
public class RabbitConfig {
    /**
     * 主机名
     */
    private String rabbitHost;
    /**
     * 端口
     */
    private int rabbitPort;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String passWord;
}
