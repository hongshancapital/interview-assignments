package com.wangxiao.shortlink.infrastructure.register;

public interface RegisterCenter {
    void registe(String machineId);

    void unRegisteMethod(String machineId, String method);
}
