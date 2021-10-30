作者：冯炜  联系电话： 19907131722
# short-DN-service
  编译后取启动类jar包即可命令行启动
 ## 启动命令：java -jar -Xms2048M -Xmx2048M -Xmn1024M -Xss512k 

 ## -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m XX:MaxDirectMemorySize=1024m short-dn-service-1.0-SNAPSHOT.jar

 ## swagger地址： http://localhost:8080/swagger-ui.html#/short45dn45controller
# 功能描述
  ##  功能：实现长短域名存储、读取服务
    功能要求: 
    短域名存储接口：接受长域名信息，返回短域名信息
    短域名读取接口：接受短域名信息，返回长域名信息。
    短域名长度最大为 8 个字符
    采用SpringBoot，集成Swagger API文档；

# 功能分析
    假定用户针对输入域名的长短域名转换、存储及长域名读取功能的业务场景。
    US1: 用户输入长域名信息且经过校验合法数据，提交服务端后转换成短域名信息返回。
        TASK1:接受长域名信息录入及输入合法性校验
        TASK2:使用短域名算法，进行长短域名的转换，获取短域名信息.
        TASK3:保存短域名信息，返回短域名信息
    US2: 用户输入短域名信息且经过校验合法数据，提交服务端后获取长域名信息
        TASK1:接受用户端短域名信息录入及输入合法性校验
        TASK2:通过短域名查询已保存的长短域名对应信息，并返回长域名。
# 单元覆盖率
![覆盖率](F:\Idea_workspace\HS\short-dn-service\覆盖率.JPG)

# 性能测试
## 方案要点:
       1. 压力测试：在一定饱和度和状态下，逐步提升负载，直到系统出现问题。目的是找到系统瓶颈。但是由于本人资源有限，只打包跑在一台vm上，配置为4C8G
       2. 负载测试：在正常负载或峰值负荷下，测试系统的性能业务指标。服务器资源消耗是否满足指标要求。
       3. 稳定性测试：找出系统4/8小时 持续运行下的性能风险。（如宕机，内存溢出，内存泄露，磁盘空间不足等）
## 测试结果：

![压测1](F:\Idea_workspace\HS\short-dn-service\压测1.JPG)![压测](F:\Idea_workspace\HS\short-dn-service\压测.jpg)

​      

