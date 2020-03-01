package com.dubbo.histrix.command;

import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesCommandDefault;

/**
 * Created by lijinpeng on 2018/9/19.
 */
public class SecondCommand extends HystrixCommand<String> {

    private int id;

    protected SecondCommand(int id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("facade-command"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("second-service"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("second-pool"))
                //0.1s 超时
                .andCommandPropertiesDefaults(HystrixPropertiesCommandDefault.Setter().withExecutionIsolationThreadTimeoutInMilliseconds(100)));
        this.id = id;
    }

    protected String run() throws Exception {
        return "response-second-" + id;
    }
}
