package com.kafka.service.proxy;

import com.kafka.service.service.BusinessProcessInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 业务代理类处理器
 * 功能点
 * 对代理类执行特殊增强操作
 * Created by lijinpeng on 2018/8/8.
 */
public class ProcessInvocationHandler implements InvocationHandler {
    private final static Logger log= LoggerFactory.getLogger(ProcessInvocationHandler.class);
    private BusinessProcessInterface businessProcess;

    public ProcessInvocationHandler(BusinessProcessInterface businessProcess) {
        this.businessProcess = businessProcess;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.debug("ProcessInvocationHandler intercept BusinessProcessInterface!!!!");
        return method.invoke(this.businessProcess,args);
    }
}
