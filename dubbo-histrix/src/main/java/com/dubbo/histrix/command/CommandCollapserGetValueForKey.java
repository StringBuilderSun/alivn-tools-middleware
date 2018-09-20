package com.dubbo.histrix.command;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 合并请求
 * Created by lijinpeng on 2018/9/20.
 */
public class CommandCollapserGetValueForKey extends HystrixCollapser<List<String>, String, Integer> {
    private Integer key;

    public CommandCollapserGetValueForKey(Integer key) {
        this.key = key;
    }

    public Integer getRequestArgument() {
        return key;
    }

    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Integer>> requests) {
        return new BatchCommand(requests);
    }

    protected void mapResponseToRequests(List<String> response, Collection<CollapsedRequest<String, Integer>> requests) {
        int count = 0;
        for (CollapsedRequest<String, Integer> request : requests) {
            request.setResponse(response.get(count++));
        }
    }

    private static final class BatchCommand extends HystrixCommand<List<String>> {
        private Collection<CollapsedRequest<String, Integer>> requests;

        protected BatchCommand(Collection<CollapsedRequest<String, Integer>> requests) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("batchCommand-group"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("batchCommand-command")));
            this.requests = requests;
        }

        protected List<String> run() throws Exception {
            ArrayList<String> response = new ArrayList<String>();
            for (CollapsedRequest<String, Integer> request : requests) {
                response.add("ValueForKey-" + request.getArgument());
            }
            return response;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            Future<String> future1 = new CommandCollapserGetValueForKey(1).queue();
            Future<String> future2 = new CommandCollapserGetValueForKey(2).queue();
            Future<String> future3 = new CommandCollapserGetValueForKey(3).queue();
            Future<String> future4= new CommandCollapserGetValueForKey(4).queue();
            System.out.println("result-future1:"+future1.get());
            System.out.println("result-future2:"+future2.get());
            System.out.println("result-future3:"+future3.get());
            System.out.println("result-future4:"+future4.get());

        } finally {
            context.shutdown();
        }
    }
}
