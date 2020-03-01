package com.rabbitmq.service.springRabbit;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: lijinpeng
 * Created by Shanghai on 2019/6/2.
 */
public class SpringRabbitTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-context.xml");
        SpringRabbitProducter producter = (SpringRabbitProducter) applicationContext.getBean("springRabbitProducter");
        for (int i = 0; i < 100; i++) {
            producter.sendMessage(String.valueOf(i));
        }
    }
}
