package com.url.transform;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Arrays;

@Slf4j
@SpringBootApplication()
public class TransformApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

//       ConfigurableApplicationContext run = SpringApplication.run(TransformApplication.class, args);
//        String[] activeProfiles = run.getEnvironment().getActiveProfiles();
//        log.info(" ***** current active profiles are :{} ******", Arrays.toString(activeProfiles));

        SpringApplication.run(TransformApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(TransformApplication.class);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
    }

}
