# DevOps Assignment

### Q1

分析系统日志: DevOps_interview_data_set.gz ,文件在Repo中。分析系统日志得到关键信息，用 Json 的格式 POST 上传至服务器 https://foo.com/bar )，key的名称在括号里：

1. 设备名称: (deviceName)
2. 错误的进程号码: (processId)
3. 进程/服务名称: (processName)
4. 错误的原因（描述）(description)
5. 发生的时间（小时级），例如 0100-0200，0300-0400, (timeWindow)
6. 在小时级别内发生的次数 (numberOfOccurrence)

***使用Python或sh完成***

---

- [作业代码](analyse_syslog.py)
- 思路概览
作为日志分析功能，除了日志的解析（切割）外，比较重要的是对每条日志进行频率统计。这里采用的是特征值法，代码中的LoggerTrait类对每条日志进行特征值采集，然后用一个简单map对该特征值进行存储（特征值为key），从而将特定时段内的数据统计上报。
- 扩展
作业中对处理流程和过程点进行了部分抽象。作业流程为：采集->解析->统计->上报，并且对一下实体做了简单抽象：
  + 采集器 LoggerFetcher，可以接受不同格式的文件，并预留了其它方式的日志输入
  + 解析器 LoggerContentParser，匹配日志中不同的字段，这里使用了正则，优点是匹配精确，兼容性好。缺点是计算消耗稍高
  + 特征值 LoggerTrait，目前是对日志主要字段进行hash计算，可以扩展为对message进行模板提前，这样的统计结构更具可读性
  + 处理器 LoggerProcesser，核心流程，采用callback的方式对report进行处理。
  + 统计上报 ReporterSubmitter，简单的post请求发送

### Q2

作为团队的DevOps专家，您正在设计团队某应用的DevOps方案，代码使用Github存放[此仓库](https://github.com/goxr3plus/Simplest-Spring-Boot-Hello-World)(也可以使用您自己的应用代码)，应用运行在AWS的ECS Fargate中：

- 开发人员的源代码更改合并到master分支之前，自动运行单元测试(如果使用本仓库的Java工程，则使用maven执行测试命令)；
- 通过AWS CDK或AWS SDK创建应用所需的基础架构和所需的服务，包括VPC、ECS、Task Define、Service，使用AWS CloudWatch管理日志；
- 应用程序部署是自动化的，可以使用Jenkins、AWS Code Deploy、Github Action；
- 监控CloudWatch中的应用日志，当出现ERROR关键字时通过AWS SNS发送邮件告警；


递交作业内容

1. 设计文档，包含框架设计图、执行与部署步骤以及说明；
2. Dockerfile；
3. CI/CD的代码或配置；
4. 创建Infrastructure的代码；
5. 其他用到的代码和配置文件；

**Bonus**

- Markdwon格式的文档

---

- [作业地址](https://github.com/lfbear/Simplest-Spring-Boot-Hello-World)
- 实现：AWS CDK + Github Action

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
