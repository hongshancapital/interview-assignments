# Typescript + Express + Node.js + Redis（bloomFilter） + Mysql
 这是一个基于Typescript语言编写的短域名服务应用程序

## 安装依赖服务

## 通过docker安装Redis

安装Redis
`docker pull redislabs/rebloom:latest`

启动Redis
`docker run -p 6379:6379 --name redis-redisbloom -itd redislabs/rebloom:latest`

## 通过docker安装MySQL

安装MySQL
`docker pull mysql:latest`

启动MySQL
`docker run -itd --name mysql-ts -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql`

进入数据库初始化数据库脚本
`docker exec -it mysql-ts bash`

登陆并输入刚设置的数据库密码123456
`mysql -h localhost -u root -p`

执行数据库脚本
src\script\init.sql

## 应用程序安装
安装依赖库
`$ npm install`

编译
`$ npm run build`

启动
`$ npm run start`

快速启动
`$ npm run restart`

## 单元测试
`$ npm test`

## 应用程序环境配置
具体环境配置信息，查看./config/config.ts