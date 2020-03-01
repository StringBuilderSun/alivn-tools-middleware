package com.dubbo.histrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * Created by lijinpeng on 2018/9/18.
 */
public class SemaphoreCommand extends HystrixCommand<String> {
    private String name;

    public SemaphoreCommand(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("semaphore-command"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().
                        //使用信号量隔离   默认是线程池
                                withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)));
        this.name = name;
    }

    protected String run() throws Exception {
        return "服务响应结果result:" + Thread.currentThread().getName() + "-" + name;
    }


    public static void main(String[] args) {
        SemaphoreCommand command = new SemaphoreCommand("信号量");
        SemaphoreCommand command1 = new SemaphoreCommand("信号量");
        String result = command.execute();
        String result1= command1.execute();

        System.out.println(result);
        System.out.println(result1);

        System.out.println(Thread.currentThread().getName());
    }

}
