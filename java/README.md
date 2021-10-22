作者：冯炜  联系电话： 19907131722
# shortdns
  编译后取启动类jar包即可命令行启动
 ## 启动命令：java -jar -Xms2048M -Xmx2048M -Xmn512M -Xss256k -XX:PermSize=512M short-dns-demo-starter-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
 ## swagger地址： http://localhost:8080/shortdns/swagger-ui.html#/short45dns45controller
 <br>
 
# 功能描述
  ##  功能：实现长短域名存储、读取服务
    1. 功能要求: 
    短域名存储接口：接受长域名信息，返回短域名信息
    短域名读取接口：接受短域名信息，返回长域名信息。
    短域名长度最大为 8 个字符
    采用SpringBoot，集成Swagger API文档；

    2. 非功能要求:
    映射数据存储在JVM内存即可，防止内存溢出；

# 功能分析
    假定用户针对输入域名的长短域名转换、存储及长域名读取功能的业务场景。
    US1: 用户输入长域名信息且经过校验合法数据，提交服务端后转换成短域名信息返回。
        TASK1:接受长域名信息录入及输入合法性校验
        TASK2:使用短域名算法，进行长短域名的转换，获取短域名信息.
        TASK3:保存短域名信息，返回短域名信息
    US2: 用户输入短域名信息且经过校验合法数据，提交服务端后获取长域名信息
        TASK1:接受用户端短域名信息录入及输入合法性校验
        TASK2:通过短域名查询已保存的长短域名对应信息，并返回长域名。
# 场景假设
   1.  网关适配可对接rpc及其他方式 .同short-dns-apadter-gateway类似实现接口
   2.  基础设施层可以在不更改核心业务功能情况下适配redis等设施。同short-dns-apadter-memory类似实现接口
   3.  短域名算法可能在需要时实现不同算法。目前已实现了两种算法id自增与MD5


# 架构分析设计
    采用六边形架构（适配器端口方式），依据高内聚低耦合，职责单一的原则。内聚核心功能，外分而治之基础技术设施层。尽力保证后续需求变化中核心功能良好的易扩展性以及基础设施的易替代性。从而降低企业成本，提高研发效率。  
     1. 逻辑层次结构由内而外结构分为 业务功能层，应用服务层，基础设施适配层（分为南北设施层）。如图所示
![](https://user-images.githubusercontent.com/91041551/138206183-1a9e2152-dd3c-45aa-8ae0-dd81183d3c2c.png)

    2. 采用基于springboot框架实现，代码目录结构如图所示：

![ml](https://user-images.githubusercontent.com/91041551/138206272-006ca2d8-ad2e-4a01-b188-03530ad10dff.png)
    
    short-dns-demo-starter 主启动服务
    short-dns-adapter-rest 网关restful适配层
    short-dns-app 应用服务层
    short-dns-domian 业务功能服务层
    short-dns-adapter-memory 基础设施层-内存适配
   
    3. 数据请求路径是通过restful请求方式，通过网关适配器-->应用服务层-->业务功能层--->基础设施层
     
    4. 物理可独立部署，也可根据需要成为微服务方式部署。

# 单元测试覆盖率截图
![单元测试覆盖率图](https://user-images.githubusercontent.com/91041551/138206316-eb1d0c6e-34ad-4f75-be6c-514d5959de67.png)

# 性能测试
## 方案要点:
       1. 压力测试：在一定饱和度和状态下，逐步提升负载，直到系统出现问题。目的是找到系统瓶颈。
       2. 负载测试：在正常负载或峰值负荷下，测试系统的性能业务指标。服务器资源消耗是否满足指标要求。
       3. 稳定性测试：找出系统4/8小时 持续运行下的性能风险。（如宕机，内存溢出，内存泄露，磁盘空间不足等）
## 测试结果：
    
![cs1](https://user-images.githubusercontent.com/91041551/138206429-a1284821-7695-42fd-9755-65d0ca1bfc79.png)
![cs2](https://user-images.githubusercontent.com/91041551/138206430-682fc071-7b48-4b3d-8c09-0e0913212fdd.png)

***注：因工作忙时间仓促，仅实现了个基本功能，有误之处，请不吝赐教。希望能有面试机会。当面详解，谢谢。***
      


