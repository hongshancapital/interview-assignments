# Q1 分析系统日志并且POST上传


##解析：

日志中有多处报错，当需要分析日志时，必然有业务场景受影响，需要结合受影响的业务场景选择对应的报错进而具体排查。  比如错误“ Could not find uid 501”和“AMPDeviceDiscoveryAgent“等。  

作业以应用相关的AMPDeviceDiscoveryAgent的报错为例，分析过程如下：

---

1. 设备名称: (deviceName)：BBAOMACBOOKAIR2  
2. 错误的进程号码: (processId)： 976
3. 进程/服务名称: (processName)： AMPDeviceDiscoveryAgent
4. 错误的原因（描述）(description)：
   ` Mux ID not found in mapping dictionary`
5. 发生的时间（小时级），例如 0100-0200，0300-0400, (timeWindow): 0750-1756
```
# cat log.txt |grep  "Mux ID not found in mapping dictionary" | head -1
May 13 07:50:36 BBAOMACBOOKAIR2 AMPDeviceDiscoveryAgent[976]: tid:8813 - Mux ID not found in mapping dictionary

# cat log.txt |grep  "Mux ID not found in mapping dictionary" | tail -1
May 13 17:56:09 BBAOMACBOOKAIR2 AMPDeviceDiscoveryAgent[976]: tid:8827 - Mux ID not found in mapping dictionary
```
6. 在小时级别内发生的次数 (numberOfOccurrence)


```
cat log.txt  | grep "Mux ID not found in mapping dictionary" |awk -F : '{ print $1 }' | sort | uniq -c
```
结果（次数 月 日 时）：

      8 May 13 07
     40 May 13 08
     18 May 13 09
     14 May 13 10
      2 May 13 11
     41 May 13 12
     21 May 13 13
     33 May 13 14
     17 May 13 16
     34 May 13 17

---

##上传json的Shell代码如下：
```
curl -i -X POST -H "Content-type:'application/json" -d '{"deviceName":"：BBAOMACBOOKAIR2","processId":"976","processName":"AMPDeviceDiscoveryAgent","description":"Mux ID not found in mapping dictionary","timeWindow":"0750-1756","numberOfOccurrence":{"0700":8,"0800":40,"0900":18,"1000":14,"1100":2,"1200":41,"1300":21,"1400":33,"1600":17,"1700":34}}'  https://foo.com/bar
```

# Q2 Devops - ECS Fargate + CICD设计文档
注：包含框架设计图、执行与部署步骤以及说明；

## 架构设计思路
本方案采用GithubAction实现CICD流程,采用fargate的蓝绿部署方式实现测试环境与生产环境的先后部署。
Github Action实现在git代码指定分支有commit时触发build且推送新的ECR的镜像，进而更新ECS task defination中docker image id，然后将使用新image id的新task definatrion更新到fargate service中实现自动测试。
方案采用cloudwatch监控并且发送通知到SNS。



![Fargate自动蓝绿部署](/fargate.jpg "Fargate自动蓝绿部署")       


## 执行步骤 

注：
目前使用aws cli命令行实现shell形式的自动化部署，时间允许可使用cdk或者sdk改写。CDK底层调用cloudformation实现自动化部署，从自动化的角度，跟AWS cli的shell脚本是一致的。由于当前没有AWS环境，ecs端还没实际测试，方案思路以及具体CICD实现需要的代码如下：

1. 创建AWS Infrastructure的代码-使用AWS CLI 创建VPC/ECR/ECS/Fargate Service等组件：
ECR REPOSITORY ："interview"
ECS task defination： "hellospring.json"
ECS cluster ："interview"
ECS service ："fargate-spring"
VPC:interviewvpc
VPC Subnet ："subnet-1"
SecurityGroup ："sg-1"   
代码如下：
    ```
    $vpcid=`aws ec2 create-vpc --cidr-block 10.0.0.0/16` |jq .vpcid  #jq提取vpcid后赋值给下面的命令创建subnet
    aws ec2 create-subnet    --vpc-id $vpcid    --cidr-block 10.0.0.0/24 
    aws ecr create-repository --repository-name interview --region us-west-2 
    aws ecs register-task-definition --generate-cli-skeleton
    aws ecs register-task-definition --region  us-west-2 --cli-input-json  file://$HOME/hellospring.json
    aws ecs create-cluster --region us-west-2 --cluster-name interview
    aws ecs create-service --region us-west-2 --service-name fargate-spring --deployment-controller CODE_DEPLOY --task-definition hellospring:1 --desired-count 1 --launch-type "FARGATE" --network-configuration "awsvpcConfiguration={subnets=[subnet-1],securityGroups=[sg-1]}" 
    ```
&ensp;&ensp;其中，task defination 的json代码如下（包含cloudwatch管理日志部分）：
```
{
  "requiresCompatibilities": [
    "FARGATE"
  ],
  "containerDefinitions": [
    {
      "name": "hellpspring",
      "image": "hellospring:latest",
      "memory": 256,
      "cpu": 256,
      "essential": true,
      "portMappings": [
        {
          "containerPort": 9092,
          "protocol": "tcp"
        }
      ],
      "logConfiguration":{
            "logDriver":"awslogs",
            "options":{
               "awslogs-group":"interview-hellpspring-ecs",
               "awslogs-region":"us-west-2",
               "awslogs-stream-prefix":"ecs"
            }
      }
    }
  ],
  "volumes": [],
  "networkMode": "awsvpc",
  "placementConstraints": [],
  "family": "spring",
  "memory": "512",
  "cpu": "256"
}
```


&ensp;&ensp; 以上创建AWS资源部分，也可使用CDK实现。其实也可以通过控制台提供的接口实现一键自动部署：
&ensp;&ensp; https://us-east-2.console.aws.amazon.com/ecs/home?region=us-east-2#/firstRun


2. 准备Dockerfile以及编译的jar包放在github中，git action的workflow会完成自动docker build等操作
- Dockerfile 
  ```
  FROM maven:3.8-jdk-11 as builder
  RUN mkdir -p /tmp/build_jar
  COPY . /tmp/build_jar
  WORKDIR /tmp/build_jar
  RUN mvn -B package --file pom.xml
  RUN ls -l /tmp/build_jar
  
  FROM openjdk:8-jdk-alpine
  CMD javac src/main/java/example/smallest/controllers/WelcomeController.java 
  ARG JAR_FILE=*.jar
  COPY ${JAR_FILE} hellospring.jar
  ARG SERVER_PORT=9092
  EXPOSE ARG SERVER_PORT=9092
  ENTRYPOINT ["java","-jar","/hellospring.jar"]
  ```

3. 配置Github Action
- 为IAM 用户设置aws secret以便github跟aws鉴权:
  AWS_ACCESS_KEY_ID：
  AWS_SECRET_ACCESS_KEY：


- 设置Action的workflow: .github/workflows，以代码commit为触发条件。 
  这个workflow action会根据github repo中的文件build一个新的container image并且push到ECR中，
  然后利用新的image部署一个新的task defination到ECS，
  需要调用aws官方支持的agithub action代码：https://github.com/aws-actions：
    https://github.com/aws-actions/configure-aws-credentials 做AWS认证
    https://github.com/aws-actions/amazon-ecr-login :将github中的dockerfile执行docker build 然后docker push to ECR
    https://github.com/aws-actions/amazon-ecs-render-task-definition 更新task defination的image以使用更新后的docker 镜像
    https://github.com/aws-actions/amazon-ecs-deploy-task-definition 将更新后的task defination部署更新到ECS service中


- 步骤：
  点击Github Action -> new workflow -> 搜索选择“Deploy to Amazon ECS“

  workflow 代码：
  ```
  name: Deploy to Amazon ECS

  on:
    push:
      branches:
        - master

  env:
    AWS_REGION: us-west-2                  # set this to your preferred AWS region, e.g. us-west-1
    ECR_REPOSITORY: interview           # set this to your Amazon ECR repository name
    ECS_SERVICE: fargate-spring                 # set this to your Amazon ECS service name
    ECS_CLUSTER: interview                 # set this to your Amazon ECS cluster name
    ECS_TASK_DEFINITION: hellospring.json # set this to the path to your Amazon ECS task definition
                                                # file, e.g. .aws/task-definition.json
    CONTAINER_NAME: hellospring           # set this to the name of the container in the
                                                # containerDefinitions section of your task definition
  jobs:
    deploy:
      name: Deploy
      runs-on: ubuntu-latest
      environment: production

      steps:
      - name: Checkout
        uses: actions/checkout@v2

      - name: Configure AWS credentials
        uses: aws-actions/configure-aws-credentials@v1
        with:
          aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
          aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
          aws-region: ${{ env.AWS_REGION }}

      - name: Login to Amazon ECR
        id: login-ecr
        uses: aws-actions/amazon-ecr-login@v1

      - name: Build, tag, and push image to Amazon ECR
        id: build-image
        env:
          ECR_REGISTRY: ${{ steps.login-ecr.outputs.registry }}
          IMAGE_TAG: ${{ github.sha }}
        run: |
          # Build a docker container and
          # push it to ECR so that it can
          # be deployed to ECS.
          docker build -f Dockerfile -t $ECR_REGISTRY/$ECR_REPOSITORY:$IMAGE_TAG .
          docker push $ECR_REGISTRY/$ECR_REPOSITORY:$IMAGE_TAG
          echo "::set-output name=image::$ECR_REGISTRY/$ECR_REPOSITORY:$IMAGE_TAG"
      - name: Fill in the new image ID in the Amazon ECS task definition
        id: task-def
        uses: aws-actions/amazon-ecs-render-task-definition@v1
        with:
          task-definition: ${{ env.ECS_TASK_DEFINITION }}
          container-name: ${{ env.CONTAINER_NAME }}
          image: ${{ steps.build-image.outputs.image }}

      - name: Deploy Amazon ECS task definition
        uses: aws-actions/amazon-ecs-deploy-task-definition@v1
        with:
          task-definition: ${{ steps.task-def.outputs.task-definition }}
          service: ${{ env.ECS_SERVICE }}
          cluster: ${{ env.ECS_CLUSTER }}
          wait-for-service-stability: true
  ```


4. CloudWatch监控配置
- 配置SNS的cli命令：
   ```
   aws sns create-topic --name interview-topic
   aws sns subscribe --topic-arn arn:aws:sns:us-west-2:111122223333:interview-topic --protocol email --notification-endpoint my-email-address
   ```
- 创建cloud watch alarm
  参考：https://docs.aws.amazon.com/zh_cn/AmazonCloudWatch/latest/monitoring/Create_Anomaly_Detection_Alarm.html



