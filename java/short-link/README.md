# 短域名服务

### 主要功能

- 短域名存储接口：接受长域名信息，返回短域名信息
- 短域名读取接口：接受短域名信息，返回长域名信息

### 技术引用

- Java 1.8
- Spring boot 2.5.8
- Caffeine 2.9.3
- Swagger 3.0
- Guava 31.1-jre


##### 目录结构
```
short-link
     ├── config             -- 配置
     
     ├── controller         -- 接口层
           └── advice       -- 增强配置
           
     ├── exception          -- 异常类
           
     ├── model
           ├── request      -- 接口请求参数
           └──  response     -- 接口返回参数
           
     ├── repository         -- 数据仓库层
            
     ├── service            -- 业务层
     
     └── util               -- 工具类
     
```
