## 使用方法
### 下载安装docker

* 启动docker mysql镜像，自动创建short库short表
    ```bash
    docker-compose up -d --build
    ```
* 关闭mysql镜像
    ```bash
    docker-compose down
    ```
* 启动client
    ```bash
    cd client
    npm i
    npm run dev
    ```
* 启动services
    ```bash
    cd services
    npm i
    npm run start:dev
    ```

### 创建mysql data目录
> mkdir -p mysql/data/，挂载容器下的/var/lib/mysql目录

### 通过docker-compose启动mysql，client，service容器

## 技术栈
* 前端：nextjs + react + react-query + antd
* 后端：nestjs + typeorm + mysql
* 数据层： mysql

## 注意端口请勿占用
* nextjs 3001端口
* nestjs 3002端口
* mysql 3308端口

## todo
* nodejs获取环境变量方式优化，抽取到config.js中
* 前端接口竟态问题
* antd 组件懒加载问题
* 增加nodejs接口，传入短链接，接口返回302直接跳转到对应的长链接
* 将client和services集成到docker-compose中
