# interview-assignments
The monorepo for interview take home assignments.
撰写两个 API 接口:
- 短域名存储接口：接受长域名信息，返回短域名信息
接口：/saveShortUrl
- 短域名读取接口：接受短域名信息，返回长域名信息。
接口：/getLongUrl/{shortUrl}

限制：
- 短域名长度最大为 8 个字符
通过方法ShortNetAddressUtil.java生成8位的短域名
- 采用SpringBoot，集成Swagger API文档；
Swagger2集成
- JUnit编写单元测试, 使用Jacoco生成测试报告(测试报告提交截图即刻)；
Jacoco报告截图如下“Jacoco单元测试覆盖率截图“
- 映射数据存储在JVM内存即可，防止内存溢出；
内存存入数据大于10万条数据就会删除1000条
