package com.newkafka.provider;

import com.newkafka.provider.model.MessageNotifyModel;
import com.newkafka.provider.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by lijinpeng on 2018/9/10.
 */
@Slf4j
public class KafkaProducterTest {

    public static void main(String[] args) throws InterruptedException {
        String location = "properties/spring/spring-provider.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(location);
        KafkaTemplate kafkaTemplate = context.getBean("kafkaTemplate", KafkaTemplate.class);
        List<String> viewsPages = new ArrayList<String>();
        viewsPages.add("心灵鸡汤-001");
        viewsPages.add("人生感悟-002");
        for (int i = 0; i < 100; i++) {
            TimeUnit.MICROSECONDS.sleep(100);
            MessageNotifyModel messageNotifyModel = new MessageNotifyModel(String.valueOf(i), "lijinpeng", "2018-09-10", viewsPages, "3600");
            String message = JsonUtil.toJson(messageNotifyModel);
            kafkaTemplate.sendDefault(message);
        }
    }
}
