我们希望你递交的作业内容包括：

1. 系统设计文档   https://github.com/devil0118/short_url/blob/1612e87e7a2e408f43306c0a56679fbebd437352/readme/%E7%9F%AD%E5%9F%9F%E5%90%8D%E6%9C%8D%E5%8A%A1%E7%B3%BB%E7%BB%9F%E8%AE%BE%E8%AE%A1%E6%96%87%E6%A1%A3.docx
2. 源代码  
https://github.com/devil0118/short_url/tree/1612e87e7a2e408f43306c0a56679fbebd437352
3. 单元测试代码以及单元测试覆盖率  
https://github.com/devil0118/short_url/tree/1612e87e7a2e408f43306c0a56679fbebd437352/readme/jacoco
4. API 集成测试案例以及测试结果  
见测试代码
5. 简单的框架设计图，以及所有做的假设  
见系统设计文档
6. 涉及的 SQL 或者 NoSQL 的 Schema，注意标注出 Primary key 和 Index 如果有。  
https://github.com/devil0118/short_url/blob/18fc3731fc067839b5ed0ac4d242924081ba6e55/readme/short_url_init.sql
7. CI/CD、日志、监控和报警的设计  
见系统设计文档，代码及https://github.com/devil0118/short_url/blob/18fc3731fc067839b5ed0ac4d242924081ba6e55/readme/script/monitor.py
8. （Bonus），使用 Google/AWS/Aliyun 制作相关的 CI/CD pipeline， 并且提供 IAM 可以 preview  
采用Jenkins进行自动打包部署  测试地址：http://182.92.185.140/short_url/   测试账号及密码：test:test123

### Question 2

**不使用任何框架的前提下，使用 Java 编写 Http 服务器，考虑服务负载、并发访问、高可用，实现 Http 协议 Authorization 验证方式，支持 html、image、xml、file 的资源访问。**

我们希望你递交的作业内容包括：

1. 系统设计文档  
https://github.com/devil0118/http_server/blob/fb564b6fc1dc63ff1a9436d02ee219eb38e81c86/readme/HTTP%E6%9C%8D%E5%8A%A1%E5%99%A8%E7%B3%BB%E7%BB%9F%E8%AE%BE%E8%AE%A1%E6%96%87%E6%A1%A3.docx
2. 源代码  
https://github.com/devil0118/http_server/tree/fb564b6fc1dc63ff1a9436d02ee219eb38e81c86
3. 单元测试代码以及单元测试覆盖率  
https://github.com/devil0118/http_server/tree/fb564b6fc1dc63ff1a9436d02ee219eb38e81c86/readme/jacoco
4. API 集成测试案例以及测试结果  
见测试代码
5. 简单的框架设计图，以及所有做的假设  
见系统设计文档
6. 性能指标即测试过程  
https://github.com/devil0118/http_server/blob/fb564b6fc1dc63ff1a9436d02ee219eb38e81c86/readme/%E6%B5%8B%E8%AF%95%E6%8A%A5%E5%91%8A.docx
7. CI/CD、日志、监控和报警的设计  
见系统设计文档，代码及https://github.com/devil0118/http_server/blob/fb564b6fc1dc63ff1a9436d02ee219eb38e81c86/readme/script/monitor.py
8. （Bonus），使用 Google/AWS/Aliyun 制作相关的 CI/CD pipeline， 并且提供 IAM 可以 preview  
采用Jenkins进行自动打包部署 测试地址：http://182.92.185.140/http_server/  测试账号及密码：test:test
可访问URL示例http://182.92.185.140/http_server/file/pom.xml，http://182.92.185.140/http_server/errors/404.html
