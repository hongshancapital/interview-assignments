# 短链接服务

### 概述

本服务采用自增序列法进行短链接的转换，具有避免碰撞的特点。具体使用`RedisAtomicLong`实现，也可以使用`mysql`自增主键`ID`实现。

项目使用`junit`对用到的方法进行了功能测试，并使用`jmeter`进行了接口压力测试

### 涉及技术

| 技术        | 描述            |
| ----------- | --------------- |
| Spring Boot | 提供接口服务    |
| Swagger2    | 自动生成API文档 |
| Redis       | 提供数据存储    |

### **存储结构**

```shell
127.0.0.1:6379> keys *
#自增主键
1) "short_url_code"
#短链接KEY VALUE为原链接
2) "short_url:LYYL"
#原链接KEY VALUE为生成时主键
3) "origin_url:http://www.sohu.com"

```

### **项目结构**

**├─src**
**│  ├─main**
**│  │  ├─java**
**│  │  │  └─com**
**│  │  │      └─asan**
**│  │  │          └─shorturlserver**
**│  │  │              │  ShortUrlServerApplication.java  `启动类`**
**│  │  │              ├─aop**
**│  │  │              │      WebLogAspect.java `接口切面文件`  
│  │  │              ├─config**
**│  │  │              │      BaseRedisConfig.java  `Redis配置文件`**
**│  │  │              │      GlobalExceptionHandler.java  `全局异常控制`     
│  │  │              ├─constants**
**│  │  │              │      Constants.java  `常量`  
│  │  │              ├─controller**
**│  │  │              │      ShortUrlController.java   `接口控制器`**
**│  │  │              ├─dto**
**│  │  │              │      ResponseResult.java  `统一返回`    
│  │  │              ├─service**
**│  │  │              │  │  ShortUrlService.java  `短链接服务`  
│  │  │              │  └─impl**
**│  │  │              │          ShortUrlServiceImpl.java  `短链接服务实现类`        
│  │  │              └─utils**
**│  │  │                      ShortUrlUtil.java  `工具类`               
│  │  └─resources**
**│  │      │  application.yml   `配置文件`**



