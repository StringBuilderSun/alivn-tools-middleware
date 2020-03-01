package com.dubbo.histrix.command;

import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesCommandDefault;


/**
 * Created by lijinpeng on 2018/9/19.
 */
public class PrimaryCommand extends HystrixCommand<String> {
    private int id;

    protected PrimaryCommand(int id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("facade-command"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("primary-service"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("primary-pool"))
                //超时0.5s
                .andCommandPropertiesDefaults(HystrixPropertiesCommandDefault.Setter().withExecutionIsolationThreadTimeoutInMilliseconds(500)));
        this.id = id;
    }

    protected String run() throws Exception {
        return "response-primary-"+id;
    }
}
