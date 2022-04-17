package com.david.urlconverter;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author whohasthis
 */
@SpringBootApplication
public class UrlConverterServiceImplApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(UrlConverterServiceImplApplication.class).run(args);
    }
}
