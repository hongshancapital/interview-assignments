# DevOps Assignment

### Q1

分析系统日志。DevOps_interview_data_set.gz

分析系统日志得到关键信息，用 Json 的格式 POST 上传至服务器 https://foo.com/bar )，key 的名称在括号里

1. 设备名称: (deviceName)
2. 错误的进程号码: (processId)
3. 进程/服务名称: (processName)
4. 错误的原因（描述）(description)
5. 发生的时间（小时级），例如 0100-0200，0300-0400, (timeWindow)
6. 在小时级别内发生的次数 (numberOfOccurrence)

分别使用

1. Bash 或其他脚本语言，假设在 Mac 环境下，进行操作
2. Powershell，假设在 windows 环境下，进行操作

#### 说明
1. 使用python语言处理日志，脚本文件'log_analysis.py'  
2. 需要将原日志中某些不规范的行清洗，比如  
```
        ASL Module "com.apple.cdscheduler" claims selected messages.
        Those messages may not appear in standard system log files or in the ASL database.
```
### Q2

使用 CDK，搭建一套 Fargate + ALB/NLB 的应用（应用可以参考 https://hub.docker.com/r/amazon/amazon-ecs-sample ），在私有网络的 VPC 环境中（无公网访问）

#### 注意

- VPC 的子网 no public assign attribute

#### Bonus

WAF 配置

使用 Typescript 编写相关 CDK 脚本

### Q3

假设目前我们有一个日志文件是这样的格式, 可以假设字符串开始（无空格），然后一个空格，然后一个正整数。
使用任何方式，来对这个文件内容按照数字从大到小进行排序。如果是代码，请附加代码，如果是命令行，请附加命令行。

输入样例:

```
foo 1
bar 4
footer 3
testline 5
dafsd812342 9
```
#### 说明  
1. 将输入样例输入到一个文件：3_log.txt
2. 命令及输出如下：
```
cat 3_log.txt |awk '{print $2" "$1}'|sort -r
9 dafsd812342
5 testline
4 bar
3 footer
1 foo
```

## 岗位描述

1.负责公司自动化运维平台的研发和建设.

2.负责公司 Tier2 技术支持.

3.基于业务场景、技术体系，不断完善问题处理机制和流程，持续帮助业务提升产品技术能力；

4.通过数据分析、平台建设, 提升基础架构稳定性，通过容量部署规划，降本提效;

5.协助并提供技术咨询，协同架构师/项目经理所提供的设计方案的落地、实施和交付工作；

## 岗位要求

1. 拥有 5 年以上工作经验，熟悉 Typescript、Java、Python，Shell 或 Powershell 编程语言至少两种，熟练的编程技巧;

2. 熟悉分布式、缓存、RPC、消息中间件、MySQL 存储, Postgres 存储至少一种;

3. 熟悉容器服务、K8S，熟悉云原生持续交付的体系架构;

4. 系统工程能力扎实过硬，深入了解系统（linux）及上下游链路服务（网络/io 等），具有很强技术敏感度和故障排查经验，并能进行技术方案的整合；

5. 有较强学习和沟通能力。有很强的动手能力和解决问题能力;

6. 熟悉云平台，有 AWS 云使用背景优先;

7. 熟悉平台运维和应用运维，能够建设运维规范和运维平台。

8. 熟悉 CI/CD 的搭建。

9. 熟悉日志收集、分析、报警的流程和工具的使用

10. 具备监控领域，包括 prometheus、zabbix 使用开发经验者优先考虑；

11. 为团队引入创新的技术和解决方案，以技术创新驱动业务，促进团队在这些领域的发展。

12. 有责任心，能在日常服务过程中很好实践客户第一，善于推动跨部门复杂项目的实施和较强的拿结果能力；
