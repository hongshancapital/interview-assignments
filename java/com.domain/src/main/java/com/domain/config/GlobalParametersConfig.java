package com.domain.config;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import java.util.HashSet;
import java.util.Set;

/**
 * 全局系统变量参数
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
@Data
public class GlobalParametersConfig implements InitializingBean {

    @Value("${domain.url}")
    private String url;  //短域名地址

    @Value("${domain.limit}")
    private String limit;  //限流值

    @Value("${domain.authentication.enabled}")
    private String authEnabled;  //是否启动鉴权

    @Value("${domain.authentication.token}")
    private String accessToken;  //鉴权token

    @Value("${domain.authentication.exclude.url}")
    private String authExcludeUrl;  //不鉴权URL


    @Value("${domain.stone.file.config}")
    private String configFilePath;  //索引/指针文件目录

    @Value("${domain.stone.file.data}")
    private String dataFilePath;  //存储文件目录

    @Value("${domain.stone.slots}")
    private String slots;  //存储槽位

    @Value("${domain.stone.capacity}")
    private String capacity;  //槽LRU 最大扩容

    private Set<String> authExcludeUrls;

    /**
     * BEAN 初始化
     * @param
     * @return
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        splitAuthUrl();
    }

    /**
     * 解析需要排除鉴权的URL 放入缓存容器中
     * @param
     * @return
     */
    protected void splitAuthUrl(){
        if(!StringUtils.isEmpty(authExcludeUrl)){
            String[] urls=authExcludeUrl.split("\\,");
            authExcludeUrls=new HashSet<String>(urls.length);
            for(String url:urls){
                authExcludeUrls.add(url);
            }
        }
    }



}
