package com.kafka.service.model;

import com.kafka.service.enums.ProducerType;
import com.kafka.service.enums.RequiredAck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by lijinpeng on 2018/8/3.
 */
@Component
public class
TopicInfo {
    private static final Logger log = LoggerFactory.getLogger(TopicInfo.class);
    public static final int DEFAULT_REFRESHINTERVAL = 30000;

    public static final int LOW_WATERMARK_REFRESHINTERVAL = 3000;
    /**
     * 默认的kafka组
     */
    public static final String DEFAUT_GROUP = "default_group";
    /**
     * 定期的获取元数据的时间。当分区丢失，leader不可用时producer也会主动获取元数据，如果为0，
     * 则每次发送完消息就获取元数据，不推荐。如果为负值，则只有在失败的情况下获取元数据。
     */
    private Integer refreshInterval = Integer.valueOf(30000);
    /**
     * 消息同异步标识
     */
    private ProducerType sync;
    /**
     * 发送消息前等待服务节点消息确认机制
     */
    private RequiredAck requiredAck;
    /**
     * 主题名
     */
    private String topicName;
    /**
     * 注册中心地址
     */
    private String zkAdress;
    /**
     * kafka组名
     */
    private String groupName;
    /**
     * 服务器节点
     */
    private String brokerList;
    /**
     * 是否重试
     */
    private boolean retries;

    public Integer getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(Integer refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    public boolean isRetries() {
        return retries;
    }

    public void setRetries(boolean retries) {
        this.retries = retries;
    }

    public ProducerType getSync() {
        return sync;
    }

    public void setSync(ProducerType sync) {
        this.sync = sync;
    }

    public RequiredAck getRequiredAck() {
        return requiredAck;
    }

    public void setRequiredAck(RequiredAck requiredAck) {
        this.requiredAck = requiredAck;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getZkAdress() {
        return zkAdress;
    }

    public void setZkAdress(String zkAdress) {
        this.zkAdress = zkAdress;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getBrokerList() {
        return brokerList;
    }

    public void setBrokerList(String brokerList) {
        this.brokerList = brokerList;
    }
}
