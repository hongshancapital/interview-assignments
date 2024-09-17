package com.liuwangpan.urlconvert.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @Deacription read local server config
 * @author wp_li
 **/
@Component
public class ApplicationConfig implements ApplicationRunner {

    @Autowired
    private ApplicationContext applicationContext;
    /**
     * server path
     */
    public static String SHORT_DOMAIN_PATH = "http://localhost:";
    /**
     * url prefix
     */
    private static final String SHORT_PATH = "/s/";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Environment environment = applicationContext.getBean(Environment.class);
        SHORT_DOMAIN_PATH += environment.getProperty("server.port") + SHORT_PATH;
    }
}