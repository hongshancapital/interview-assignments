package interview.assignments.zhanggang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class ZhanggangApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZhanggangApplication.class, args);
	}

}
