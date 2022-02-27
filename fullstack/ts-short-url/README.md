# Typescript + Express + Node.js + Redis + bloomFilter + Mysql
 这是一个基于Typescript语言编写的短域名服务应用程序

## 安装

## redis

安装Redis
`docker pull redislabs/rebloom:latest`

启动Redis
`docker run -p 6379:6379 --name redis-redisbloom -itd redislabs/rebloom:latest`

## MySQL

安装MySQL
`docker pull mysql:latest`

启动MySQL
`docker run -itd --name mysql-ts -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql`

进入数据库初始化数据库脚本
`docker exec -it mysql-ts bash`

登陆并密码123456
`mysql -h localhost -u root -p`

创建数据库
`create database  short_url;`


创建数据库用户、授权、调整用户密码存储方式（针对MySQL8.0以上的版本）
`create user  'su'@'%' identified by 'su';`
`grant all on short_url.* to 'su'@'%';`
`alter user 'su'@'%' identified with mysql_native_password by 'su';`


创建表结构
`use short_url;`

`CREATE TABLE SHORT_URL (shorturlid VARCHAR(255) COMMENT '短链接ID',originalurl VARCHAR(255) COMMENT '长连接URL',createdata VARCHAR(10) COMMENT '生成日期',PRIMARY KEY (shorturlid)) ENGINE=InnoDB DEFAULT CHARSET=utf8;`

## 应用程序安装
安装程序依赖库
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