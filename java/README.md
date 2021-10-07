# 实现短域名服务（细节可以百度/谷歌）
##功能性需求
1. 创建一个长域名转换成一个短域名（write）
2. 根据短域名获得长域名（read）
3. 短域名长度最大为 8 个字符
4. 采用SpringBoot，集成Swagger API文档；
5. JUnit编写单元测试, 使用Jacoco生成测试报告(测试报告提交截图即刻)；
6. 映射数据存储在JVM内存即可，防止内存溢出；

##条件假设
1.短域名地址长度
和写的数量有关系

* 使用[a-z,A-Z,0-9,'-','_'] → 26(小写字母)+26(大写字母)+10(数字)+2(特殊符号) = 64（每种字符有64种可能）
* 假设短域名的长度为7，那么可以生成的短域名地址有64的7次方个约等于4.39e12个短地址
* 假设1000 writes/s => 3.15e10 writes/year => 可使用139年
* 假设写提升100倍 100,000 writes/s => 7个短域名长度可以使用1.4年 => 将域名长度设置为8个可以使用89年

2.URL长度和存储
* 不同的浏览器设定的上限不一样，其中最短的是IE浏览器长度，length ≤ 2083 字符，为了兼容所有的浏览器我们会取最短的长度2083
* 每条记录的长度估算：8+2083+4(过期时间) = 2095 chars
    100,000 writes/s => 一年产生数据量：3.15e12 => 每年 6599 TB存储

##系统API
```
1. createShortUrl(String longUrl,Optional expireMills) 
    return shortUrl
2. getLongUrl(String shortUrl)
    if not expired -> redirect long url
    else not found
```

### 架构设计

![image-20211007095700430](/Users/kunliu/project/my/short-domain/readme.assets/image-20211007095700430.png)



### swagger

http://localhost:8080/swagger-ui.html#/

![image-20211007164104171](/Users/kunliu/project/my/short-domain/readme.assets/image-20211007164104171.png)

### 执行结果

![image-20211007164011891](/Users/kunliu/project/my/short-domain/readme.assets/image-20211007164011891.png)

![image-20211007140538538](/Users/kunliu/project/my/short-domain/readme.assets/image-20211007140538538.png

![image-20211007140635237](/Users/kunliu/project/my/short-domain/readme.assets/image-20211007140635237.png)

### jacoco测试结果

![image-20211007163737472](/Users/kunliu/project/my/short-domain/readme.assets/image-20211007163737472.png) 

### 性能测试

![image-20211007171035975](/Users/kunliu/project/my/short-domain/readme.assets/image-20211007171035975.png)

测试环境是运行在本地，如果要支持分布式情况会有所差异
