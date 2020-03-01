package com.kafka.service.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取机器IP帮助类
 * Created by lijinpeng on 2018/8/8.
 */
public class IPUtil {
    private final static Logger log = LoggerFactory.getLogger(IPUtil.class);

    public IPUtil() {
    }

    /**
     * 获取主机IP
     * @return
     */
    public static String getLocalIP() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            return address.getHostAddress();
        } catch (UnknownHostException var1) {
            log.error("找不到本地主机名对应的IP：", var1);
        } catch (SecurityException var2) {
            log.error("该线程没有权限打开到本地主机的Socket连接：", var2);
        } catch (NullPointerException var3) {
            log.error("找不到本地主机名称：", var3);
        }
        return "888.888.888.888";
    }
}
