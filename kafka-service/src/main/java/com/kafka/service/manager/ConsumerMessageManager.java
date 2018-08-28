package com.kafka.service.manager;

import com.kafka.service.factory.ConsumerFactory;
import com.kafka.service.model.TopicInfo;
import com.kafka.service.proxy.ProcessInvocationHandler;
import com.kafka.service.serializer.MessageSerializer;
import com.kafka.service.service.BusinessProcessInterface;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.consumer.Whitelist;
import kafka.message.MessageAndMetadata;
import kafka.utils.VerifiableProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 消息消费管理器
 * 功能点
 * 1、构造业务处理代理类
 * 2、取出待消费的消息 给指定的业务处理类消费消息
 * Created by lijinpeng on 2018/8/8.
 */
@Slf4j
public class ConsumerMessageManager {

    /**
     * 供业务代理类存放的线程安全的hashMap
     */
    private static ConcurrentHashMap<String, BusinessProcessInterface> businessProcessMap = new ConcurrentHashMap<String, BusinessProcessInterface>();

    /**
     * 开启一个线程消费消息
     *
     * @param topicInfo       主题
     * @param businessProcess 业务处理类
     * @param traceLogId      日志追踪号
     */
    public  static void process(final TopicInfo topicInfo,final BusinessProcessInterface businessProcess, final String traceLogId) {
        //开启一个线程执行操作
        new Thread(new Runnable() {
            public void run() {
                log.debug("开启线程消费消息......");
                getMessageAndConsumer(topicInfo, businessProcess, traceLogId);
            }
        }).start();
    }

    /**
     * 处理kafka的消息
     *
     * @param topicInfo       主题
     * @param businessProcess 处理kafka的业务类
     * @param traceLogId
     */
    private static void getMessageAndConsumer(TopicInfo topicInfo, BusinessProcessInterface businessProcess, String traceLogId) {
        log.info("topicName:{} groupName:{}", topicInfo.getTopicName(), topicInfo.getGroupName());
        //1、获取消费者连接器
        Whitelist filter = new Whitelist(topicInfo.getTopicName());//订阅指定topic的消息
        MessageSerializer decoder = new MessageSerializer(new VerifiableProperties());//封装的反序列化工具
        //2、获取topic下的kafka消息集合
        List consumerList = ConsumerFactory.getConnector(topicInfo, traceLogId).createMessageStreamsByFilter(filter,1,decoder,decoder);
        Iterator iterators = consumerList.iterator();
        while (iterators.hasNext()) {
            try {
                //3、获取一个kafka消息
                KafkaStream kafkaStream = (KafkaStream) iterators.next();
                ConsumerIterator consumerIterator = kafkaStream.iterator();
                while (consumerIterator.hasNext()) {
                    MessageAndMetadata metadata = consumerIterator.next();
                    Object message = metadata.message();
                    log.debug("消费者要消费的消息:{} topic:{} offset:{} key:{} partition:{} message:{}", new Object[]{message.toString(), metadata.topic(), Long.valueOf(metadata.offset()), metadata.key(), Integer.valueOf(metadata.partition()), message});
                    businessProcess.doBusiness(message);
                }

            } catch (Throwable var12) {
                log.error("an exception occurs when process message : ", var12);
                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException var11) {
                    log.error("thread interrupted Exception: ", var11);
                }
            }
        }
    }

    /**
     * 获取与指定topic相关联的业务处理消息类的代理类
     *
     * @param topicInfo       主题
     * @param businessProcess 业务处理实现类(要被代理增强的类)
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static BusinessProcessInterface getBusinessProcessInterfaceProxy(TopicInfo topicInfo, BusinessProcessInterface businessProcess) throws IllegalAccessException, InstantiationException {
        //先从缓存map中查找
        BusinessProcessInterface businessProcessProxy = businessProcessMap.get(topicInfo);
        if (businessProcessProxy == null) {
            //创建代理类
            businessProcessProxy = (BusinessProcessInterface) Proxy.newProxyInstance(businessProcess.getClass().getClassLoader(),businessProcess.getClass().getInterfaces(), new ProcessInvocationHandler(businessProcess));
            //放入到缓存map
            businessProcessMap.put(topicInfo.getTopicName(),businessProcessProxy);
        }
        return businessProcessProxy;
    }
}
