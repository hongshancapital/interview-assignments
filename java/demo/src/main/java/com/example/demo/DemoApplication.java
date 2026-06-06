package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class DemoApplication {

    public static void main( String[] args ) throws UnknownHostException {
        ApplicationContext ctx=SpringApplication.run(DemoApplication.class, args);
        Environment env=ctx.getEnvironment();
        String ip=InetAddress.getLocalHost().getHostAddress();
        String port=env.getProperty("server.port");
        String path=env.getProperty("server.servlet.context-path");
        System.out.print("\n----------------------------------------------------------\n\t" +
                "Application 基础服务模块启动正常! \n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "Swagger-UI: \t\thttp://" + ip + ":" + port + path + "/doc.html\n" +
                "----------------------------------------------------------");
    }

}
