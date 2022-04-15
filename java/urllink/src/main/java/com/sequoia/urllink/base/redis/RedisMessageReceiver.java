package com.sequoia.urllink.base.redis;

public interface RedisMessageReceiver {
    public void receiveMessage(Object message);
}