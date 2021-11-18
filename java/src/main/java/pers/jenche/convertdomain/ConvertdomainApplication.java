package pers.jenche.convertdomain;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConvertdomainApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ConvertdomainApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("系统已经启动……");
    }
}
