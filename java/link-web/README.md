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
- Jacoco单元测试覆盖率、Swagger接口测试、单元测试结果等截图  @see /link-web/src/main/java/com/link/test/screenshot
- 设计思路：批量ID生成器，从ID池子获取ID生成62进制字符串（长度8位数），作为短地址一部分，并用此字符串作为Redis的Key来缓存长地址路径。@see com.link.service.LinkService

**我的邮箱**
- 466142226@qq.com 