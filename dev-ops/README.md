### Q1

分析系统日志。DevOps_interview_data_set.gz

分析系统日志得到关键信息，用Json的格式POST上传至服务器(https://foo.com/bar)，key的名称在括号里

1. 设备名称: (deviceName)
2. 错误的进程号码: (processId)
3. 进程/服务名称: (processName)
3. 错误的原因（描述）(description)
4. 发生的时间（小时级），例如0100-0200，0300-0400, (timeWindow)
4. 在小时级别内发生的次数 (numberOfOccurrence)


分别使用

1. Bash 或其他脚本语言，假设在Mac环境下，进行操作
2. Powershell，假设在windows环境下，进行操作

### Q2

使用CDK，搭建一套Fargate + ALB/NLB 的应用（应用可以参考https://hub.docker.com/r/amazon/amazon-ecs-sample），在私有网络的VPC环境中（无公网访问）

注意:
- VPC的子网 no public assign attribute

Bonus：
WAF配置

使用Typescript 编写相关CDK脚本


### Q3

假设目前我们有一个日志文件是这样的格式, 可以假设字符串开始（无空格），然后一个空格，然后一个正整数。
使用任何方式，来对这个文件内容按照数字从大到小进行排序。如果是代码，请附加代码，如果是命令行，请附加命令行。


输入样例:

foo 1 
bar 4 
footer 3 
testline 5 
dafsd812342 9
