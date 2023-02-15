# README

### Note 说明
* Please check the folder for design and diagrams: /design/diagram
* Please check the folder for SQL DDL: /design/sql
* Please check the folder for Postman test: /design/postman
* Please check the folder for Unit Test Coverage: /design/test


### Assumption 假定
* Ever used java for v1, and used TypeScript & Express for this version\
  之前经猎头确认可用任意编程语言，故第一版采用了Java；当前这版采用TypeScript和Express
* Only implement the server side according to the Fullstack Engineer Assignment\
  根据作业要求，仅实现服务端及接口，暂未开发前端网页
* API TPS is limited by Firewall (infra)\
  API接口调用频率限制由基础架构防火墙控制
* Don't implement Membership and Authentication for now\
  本次暂时不引入会员管理及权限控制，后续可扩展开发
* All the tiny urls use the same domain, dependent on the deployment\
  所有短链接共用一个域名，根据部署环境来定，短域名存储接口返回短域名的路径
* Don't consider the scenarios of editing or deleting tiny url for now\
  本次暂时不考虑编辑及删除短链接场景
* The statics of tiny url access is out of scope\
  短链接调用统计不在本次范围
* Don't judge whether the target URL is duplicate\
  不考虑长链接重复的情况，即使之前有长链接申请过，仍会生成新的短链接
* The length of Target URL is no longer than 1024\
  长链接长度不超过1024
* Tiny url will be not be expired once generated\
  短链接永久有效，暂不考虑失效时间
* Tiny url is case-sensitive and consists of 62 characters including a-zA-Z0-9\
  短链接是大小写敏感的，由62种字符组成，包括a-zA-Z0-9
* REST interfaces only return the result without any status code or error message for now\
  REST接口仅返回所需要的信息，暂不包括状态码或错误信息
* Don't consider log framework and collection this time\
  本次暂不考虑日志框架及收集
* The configuration is given only for local development environment\
  仅给出本地开发环境配置；测试及生产环境，可根据实际情况再配置
* Apply Mysql as the database, and authenticate by mysql_native_password\
  采用Mysql作为数据库，并采用mysql_native_password认证方式
* Run "yarn test --coverage" for code coverage\
  运行"yarn test --coverage"计算代码覆盖率