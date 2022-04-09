# 短域名生成服务

## 1、需求
#### 实现短域名服务（细节可以百度/谷歌）

撰写两个 API 接口:
- 短域名存储接口：接受长域名信息，返回短域名信息
- 短域名读取接口：接受短域名信息，返回长域名信息。

限制：
- 短域名长度最大为 8 个字符
- 采用SpringBoot，集成Swagger API文档；
- JUnit编写单元测试, 使用Jacoco生成测试报告(测试报告提交截图)；
- 映射数据存储在JVM内存即可，防止内存溢出；

**递交作业内容** 
- 源代码(按照生产级的要求编写整洁的代码，使用gitignore过滤掉非必要的提交文件，如class文件)
- Jacoco单元测试覆盖率截图(行覆盖率和分支覆盖率85%+)
- 文档：完整的设计思路、架构设计图以及所做的假设(Markdown格式)

**加分项** 
- 系统性能测试方案以及测试结果


## 2、设计思路
![系统设计图](https://github.com/renyiran/interview-assignments/blob/renyiran-java/doc/%E7%B3%BB%E7%BB%9F%E6%B5%81%E7%A8%8B%E8%AE%BE%E8%AE%A1.jpg)

- 采用murmurhash生成32位hashcode 然后将hashcode转换为62进制生成短链接
- 采用布隆过滤器记录已经存在的短链，布隆过滤器长度1000w，冲突率0.01%
- 采用loadingCache保存已有短链长链对应关系，最多保存1000w条数据，最多保存1天，支持10个线程并发
- murmurhash、布隆过滤器、loadingCache均采用google的guava包实现
- 简化了接口返回信息，直接返回了链接地址，异常信息也直接返回了代表异常的字符串

## 3、系统流程

- 1.接收长链接，采用murmurhash生成32位hashcode，将hashcode转换为6位62进制短链接
- 2.依据布隆过滤器判断短链是否已存在
- 3.若短链不存在，则保存到loadingCache，返回短链
- 4.若短链已存在，则判断已保存长链接是否与需要保存的一致，一致则直接返回短链。
- 5.若不一致则为生成的短链冲突，将长链接拼接自定义字符串后重复步骤1
- 6.短链冲突最多重新生成3次，否则返回生成失败

## 4、jvm参数

**由于布隆过滤器初始化以及loadingCache占用空间，所以需要分配一定堆内存**

````
-Xmx1024m 
-Xms1024m 
````

## 5、swagger文档
![swagger页面](https://github.com/renyiran/interview-assignments/blob/renyiran-java/doc/swagger.png)

**swagger地址:http://localhost:8765/swagger-ui.html**


## 6、单元测试
![单元测试](https://github.com/renyiran/interview-assignments/blob/renyiran-java/doc/%E6%B5%8B%E8%AF%95%E4%BB%A3%E7%A0%81%E8%A6%86%E7%9B%96%E7%8E%87.jpg)

## 7、性能测试

### jmeter配置
![请求配置](https://github.com/renyiran/interview-assignments/blob/renyiran-java/doc/%E5%8E%8B%E6%B5%8B%E8%AF%B7%E6%B1%82%E9%85%8D%E7%BD%AE.jpg)
![并发配置](https://github.com/renyiran/interview-assignments/blob/renyiran-java/doc/%E5%8E%8B%E6%B5%8B%E7%BA%BF%E7%A8%8B%E7%BB%84%E9%85%8D%E7%BD%AE.png)

### 性能测试结果
![压测1分钟](https://github.com/renyiran/interview-assignments/blob/renyiran-java/doc/%E5%8E%8B%E6%B5%8B1%E5%88%86%E9%92%9F.png)
![压测2分钟](https://github.com/renyiran/interview-assignments/blob/renyiran-java/doc/%E5%8E%8B%E6%B5%8B2%E5%88%86%E9%92%9F.png)
![压测4分钟](https://github.com/renyiran/interview-assignments/blob/renyiran-java/doc/%E5%8E%8B%E6%B5%8B4%E5%88%86%E9%92%9F.png)

压测4分钟后 qps能达到1.25w/s,平均响应时间为15ms

## 8、联系方式

emai:373133745@qq.com
