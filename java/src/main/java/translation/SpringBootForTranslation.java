package translation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 长短域名转换
 * @author: hello
 * @since: 2023/2/21
 */
@SpringBootApplication
public class SpringBootForTranslation {
    private final static Logger log = LoggerFactory.getLogger(SpringBootForTranslation.class);
    public static void main(String[] args) {
        SpringApplication.run(SpringBootForTranslation. class,args);
        log.info("长短区域名-启动成功");
    }
}