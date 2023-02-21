package com.wangxiao.shortlink.infrastructure.register;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
@Slf4j
public class RegisterCenterImpl implements RegisterCenter {

    private static String IP;

    static {
        try {
            IP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("IP获取异常无法注册！");
        }
    }

    @Override
    public void registe(String machineId) {
        log.info("注册机器，machineId：{}，IP：{}", machineId, IP);
    }

    @Override
    public void unRegisteMethod(String machineId, String method) {
        log.info("下线服务，machineId：{}，IP：{}，method:{}", machineId, IP, method);
    }
}
