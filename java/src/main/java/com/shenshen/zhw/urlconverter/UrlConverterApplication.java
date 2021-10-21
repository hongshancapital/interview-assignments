package com.shenshen.zhw.urlconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.shenshen.zhw.urlconverter.**"})
public class UrlConverterApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.shenshen.zhw.urlconverter.UrlConverterApplication.class, args);
    }

}
