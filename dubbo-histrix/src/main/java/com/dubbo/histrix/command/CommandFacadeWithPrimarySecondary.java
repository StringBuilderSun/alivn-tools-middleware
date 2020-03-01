package com.dubbo.histrix.command;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

/**
 * 特殊业务处理 显示调用fallback
 * fallback不再当作降级使用 而是作为业务处理
 * Created by lijinpeng on 2018/9/19.
 */
public class CommandFacadeWithPrimarySecondary extends HystrixCommand<String> {

    private static DynamicBooleanProperty usePrimary = DynamicPropertyFactory.getInstance().getBooleanProperty("primarySecondary.usePrimary", true);
    private int id;

    protected CommandFacadeWithPrimarySecondary(int id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("facade-command"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("business-service")));
        this.id = id;
    }

    protected String run() throws Exception {
        if (usePrimary.getValue()) {
            return new PrimaryCommand(id).execute();
        } else {
            return new SecondCommand(id).execute();
        }
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }

    @Override
    protected String getFallback() {
        return "主业务调用失败-" + id;
    }

    public static void main(String[] args) {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        ConfigurationManager.getConfigInstance().addProperty("primarySecondary.usePrimary", false);
        try {
            CommandFacadeWithPrimarySecondary command = new CommandFacadeWithPrimarySecondary(1);
            CommandFacadeWithPrimarySecondary command2 = new CommandFacadeWithPrimarySecondary(2);
            String result = command.execute();
            String result2 = command2.execute();
            System.out.println(result + " isCache:" + command.isResponseFromCache());
            System.out.println(result2 + " isCache:" + command.isResponseFromCache());
            CommandFacadeWithPrimarySecondary command3 = new CommandFacadeWithPrimarySecondary(1);
            String result3 = command3.execute();
            System.out.println(result3 + " isCache:" + command3.isResponseFromCache());
        } finally {
            context.shutdown();
        }
    }
}
