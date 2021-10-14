# 说明

使用 AWSCloudFormation 创建 ECS Fargate cluster

## ecs-cluster.yaml

- 创建 VPC 等网络资源
- 创建 ECS cluste r和相关 Role

## ecs-service.yaml

- 创建 Fargate Service 和 TTaskDefinition
- 创建 LogGroup
- 创建 LoadBalancer
- 创建 SecurityGroup

## aws cli

```bash
$ aws cloudformation create-stack \
--stack-name ecs-cluster \
--template-body file:///home/jason/MyProjects/scdt/dev-ops-Q2/ecs-cluster.yaml \
--capabilities CAPABILITY_NAMED_IAM
```

```bash
aws cloudformation create-stack \
--stack-name ecs-service \
--template-body file:///home/jason/MyProjects/scdt/dev-ops-Q2/ecs-service.yaml
```