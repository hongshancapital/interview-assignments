package com.hongshan.shorturl.config;

import com.hongshan.shorturl.model.url.HostRegister;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: huachengqiang
 * @date: 2022/1/16
 * @description:
 */
public class RegisterContext {
    private static Map<String, HostRegister> contextMap = new ConcurrentHashMap<>();

    static {
        HostRegister register = new HostRegister();
        register.setHost("www.baidu.com");
        register.setSecret("ks*7sdlSI)sd");
        contextMap.put(register.getHost(), register);
    }

    public static void register(HostRegister register) {
        contextMap.put(register.getHost(), register);
    }

    public static HostRegister find(String host) {
        return contextMap.get(host);
    }
}
