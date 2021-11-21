### Q1

脚本基于日志中的关键字进行过滤后再进行相应的格式整理，因不确定processId是否应该去除前缀的一些内容只保留数字，就暂时保留原内容不做进一步处理。

**后续待改进部分**

* 添加日志记录运行过程可能出现的问题。

### Q2

整个CI/CD流程原计划通过Jenkins作为平台基于Ansible和aws cli来进行整个环境的搭建及后续监控及报警的配置，但实际测试过程中发现设置报警部分无法通过命令行来实现（cloudwatch相关部分）

**Jenkinsfile**

此文件只负责程序打包上传镜像

**playbooks**

* cd.yaml负责执行应用的环境创建及服务的部署
* alert.yaml负责创建报警相关的工作
* yaml中存在的一些变量例如<code>region_name,app_name</code> 均可通过在jenkins中配置来进行调整，
* 可通过修改yaml执行方式，基于<code>with_items</code>功能针对多region部署进行调整

**补充内容**
* Jenkins Server中需要存放有AWS相关账户信息，例如<code>AWS_ACCESS_KEY_ID</code> <code>AWS_ACCESS_KEY</code> 敏感信息可通过Ansible的vault功能进行加密

**后续待改进部分**

* 可以考虑从Ansible替换为Terraform,对云平台的管理功能支持更多更灵活
* CI部分可通过抽取为lib包，然后通过build.yaml来执行所需的CI步骤
* 要求为开发人员的源代码更改合并到master分支之前，所以CI中的分支应从webhook中获取，<code>BRANCH_NAME</code>获取方式根据实际情况修改
* 部分脚本内容可能还需要进行调整，才可达到最终期望的效果
* CI-->CD如需自动触发可通过配置Jenkins中的job trigger进行设置
