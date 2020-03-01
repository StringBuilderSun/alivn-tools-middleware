package com.dubbo.histrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.concurrent.TimeUnit;

/**
 * 自定义回调方法
 * 当调用的服务出现异常的时候  调用mock结果
 * Created by lijinpeng on 2018/9/18.
 */
public class DefiendFallBackCommand extends HystrixCommand<String> {

    private String name;

    protected DefiendFallBackCommand(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("fallBack_group")).andCommandPropertiesDefaults(
                //设置调用依赖的超时事件为500ms  一旦依赖调用超过500ms 立即调用自定义的fallback
                HystrixCommandProperties.Setter().withExecutionIsolationThreadTimeoutInMilliseconds(500)
        ));
        this.name = name;
    }

    protected String run() throws Exception {
        //接口直接设置超时
        TimeUnit.MILLISECONDS.sleep(1000);
        return "调用接口Result:" + name;
    }

    @Override
    protected String getFallback() {
        return "服务调用失败，返回MOCK结果";
    }

    public static void main(String[] args) {
        //验证服务mock结果
        DefiendFallBackCommand command = new DefiendFallBackCommand("服务响应成功");
        String result = command.execute();
        System.out.println("result:" + result);
    }
}
