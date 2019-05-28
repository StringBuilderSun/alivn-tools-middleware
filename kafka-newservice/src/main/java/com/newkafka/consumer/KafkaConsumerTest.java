package com.newkafka.consumer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * Created by lijinpeng on 2019/5/27.
 */
public class KafkaConsumerTest {

    public static void main(String[] args) throws InterruptedException {
        final ApplicationContext context = new ClassPathXmlApplicationContext("properties/spring/spring-consumer.xml");
//        TimeUnit.SECONDS.sleep(30);
//        Thread thread = new Thread(new Runnable() {
//            public void run() {
//                ConcurrentMessageListenerContainer container = (ConcurrentMessageListenerContainer) context.getBean("messageListenerContainer");
//                container.stop();
//            }
//        });
//        thread.start();

    }
}
