## Overview
This assignment need you have an [aws account](https://aws.amazon.com/), [python3.7](https://www.python.org/downloads/) with pip3, [GNU make](https://www.gnu.org/software/make/) and [aws credentials](https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-files.html) in your local host (Mine is MacOS Catalina, I think linux or mac should be ok to run this project) default profile.
#### Q1:
----------
After you have GNU make installed, you can run `make ci-q1` directly to send result `dev-ops/Ans-YiWeng/stage/Q1/file.json` to https://foo.com/bar.
##### Snippet output for Q1:
```
[
    {
      "deviceName":"BBAOMACBOOKAIR2",
      "ProcessId":"[113]",
      "processName":"syslogd",
      "timeWindow":"0000-100",
      "description":"+ASL+Sender+Statistics,+ASL+Sender+Statistics,+ASL+Sender+Statistics,+Configuration+Notice:+ASL+Module+\"com.apple.cdscheduler\"...",
      "numberOfOccurrence":22
    },
    {
      "deviceName":"BBAOMACBOOKAIR2",
      "ProcessId":"[1][12622]",
      "processName":"com.apple.xpc.launchd+(com.apple.mdworker.bundles)",
      "timeWindow":"0000-100",
      "description":"+Could+not+find+uid+associated+with+service:+0:+Undefined+error:+0+501,+Service+exited+with+abnormal+code:+78",
      "numberOfOccurrence":2
    },
    ...
```
#### Q2
----------
Installation
-----
- `make deps` -- use to the requirements.
- You should update `repo_id` with your aws ecr repo id and `region` in Makefile.

Usage
-----
- Build your own aws env with the procedures like:
```
  make create-infra-resources
  make package q2_version=1.0.0 (optional) 
  make publish q2_version=1.0.0 (optional) - need a basic image in ecr repo for placeholder task
  make create-infra-service
```
- Delete whole env after testing:
```
  make delete-infra-service
  make delete-images
  make delete-infra-resources
```
Application
-----
- `make ci-q2 <q2_version> <region> <repo_id>`
- `make package <q2_version>` and `make publish <q2_version>` will build current app code from `dev-ops/Ans-YiWeng/Q2/Simplest-Spring-Boot-Hello-World-master` in a docker image and publish to your ecr repo.
- `Q2/deployments/helloworldwebapp_task_definition.json` defines the deployment of task definition consumed by deploy-ecs-service.
- `make deploy-ecs-service <q2_version> <region> <repo_id>` will deploy direct deployment to ecs fargate env

Testing
-----
- Unit test will run automatically when package the code defined in Dockerfile `RUN mvn clean test package`. 
- `make integration-test <q2_version>` will test your local image which contains your testing version code.

### Q2

作为开发团队的成员，您的任务是确保：

- 应用程序(一个简单的Java应用程序，返回Hello World)具有一定的弹性，并且单节点故障不会影响最终用户，您可以使用[此仓库](https://github.com/goxr3plus/Simplest-Spring-Boot-Hello-World)中提供的源代码，也可以创建自己的应用程序。
  答：应用程序使用ecs fargate和应用负载均衡，默认是双AZ预期2负载。进行部署的时候采用200%增量部署，如果成功自动从服务中减去旧有容器。
- 可以伸缩应用程序，最好自动伸缩以处理增加的负载。
  答：在ecs服务内使用自动伸缩，使用cloudwatch metrics警告监控cpu使用率，默认达到60%进行扩容，当使用率低于54%进行缩减。
- 基础架构和所需的服务以及应用程序部署是自动化的，可以通过在终端中单击按钮或命令来触发。
  答：使用GNU make唤起sceptre 1.3.1来创建独立或者全套基础架构。
- 在将应用程序的源代码更改合并到master分支之前，可以对其进行自动测试。
  答：应用程序会在创建阶段执行Unit test, `integration-test` 可以在本地主机进行集成测试，随机使用（3000-65000）其中一个未被占用的端口。
- 可以启动该应用程序的特定版本以进行故障排除，测试，展示等。
  答：`integration-test <q2_version>`在主机或者容器中测试特定版本，`make deploy-local <app> <q2_version> <container_name>`和`make delete-local <container_name>`在本地创建和摧毁特定版本。
  还可以拷贝`aws-fargate/config/helloworld`下的文件到一个新的目录，比如test，去创建新环境进行测试。
- 需要监控环境、应用的日志，自动执行事件管理和警报
  答：应用日志记录在cloudwatch日志下，cloudwatch进行实时cpu使用率的监控并警告，达到触发条件开始伸缩。

###缺点：
- 目前没有针对其他设备如负载均衡和vpc流量的监控
- 进行应用部署的时候如果容器启动失败，需要登录到控制台去查看错误，目前没有针对部署容器服务启动时发生错误的日志返回。