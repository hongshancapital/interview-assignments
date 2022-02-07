package com.xg.shorturl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 启动类，支持优雅关机
 * @author xionggen
 */
@SpringBootApplication
@Slf4j
public class ShortUrlApplication implements EmbeddedServletContainerCustomizer {

    @Value("${server.port}")
    private int servePort;

    public static void main(String[] args) {
        try {
            SpringApplication springApplication = new SpringApplication(ShortUrlApplication.class);
            springApplication.setRegisterShutdownHook(false);
            ConfigurableApplicationContext ctx = springApplication.run(args);
            Runtime.getRuntime().addShutdownHook(
                    new Thread(() -> {
                        try {
                            Thread.sleep(5000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ctx.close();
                    }));
        } catch (Exception ex) {
            log.error("SpringApplication.run with Exception", ex);
            System.exit(1);
        }
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.setPort(servePort);
    }
}
