## TypeScript Fullstack Engineer Assignment

### Typescript 实现短域名服务（细节可以百度/谷歌）

撰写两个 API 接口

- 短域名存储接口：接受长域名信息，返回短域名信息
- 短域名读取接口：接受短域名信息，返回长域名信息。

限制

- 短域名长度最大为 8 个字符（不含域名）


## 项目

项目启动步骤 

1. 点击docker_install.cmd,启动docker容器
2. 安装项目依赖，运行项目，npm run build & npm run serve
3. 访问localhost:3000 点击登陆，输入任意符合要求账号，密码登陆
4. 点击域名服务
5. 输入长域名获取短域名
6. 输入短域名获取长域名信息

项目通过siphash24算法生成短域名，并存入redis，通过对redis操作获取短域名

![长域名-短域名](D:\johnproblem\interview-assignments\fullstack\image\长域名-短域名.png)

![短域名-长域名](D:\johnproblem\interview-assignments\fullstack\image\短域名-长域名.png)

![数据流向](D:\johnproblem\interview-assignments\fullstack\image\数据流向.png)

---------------------------------------------------------------------------------------
项目
 功能点
 1.基础用户校验。
 2.对域名进行siphash24加密，生成8位字符串
 3.将字符串与长域名进行映射，存入redis数据库