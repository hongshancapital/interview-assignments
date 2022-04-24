package io.nigelwy.javaassignments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class JavaAssignmentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaAssignmentsApplication.class, args);
    }

}
