package com.dubbo.histrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 最基本的Command
 * Created by lijinpeng on 2018/9/18.
 */
public class HelloCommand extends HystrixCommand<String> {
    private final String name;

    protected HelloCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("exampleGroup"));
        this.name = name;
    }

    protected String run() throws Exception {
        return "Hello:" + name + "  thread:" + Thread.currentThread().getName();
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        HelloCommand command1 = new HelloCommand("excute--result");
        //结果立即执行
        String result = command1.execute();
        System.out.println("excute---result:" + result);
        HelloCommand command2 = new HelloCommand("future--result");
        //结果异步执行
        Future<String> future = command2.queue();
        //get操作不能超过command定义的超时时间,默认:1秒
        result = future.get(100, TimeUnit.MILLISECONDS);
        System.out.println("future---result:" + result);
        //注册观察者事件拦截
        Observable<String> observer = new HelloCommand("obsever").observe();
        //订阅结果回调事件 接口任务执行完成 对返回结果进行包装处理
        observer.subscribe(new Action1<String>() {
            //对执行结果进行再次处理
            public void call(String result) {
                System.out.println("我要对执行结果进行二次处理result:" + result + "--again");
            }
        });
        //执行完整的生命周期
        observer.subscribe(new Observer<String>() {
            //onError  onNext 执行完成后调用
            public void onCompleted() {
                System.out.println("onCompleted:execute finsh！");
            }

            //当产生异常时回调
            public void onError(Throwable throwable) {
                System.out.println("on error:excute happend exception:" + throwable.getMessage());
            }

            //当接口执行完成后回调
            public void onNext(String s) {
                System.out.println("onNext:" + s);
            }
        });
    }
}
