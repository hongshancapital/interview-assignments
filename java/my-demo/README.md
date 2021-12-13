# 面试作业项目：[my-demo](https://github.com/173951428/interview-assignments/tree/master/java/my-demo)
# 主要功能
撰写两个 API 接口:
* 短域名存储接口：接受长域名信息，返回短域名信息
* 短域名读取接口：接受短域名信息，返回长域名信息。
# 设计方案
## 1. 长域名转短域名并存储
使用redis，使用固定key和redis自增函数获取累计自增数值，加上固定前缀作为key，然后把将长域名值作为value，存储至redis，并且相同的长链接对应redis同一个key值，所以能保证同一个长链接对应同一个短链接

## 2. 短域名转长域名并存储
每次生成短域名的时候，用固定常量key值+redis自增值 作为key值，把原来的长域名作为value值，同时存储到redis里面，每次短域名转长域名时，解析短域名时,可以根据 短域名解析到key，从redis中查找

## 3.短域名解析，并且跳转
使用过滤器filter，拦截所有请求，根据命名规则过滤短域名url，如果是检测到请求是短域名，就做对应长域名的转换，最终java后台实现跳转。

# 单元测试：
![d6a174bc31cad2f582d91ba6e928cae](https://user-images.githubusercontent.com/33243007/145809671-59af90b6-e907-49cb-bcf8-2c59584ad8a9.png)


# 性能测试:
性能测试采用jmter，每秒1000个并发请求，测试结果如下：
最小响应时间 24毫秒 ，最大响应时间35毫秒，错误率 0% ，吞吐量 937.7/秒 ，性能表现十分优异
![image](https://user-images.githubusercontent.com/33243007/145809267-53853a20-7872-4d04-aaa8-812eeccbf1b6.png)

# swagger 地址：
为了节约面试官时间，已经线上docker部署了swagger环境，公网可以直接查看，地址：http://47.103.32.78:8080/demo/api/doc.html
