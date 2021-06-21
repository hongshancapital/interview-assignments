自己fork了一个spring-boot的项目: https://github.com/lolspider/spring-boot-hello.git

```markdown
App: spring-boot-hello
CI: Github Action
Platform: AWS Fargate
```

### CI
CI 选择了github actions.
workflow文件在[workflow/master.yml](./workflow/master.yml)

流水线包含两个stage:
* test - 用于代码测试
* build - 用于生成jar包并打包成docker镜像上传到Docker Hub

Tips: 
  * 测试和生成jar包都是运行的maven 
  * Docker images的tag名字由**BRANCH_NAME-GITHUB_RUN_NUMBER**组成,比如master-8

用于打包docker镜像的Dockerfile[在这](Dockerfile)
    

### 基础设施服务
基于Python使用AWS CDK创建AWS Fargate,代码[在这](./cdk/app.py),cdk使用说明[在这](./cdk/README.md)）由于AWS账号原因，该代码并未进行测试。

### CD
CD部分没有进行设计，想法是可以基于cdk脚本和github actions进行部署。