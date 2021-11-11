package com.lynnhom.sctdurlshortservice;

import com.lynnhom.sctdurlshortservice.service.UrlMappingCacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SctdUrlShortServiceApplication.class})
public class SctdUrlShortServiceApplicationTests {

    @Autowired
    protected UrlMappingCacheService urlMappingCacheService;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneSecond = now.minusSeconds(-1L);
        LocalDateTime oneMinute = now.minusMinutes(-1L);
        LocalDateTime oneHour = now.minusHours(-1L);
        LocalDateTime oneDay = now.minusDays(-1L);

        /**
         * https://github.com/scdt-china/interview-assignments  -> hpoZMZ  now
         * https://github.com/scdt-china/interview-assignments/tree/master/java  ->  hAEu9V  oneSecond
         * https://github.com/scdt-china/interview-assignments/tree/master/fullstack  -> e3WuDO  oneSecond
         * https://github.com/scdt-china/interview-assignments/tree/master/design  ->  hjgqWi  oneMinute
         * https://github.com/scdt-china/interview-assignments/tree/master/php  ->  eYvlAF  oneMinute
         * https://github.com/scdt-china/interview-assignments/tree/master/dev-ops  —>  hxU8n2  oneHour
         * https://github.com/scdt-china/interview-assignments/tree/master/qa  ->  eZEUKF  oneHour
         * https://github.com/scdt-china/interview-assignments/tree/master/frontend  ->  eNOZWY  oneDay
         * https://github.com/scdt-china/interview-assignments/tree/master/swift  ->  90DUMh  oneDay
         */
        urlMappingCacheService.insertMapping("001", "https://github.com/scdt-china/interview-assignments", now);
        urlMappingCacheService.insertMapping("001", "https://github.com/scdt-china/interview-assignments/tree/master/java", oneSecond);
        urlMappingCacheService.insertMapping("001", "https://github.com/scdt-china/interview-assignments/tree/master/design", oneMinute);
//        urlMappingCacheService.insertMapping("001", "https://github.com/scdt-china/interview-assignments/tree/master/dev-ops", oneHour);
//        urlMappingCacheService.insertMapping("001", "https://github.com/scdt-china/interview-assignments/tree/master/frontend", oneDay);

        urlMappingCacheService.insertMapping("002", "https://github.com/scdt-china/interview-assignments/tree/master/fullstack", oneSecond);
        urlMappingCacheService.insertMapping("002", "https://github.com/scdt-china/interview-assignments/tree/master/php", oneMinute);
        urlMappingCacheService.insertMapping("002", "https://github.com/scdt-china/interview-assignments/tree/master/qa", oneHour);
        urlMappingCacheService.insertMapping("002", "https://github.com/scdt-china/interview-assignments/tree/master/swift", oneDay);
    }

    @Test
    void contextLoads() {
    }
}
