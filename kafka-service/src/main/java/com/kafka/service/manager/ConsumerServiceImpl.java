package com.kafka.service.manager;

import com.kafka.service.manager.ConsumerMessageManager;
import com.kafka.service.factory.ConsumerFactory;
import com.kafka.service.model.TopicInfo;
import com.kafka.service.service.BusinessProcessInterface;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

/**
 * 消费服务类
 * Created by lijinpeng on 2018/8/8.
 */
@Slf4j
@Service
public class ConsumerServiceImpl implements DisposableBean {

    private TopicInfo topicInfo;

    public ConsumerServiceImpl() {

    }

    /**
     * 消费消息
     *
     * @param topicInfo       主题
     * @param businessProcess 消费消息业务处理类
     * @param traceLogId      业务追踪号
     */
    public void consumerMessage(TopicInfo topicInfo, BusinessProcessInterface businessProcess, String traceLogId) throws InstantiationException, IllegalAccessException {
        this.topicInfo = topicInfo;
        BusinessProcessInterface businessProcessInterface = ConsumerMessageManager.getBusinessProcessInterfaceProxy(topicInfo, businessProcess);
        ConsumerMessageManager.process(topicInfo, businessProcessInterface, traceLogId);
        log.info("消费组:{}  消费topic:{} 成功！",topicInfo.getGroupName(),topicInfo.getTopicName());
    }

    /**
     * 消费者连接是基于长连接的方式存在的
     * 对象销毁时候  关闭 消费者连接器
     *
     * @throws Exception
     */
    public void destroy() throws Exception {
        try {
            if (this.topicInfo != null) {
                ConsumerFactory.getConnector(topicInfo).shutdown();
            }
        } catch (Exception e) {
            log.error("exception occured whiling closing connection");
        }
    }
}
