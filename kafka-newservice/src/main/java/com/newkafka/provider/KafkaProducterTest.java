package com.newkafka.provider;

import com.newkafka.provider.model.MessageNotifyModel;
import com.newkafka.provider.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijinpeng on 2018/9/10.
 */
@Slf4j
public class KafkaProducterTest {

    public static void main(String[] args) throws InterruptedException {

           provideMessage();

    }

    public static void provideMessage() {
        String location = "properties/spring/spring-context.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(location);
        KafkaTemplate kafkaTemplate = context.getBean("kafkaTemplate", KafkaTemplate.class);
        List<String> viewsPages = new ArrayList<String>();
        viewsPages.add("心灵鸡汤-001");
        viewsPages.add("人生感悟-002");
        MessageNotifyModel messageNotifyModel = new MessageNotifyModel("001", "lijinpeng", "2018-09-10", viewsPages, "3600");
        String message = JsonUtil.toJson(messageNotifyModel);
        kafkaTemplate.sendDefault(message);
    }




}
