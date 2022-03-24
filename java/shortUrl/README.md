# 短链接技术方案

## 整体依赖
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.6.4</version>
    <relativePath/>
    <!--  lookup parent from repository  -->
</parent>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>${swagger.version}</version>
</dependency>

<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>${guava.version}</version>
</dependency>

<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

## 数据校验
采用spring 注解实现
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```
@URL 注解实现参数校验
```java
    @ApiOperation("获取短链接")
    @GetMapping("/shortUrl")
    public String getShortUrl(@URL @ApiParam(value = "长连接") @RequestParam("url") String url) {
        return shortUrlService.getShortUrl(url);
    }
```

## API 文档 swagger(3.0)
swagger配置
```java
package com.su.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 * @author hejian
 * swagger 配置信息
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    /**
     * 用于读取配置文件 application.properties 中 swagger 属性是否开启
     */
    @Value("${swagger.enabled}")
    private Boolean swaggerEnabled;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                // 是否开启swagger
                .enable(swaggerEnabled)
                .select()
                // 过滤条件，扫描指定路径下的文件
                .apis(RequestHandlerSelectors.basePackage("com.su"))
                // 指定路径处理，PathSelectors.any()代表不过滤任何路径
                //.paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("hejian", "https://github.com/hejian3", "hejian3@qq.com");
        return new ApiInfo(
                "短链接",
                "实现短链接存储及查找",
                "v1.0",
                "https://github.com/hejian3",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>()
        );
    }
}
```
maven 依赖
```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```

## 核心实现

### 短链接实现方式
通过hash算法实现，因hash可能产生hash冲突，通过给原url添加时间戳参数实现

hash算法实现
```xml
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>27.1-jre</version>
</dependency>
```
具体实现代码
```java
HashCode hashCode = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8);
```
短链接hash冲突处理
```java
public String getShortUrl(String url) {
    if (longUrlCache.asMap().containsKey(url)) {
        return longUrlCache.asMap().get(url);
    }
    String surl = url;
    boolean flag = false;
    while (true) {
        String shortUrl = prefix + urlSuffixService.getSuffix(surl);
        if (!shortUrlCache.asMap().keySet().contains(shortUrl)) {
            shortUrlCache.put(shortUrl, url);
            longUrlCache.put(url, shortUrl);
            return shortUrl;
        }
        surl = url + "/" + System.currentTimeMillis();
    }
}
```

### 短链接存储方式
通过将数据存储LRU cache中,具体实现如下：
```java
 private Cache<String, String> shortUrlCache =
            CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.MINUTES).maximumSize(1000).build();

private Cache<String, String> longUrlCache =
        CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.MINUTES).maximumSize(1000).build();
```


### 单元测试代码覆盖率
![image](https://user-images.githubusercontent.com/4374836/159826473-4b8808ba-017f-4885-9ce2-dfe1c3f14ddb.png)
