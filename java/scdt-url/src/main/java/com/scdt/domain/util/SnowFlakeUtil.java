package com.scdt.domain.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 张来刚
 * 2021/10/9 0009.
 */
@Component
@Slf4j
@Deprecated
public class SnowFlakeUtil {

    private long workerId ;//为终端ID
    private long dataCenterId = 1;//数据中心ID
    private Snowflake snowflake = null;

    @PostConstruct
    public void init(){
        workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        //workid最大31，取模,位移取代
        workerId = workerId & 31;
        LogUtil.info(log,"当前机器的workId:{}",workerId);
        //使用默认起始日期，时钟回拨100毫秒就报错
        snowflake = new Snowflake(null,workerId,dataCenterId,true,100L);
    }

    public long nextId(){
        return snowflake.nextId();
    }

}
