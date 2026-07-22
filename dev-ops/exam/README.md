## 备注
代码编辑工具：idea

markdown编辑工具：vscode

流程图编辑工具：ProcessOn

工作环境：Mac

python版本：3.7.4

---

## 题目地址
https://github.com/scdt-china/interview-assignments/tree/master/dev-ops

---

## Q1
分析系统日志: DevOps_interview_data_set.gz ,文件在Repo中。分析系统日志得到关键信息，用 Json 的格式 POST 上传至服务器 https://foo.com/bar )，key的名称在括号里：
1. 设备名称: (deviceName)
2. 错误的进程号码: (processId)
3. 进程/服务名称: (processName)
4. 错误的原因（描述）(description)
5. 发生的时间（小时级），例如 0100-0200，0300-0400 (timeWindow)
6. 在小时级别内发生的次数 (numberOfOccurrence)

使用Python或sh完成

### 目录结构（exam/Q1）
```
Q1  # 问题一相关文件
├── DevOps_interview_data_set   # 测试数据
├── README.md                   # 备注
└── __init__.py                 # python代码
```

---

## Q2
作为团队的DevOps专家，您正在设计团队某应用的DevOps方案，代码使用Github存放此仓库(也可以使用您自己的应用代码)，应用运行在AWS的ECS Fargate中：
* 开发人员的源代码更改合并到master分支之前，自动运行单元测试(如果使用本仓库的Java工程，则使用maven执行测试命令)；
* 通过AWS CDK或AWS SDK创建应用所需的基础架构和所需的服务，包括VPC、ECS、Task Define、Service，使用AWS CloudWatch管理日志；
* 应用程序部署是自动化的，可以使用Jenkins、AWS Code Deploy、Github Action；
* 监控CloudWatch中的应用日志，当出现ERROR关键字时通过AWS SNS发送邮件告警；

递交作业内容
1. 设计文档，包含框架设计图、执行与部署步骤以及说明；
2. Dockerfile；
3. CI/CD的代码或配置；
4. 创建Infrastructure的代码；
5. 其他用到的代码和配置文件；

Bonus
* Markdown格式的文档

### 目录结构（exam/Q2、exam/Q2-cloud-native）
```
├── Q2  # 问题二相关文件
│   ├── infrastructure          # 基础设施相关文件
│   │   └── main.yml
│   ├── static                  # 设计文档中引用到的内容
│   │   ├── Dockerfile
│   │   ├── jenkinsfile
│   │   ├── 监控告警-流程图.png
│   │   └── 自动部署-流程图.png
│   └── 设计文档.md
├── Q2-cloud-native # 问题二补充回答（k8s、prometheus）
│   ├── README.md               # 备注
│   ├── static                  # 设计文档中引用到的内容
│   │   ├── 监控告警-流程图.png
│   │   └── 自动部署-流程图.png
│   └── 设计文档.md
```