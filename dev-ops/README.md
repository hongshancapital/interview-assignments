### Q1

分析系统日志。DevOps_interview_data_set.gz

分析系统日志得到关键信息，用Json的格式POST上传至服务器 https://foo.com/bar )，key的名称在括号里

1. 设备名称: (deviceName)
2. 错误的进程号码: (processId)
3. 进程/服务名称: (processName)
3. 错误的原因（描述）(description)
4. 发生的时间（小时级），例如0100-0200，0300-0400, (timeWindow)
4. 在小时级别内发生的次数 (numberOfOccurrence)

分别使用

1. Bash 或其他脚本语言，假设在Mac环境下，进行操作
2. Powershell，假设在windows环境下，进行操作

答：
```shell
$ git clone https://github.com/sequoiacapital/interview-assignments.git
$ cd dev-ops
$ gzip -d DevOps_interview_data_set.gz
$ python q1.py
```
目前只分析和post了日志，后面错误发生时间统计暂时还没写。但是觉得按题目要求这么做不是很科学，一般情况下是把log导入时序数据库，然后用sql去查。

Windows 下Powershell 很久不用了，不太了解。没有做。

### Q2

使用CDK，搭建一套Fargate + ALB/NLB 的应用（应用可以参考 https://hub.docker.com/r/amazon/amazon-ecs-sample ），在私有网络的VPC环境中（无公网访问）

#### 注意
- VPC的子网 no public assign attribute

#### Bonus
WAF配置

使用Typescript 编写相关CDK脚本

答：
先要完成 https://cdkworkshop.com/15-prerequisites.html 准备工作
```shell
$ tar -zxf q2.tar.gz
$ cd aws-cdk-fargate-test
$ npm install -g aws-cdk
$ npm install
$ npm run build
$ cdk deploy  // Deploys the CloudFormation template

# Afterwards
$ cdk destroy
```

主要参考 https://github.com/aws-samples/aws-cdk-examples/tree/master/typescript/ecs/fargate-application-load-balanced-service/
WAF的部分没找到好的example。暂时还没写。有一个疑惑，WAF一般不是用来在外网最外层入口做限制，这里起私网的ALB还需要使用WAF的什么功能？限速么？

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

答：
```shell
$ cat tt
foo 1
bar 4
footer 3
testline 5
dafsd812342 9
```

方法一：
```shell
$ cat tt | sort -k2 -rn
dafsd812342 9
testline 5
bar 4
footer 3
foo 1
```

方法二：
```shell
$ cat tt | awk 'BEGIN {PROCINFO["sorted_in"]="@ind_num_desc"} {a[$2]=$1} END {for (i in a) print a[i], i}'
dafsd812342 9
testline 5
bar 4
footer 3
foo 1
```
