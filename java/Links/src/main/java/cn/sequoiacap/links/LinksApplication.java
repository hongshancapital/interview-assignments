package cn.sequoiacap.links;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author : Liushide
 * @date :2022/4/5 16:58
 * @description : 主启动类
 */
@EnableOpenApi
@SpringBootApplication
public class LinksApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinksApplication.class, args);
    }
}
