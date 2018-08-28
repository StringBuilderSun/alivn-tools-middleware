package com.kafka.service.enums;

/**
 * 配置生产者发送消息是否等待服务节点得响应 以此保证 消息发送得可靠性
 * Created by lijinpeng on 2018/8/3.
 */
public enum RequiredAck {
    /**
     * 无需等待任何服务器节点响应   立即发送到缓存区   无法保证服务节点故障情况下  消息的丢失
     */
    NO_ACK("0", "无需应答(消息异步))"),
    /**
     * 只接受leader应答，无需等到从服务器应答，在这种情况下，如果响应消息之后但follower还未复制之前leader立即故障，那么消息将会丢失。
     */
    LEADER_ACK("1", "leader应答(同步消息，主节点应答)"),
    /**
     这意味着leader将等待所有副本同步后应答消息。此配置保障消息不会丢失（只要至少有一个同步的副本或者）。这是最强壮的可用性保障。等价于acks=-1。
     */
    ALL_ACK("-1", "全部应答(同步消息，主从节点都应答)");

    private String ack;
    private String description;

    public String getAck() {
        return this.ack;
    }

    public String getDescription() {
        return this.description;
    }

    private RequiredAck(String ack, String description) {
        this.ack = ack;
        this.description = description;
    }
}
