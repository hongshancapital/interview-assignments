package springboot.bench;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class DomainApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DomainApplication.class);
        app.setDefaultProperties(generateUndertowOptions());
        app.run(args);
    }
    
    public static Map<String, Object> generateUndertowOptions() {
        Map<String, Object> configMap = new HashMap<String, Object>();
        configMap.put("server.port", "11000");
        configMap.put("server.undertow.io-threads", 4);
        configMap.put("server.undertow.worker-threads", 32);
        configMap.put("server.undertow.max-http-post-size", "4MB");
        configMap.put("server.undertow.buffer-size", 1024);
        configMap.put("server.undertow.direct-buffers", true);
        configMap.put("spring.mvc.pathmatch.matching-strategy", "ANT_PATH_MATCHER");
        
        return configMap;
    }

}



