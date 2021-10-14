# CI/CD

## 说明

- 本流水线用于构建 Simplest-Spring-Boot-Hello-World 项目，工具是 Jenkins
- Jenkins agent 在 Kubernetes 集群中动态创建，完成测试、打包、构建镜像并上传至镜像库
- 在 agent pod 中，jnlp 负责与 Jenkins master 通信，maven 负责构建 java 应用，docker 负责构建镜像并上传，awscli 负责将新镜像部署到 AWS ECS 集群
- Docker 采用客户端和服务端分离的方式来部署，服务端部署在同 namespace 下，服务名是 docker-dind-rootless.devops.svc
- 为了充分利用 maven 的缓存，所有 agent pod 的 maven 容器都挂载同一个存储卷，要求存储卷支持并发读写，可以使用 efs、cephfs、glusterfs 等并行文件系统

## 简要步骤

- 在前置工作完成后，获取 jenkins service account 的 token
- 在 Jenkins master 上添加必要的 Credentials
- 在 Jenkins master 上安装 Kubernetes 插件
- 在 Jenkins master 上创建 Kubernetes 云
- 在 Jenkins master 上创建 pipeline 项目，并添加所需的变量
- 在 Gitlab 项目中创建 webhook , push 事件触发构建