package com.kafka.service.enums;

/**
 * 消息同异步标识
 * Created by lijinpeng on 2018/8/3.
 */
public enum ProducerType {
    SYNC("sync", "同步消息"),
    ASYNC("async", "异步消息");
    private String syncFlag;
    private String description;
    private ProducerType(String syncFlag, String description) {
        this.syncFlag = syncFlag;
        this.description = description;
    }

    public String getSyncFlag() {
        return this.syncFlag;
    }

    public String getDescription() {
        return this.description;
    }
}
