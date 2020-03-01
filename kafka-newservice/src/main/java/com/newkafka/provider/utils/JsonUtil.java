package com.newkafka.provider.utils;

import com.google.gson.Gson;

/**
 * Created by lijinpeng on 2018/9/10.
 */
public class JsonUtil {
    /**
     * java对象转换成json字符串
     *
     * @param obj Object 对象
     * @return json 字符串
     */
    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    /**
     * json字符串转成java对象
     *
     * @param str 字符串
     * @param type class 对象
     * @return class 对象
     */
    public static <T> T fromJson(String str, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(str, type);
    }
}
