package com.gege.assignment;

import com.spring4all.swagger.EnableSwagger2Doc;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.LongAdder;


/**
 * @author 3511
 */
@SpringBootApplication
@RestController
@Api(tags = "资源" )
@EnableSwagger2Doc
@EnableWebMvc
public class AssignmentApplication {

    /**
     * 短域名最大长度
     */
    private static final Integer MAX_LENGTH = 8;


    /**
     * 域名映射最大容量
     */
    private static final Long MAX_MAP_SIZE = 1000000L;

    /**
     * 长短域名映射集合   线程安全  shortUrl -> url
     */
    private ConcurrentMap<String,String> urls = new ConcurrentHashMap<>();

    /**
     * id 计数器
     */
    private static LongAdder count = new LongAdder();


    public static void main(String[] args) {
         SpringApplication.run(AssignmentApplication.class, args);
    }

    @PostMapping("shorUrl")
    @ApiOperation(value = "短域名存储接口",notes = "接受长域名信息，返回短域名信息")
    public String getShortUrl(String url){
        synchronized (this){
            Set<String> keys = urls.keySet();
            // 判断是否存在
            Optional<String> first = keys.stream().filter(key -> urls.get(key).equals(url)).findFirst();
            if(!first.isPresent()){
                // 10进制转 62进制
                String shortUrl = ConversionUtil.encode(count.longValue(),MAX_LENGTH);
                    //容器是否大于指定大小
                    if(shortUrl.length()>MAX_LENGTH||urls.size()>MAX_MAP_SIZE){
                        System.out.println(urls.size());
                        // 数量过大则进行循环覆盖
                        count.reset();
                        // 10进制转 62进制 此处 count 等于0
                        shortUrl = ConversionUtil.encode(count.longValue(),MAX_LENGTH);
                    }
                    urls.put(shortUrl,url);
                    count.increment();
                    return shortUrl;
            }
            return first.get();
        }
    }


    @PostMapping("url")
    @ApiOperation(value = "短域名读取接口",notes = "接受短域名信息，返回长域名信息。")
    public String getUrl(@Nullable String shortUrl){
        return urls.get(shortUrl);
    }
}
