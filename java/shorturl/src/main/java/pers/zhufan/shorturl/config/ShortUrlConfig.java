package pers.zhufan.shorturl.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ShortUrlConfig {

    //短链接长度，默认8
    @Value("${shorturl.lenth:8}")
    private int lenth;

    //长链接做hash操作，附加到长链接前面，防止算法泄密
    @Value("${md5.prefix.key:md5defaultkey}")
    private String key;

}
