package com.d00216118.demo;

import com.d00216118.demo.model.InfoUrl;
import com.d00216118.demo.repository.UrlRepository;
import com.d00216118.demo.service.UrlServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableCaching
@EnableJpaAuditing
//@EnableSwagger2
public class DemoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApiApplication.class, args);
    }


/*    @Bean
    public CommandLineRunner testDemo(UrlServiceImpl urlService) {

		return args -> {

			String url="http://baike.baidu.com/link?url=ZbFVNe9FDaoht-ZHu9snNTFPk2QEEWEz8-oFSkd4Fdo0pzPDwolG1tubpZ-bglJEDnqf6O4hIJ5dEIAW3IAQke3cyF-H3YmZ0EX65Z2xdjw1suMk56IxG-uNzAq9lI57M636J7FgNB5So2_1OPTk7oVe_5E_NhkiWlTqMpPpmxZj7m07Mk4UcL4HboQSF6Or";

			InfoUrl infoUrl = new InfoUrl();
			infoUrl.setUrl(url);

//			InfoUrl t = urlRepository.save(infoUrl);

//			InfoUrl t = UrlServiceImpl
			InfoUrl t = urlService.convertToTinyUrl(infoUrl);

					System.out.println(t);
			System.out.println(System.currentTimeMillis());

		};
	}
	*/

}


