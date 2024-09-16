package com.ttts.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UrlshortenerApplication {

		public static void main(String[] args) {
				SpringApplication.run(UrlshortenerApplication.class, args);
		}

}
