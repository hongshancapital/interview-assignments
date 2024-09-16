一、工程介绍
1、项目启动类  com.cn.scdt.DomainMappingApplication
2、swagger/knife地址  http://localhost:8080/swagger-ui.html   
                     http://localhost:8080/doc.html#/home
3、接口类
     com.cn.scdt.controller.DomainMappingController
4、jacoco测试报告    
   单元测试覆盖率截图.png   
5、测试类：
  com.cn.scdt.DomainMappingControllerTest     
          
二、实现思路
1、短域名存储接口实现思路
接口路由：/getShortUrl
参数：String longUrl
返回： ResponseDto<String>  其中json中data为短域名
思路：利用工具类生成8位随机字符串作为key,常域名作为value，以(key,value)形式存储在map中,
     长域名md5加密后作为key1,随机8位字符串作为key, 以(key1,key)形式存储在map中
实现步骤：
1)长域名md5加密
2)判断map中是否存在短域名
3)若存在直接返回
    不存在，利用工具类生成8位随机字符串作为key作为短域名,常域名作为value，以(key,value)形式存储在map中，
    长域名md5加密后作为key1,随机8位字符串作为key, 以(key1,key)形式存储在map中，然后返回短域名

2、短域名读取接口实现思路
   map通过短域名直接返回长域名
   
3、改进优化建议
   1)、采用分布式结构，该项目部署多个节点，将映射关系存储在redis中
   2)、并将缓存的key设置过期时间防止redis占用空间无限大
   

三、采用框架
   springboot2.3.2 + knife4j2.0.1 + swagger2.9.2 + junit + jacoco + hutool5.3.3(md5加密) + logback日志
   
四、性能测试
1、假设jvm大小为100M
2、设置 DomainMappingApplication 启动vm参数 -Xmx100M -Xms100M
3、发送10000000个不同长域名的转换请求，当存储27272个longurl会遇到内存溢出，合计54544个键值对
4、修改策略，当存放15000个时，map = null，并手动调用System.gc()，生产环境切勿使用;可以跑到38000多个