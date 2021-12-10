package cn.sequoiacap.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * 域名段服务应用程序入口
 * @author liuhao
 * @datetime 2021/12/10
 */
@EnableOpenApi
@EnableWebMvc
@SpringBootApplication
public class DomainServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DomainServiceApplication.class, args);
    }

}
