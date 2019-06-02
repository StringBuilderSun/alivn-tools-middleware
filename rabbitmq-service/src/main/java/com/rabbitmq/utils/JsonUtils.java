package com.rabbitmq.utils;

import com.alibaba.fastjson.JSON;

/**
 * User: lijinpeng
 * Created by Shanghai on 2019/6/1.
 */
public class JsonUtils {

    public static String getJson(Object value) {
        return JSON.toJSONString(value);
    }
}
