# 用户注册

## 项目介绍
- 本项目基于Docker搭建运行环境
- 项目依赖安装使用composer
- 使用自己开发的框架，依赖第三方monolog、think-orm
- docker镜像也为自研镜像

## 环境搭建
1. 在`interview-assignments/php/server`目录下使用如下命令
```
composer install
```
2. 在本地安装docker
3. 在项目根目录执行命令
```
docker network create -d bridge \
    --subnet 10.2.2.0/24 \
    --gateway 10.2.2.1 \
    denet
docker run -d --network denet --ip 10.2.2.8 \
    -p 3000:80 --name obj \
    -v $PWD/:/usr/share/nginx/html \
    -v $PWD/php/server/nginx/conf.d:/usr/share/nginx/conf/conf.d \
    -v $PWD/php/server/nginx/nginx.conf:/usr/share/nginx/conf/nginx.conf \
    decezz/php:7.4-fpm-alpine
docker run -d --network denet --ip 10.2.2.4 \
    -p 3306:3306 \
    --name mysql \
    -e MYSQL_ROOT_PASSWORD=secret20 \
    --restart always \
    mysql:5.7
```
4. 访问`http://localhost:3000/php/client/form.html`即可

## FAQ
1. 注意：docker命令中有启动mysql服务，如果mysql服务启动在本地，请勿使用`127.0.0.1`或者`localhost`去连接，因为PHP环境寻址是在容器内
2. 如果使用自建MySQL，可以不执行 1、3 的docker命令，MySQL路由地址应该在局域网或公网寻址。
