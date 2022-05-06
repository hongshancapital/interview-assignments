package com.scdt.tinyurl.config;


import com.scdt.tinyurl.common.ErrorCode;
import com.scdt.tinyurl.exception.GlobalException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Configuration
@Getter
@Setter
@Slf4j
public class AppConfig {


    public static final int BASE_62 = 62;

    @Value("${application.storage.scheduler-thread-size:5}")
    private int storageSchedulerThreadSize;

    @Value("${application.broker-id}")
    private String brokerId;

    @Value("${application.storage.expired-after-write.duration:300}")
    private Long storageExpiredDuration;

    @Value("${application.storage.expired-after-write.timeunit:second}")
    private String storageExpiredTimeunit;

    @Value("${application.cache.max-percentage:0.2}")
    private BigDecimal cachePercentage;

    @Value("${application.cache.scheduler-thread-size:5}")
    private int cacheSchedulerThreadSize;


    @Value("${application.cache.expired-after-write.duration:300}")
    private Long cacheExpiredDuration;

    @Value("${application.cache.expired-after-write.timeunit:second}")
    private String cacheExpiredTimeunit;

    @Value("${application.codec-dict:0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ}")
    private String codecDict;

    @Value("${application.codec-length:8}")
    private int codecLength;

    @Value("${application.storage.max-capacity:999999}")
    private Long maxCapacity;

    private int maxIdLength;

    @PostConstruct
    public void init() {

        maxIdLength = String.valueOf((long)Math.pow(BASE_62,codecLength)).length();

        maxCapacity = deduceMaxCapacityByBrokerId(brokerId,codecLength);
        log.info("当前机器编号{} 分配的最大ID序号范围为: 0 - {}",brokerId,maxCapacity);
    }

    //通过broker-id和62进制编码的最大长度判断当前机器最大分配的ID值
    private Long deduceMaxCapacityByBrokerId(String brokerId,int codecLength) {

        //机器编号前缀的长度
        int prefixLength= brokerId.length();

        String maxValue = String.valueOf((long)Math.pow(BASE_62,codecLength));

        long maxId = 0L;

        //比较配置的ID最大值和计算出的最大ID，取较小值返回
         if(brokerId.length() > maxValue.length() || brokerId.compareTo(maxValue.substring(0,prefixLength)) > 0) {
            throw new GlobalException(ErrorCode.INVALID_BROKER_ID_ERROR);
        } else if(brokerId.equals(maxValue.substring(0,prefixLength))) {
            maxId = Long.parseLong(maxValue.substring(prefixLength));
        }  else {
            maxId = (long) Math.pow(10,maxValue.length()-prefixLength) - 1;
        }
        return maxId < maxCapacity ? maxId : maxCapacity;

    }

    //字符串转时间单位
    public TimeUnit getExpireTimeUnit(String timeUnit) {

        if(timeUnit == null) return TimeUnit.SECONDS;

        switch (timeUnit.toLowerCase()) {
            case "minute":
                return TimeUnit.MINUTES;
            case "hour":
                return TimeUnit.HOURS;
            default:
                return TimeUnit.SECONDS;
        }
    }


}
