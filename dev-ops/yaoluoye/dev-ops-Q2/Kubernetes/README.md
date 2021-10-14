# 说明

在 Kubernetes 中动态创建 jenkins agent 的前置工作

```bash
kubectl apply -f jenkins-slave-prepare/
```

部署 docker daemon

```bash
kubectl apply -f docker-dind.yaml
```