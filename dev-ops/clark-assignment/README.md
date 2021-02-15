1. 关于Q1：
脚本只是检索日志种包含error关键字的信息，只是提取含有pid字段的log错误信息，从提供的log分析来看，com.apple.xpc.launchd[1] (com.apple.mdworker.bundles[12622])
种com.apple.xpc.launchd是个引导进程，类似于linux的systemd，是系统的第一个12622应该是进程mdworker的pid，但是我的脚本没有从这个角度去提取log，只提取一种类型的log，即com.apple.xpc.launchd[1] (com.apple.xpc.launchd.domain.pid.DesktopServicesHelper.12736)
此种类型的log多数由于找不多path，引起进程不断地重启，pid也在不断地发生变化。

脚本生成json格式的文件，通过curl post到https://foo.com/bar

./q1-push-error-info-type1.sh

2. 关于Q2：

2.1.	准备CDK环境

操作系统：CentOS7

安装npm

#yum install npm -y 或者node-v12.13.1-linux-x64

安装CDK

npm install -g aws-cdk

安装type-scripts

#npm install -g typescript

安装AWS CLI

curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"

unzip awscliv2.zip

aws/install

2.2. 配置AWS账号信息

2.2.1 创建IAM用户fargate-admin

ID: XXXXXXXXXXXXXXXXXXXXXXX

KEY: YYYYYYYYYYYYYYY

2.2.2 [aws-app@lrec-devops-admintools aws-fargate-app]$ aws configure

AWS Access Key ID [None]: XXXXXXXXXXXXX

AWS Secret Access Key [None]:  YYYYYYYYYYYYYYY

Default region name [None]: us-east-1

Default output format [None]:

2.3 执行

cd aws-fargate-alb-app

npm install @aws-cdk/core

npm install @aws-cdk/aws-ec2

npm install @aws-cdk/aws-ecs

npm install @aws-cdk/aws-ecs-patterns

2.4 执行部署

cdk deploy

2.5 cd aws-waf-policy

npm install @aws-cdk/core

npm install @aws-cdk/aws-wafv2

cdk deploy

默认创建一个IP Allow类型的WAF策略

new Waf(this, "devops-waf", {
  ipAllowList: ["10.0.0.2/32"],
});

3. 关于Q3

直接执行

q3-file-sort-answer.sh




#update dev branch by clark
