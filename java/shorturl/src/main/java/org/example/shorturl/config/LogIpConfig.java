package org.example.shorturl.config;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

/**
 * 继承ClassicConverter实现日志中打印机器ip,方便集群查询
 *
 * @author bai
 * @date 2022/3/19 18:47
 */
@Slf4j
public class LogIpConfig
        extends ClassicConverter {
    
    @Override
    @SneakyThrows
    public String convert(ILoggingEvent event) {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
