package com.rabbitmq.model;

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
public class MessageInfo<T> {
    private T result;
    private boolean isSuccess;
    private String rspCode;
    private String rspDesc;
}
