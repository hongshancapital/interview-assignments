package cn.leirq.demoshorturl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
public class DemoShortUrlApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoShortUrlApplication.class, args);
    }

}
