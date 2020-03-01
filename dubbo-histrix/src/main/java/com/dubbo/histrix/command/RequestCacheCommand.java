package com.dubbo.histrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

/**
 * 请求缓存 Request-Cache
 * Created by lijinpeng on 2018/9/18.
 */
public class RequestCacheCommand extends HystrixCommand<String> {
    private int id;

    protected RequestCacheCommand(int id) {
        super(Setter.withGroupKey(
                //为同一类服务设置隔离组
                HystrixCommandGroupKey.Factory.asKey("cache-command")).
                //为同一种服务依赖的抽象设置隔离
                        andCommandKey(HystrixCommandKey.Factory.asKey("pay-service"))
                //同一个服务如果服务请求的方式不一样  可以这样设置 以做隔离
                //比如一个服务 redis  和 http的方式 (线程池隔离)
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("request-http"))
        );
        this.id = id;
    }

    @Override
    protected String getCacheKey() {
        //缓存的key 按照 id去获取
        return String.valueOf(id);
    }

    protected String run() throws Exception {
        return "服务响应:" + Thread.currentThread().getName() + String.valueOf(id);
    }

    public static void main(String[] args) {
        HystrixRequestContext requestContext = HystrixRequestContext.initializeContext();
        try {
            RequestCacheCommand requestCache1 = new RequestCacheCommand(1);
            RequestCacheCommand requestCache2 = new RequestCacheCommand(1);
            requestCache1.execute();
            requestCache2.execute();
            System.out.println("requestCache1 is Cache:" + requestCache1.isResponseFromCache());
            System.out.println("requestCache2 is Cache:" + requestCache2.isResponseFromCache());
        } finally {
            requestContext.shutdown();
        }
        //重新开启  这个时候不会直接从缓存中拿了
        requestContext = HystrixRequestContext.initializeContext();
        try {
            RequestCacheCommand requestCache3 = new RequestCacheCommand(1);
            requestCache3.execute();
            System.out.println("requestCache3 is Cache:" + requestCache3.isResponseFromCache());
        } finally {
            requestContext.shutdown();
        }
    }
}
