## Short DNS 短域名获取解析


### API 说明
#### 获取短域名
post 方式获取，数据为长域名完整信息和有效时间（单位为秒）
>curl --location --request POST 'http://127.0.0.1:8080/s' \
--header 'Content-Type: application/json' \
--data-raw '{
    "url": "长域名完整连接信息",
    "exp": 20
}'

return:
>{
    "url": "http:127.0.0.1:8080/s/tIQw9FWM"
}

#### 获取长域名
通过获取短域名返回链接获取长域名

>curl --location --request GET 'http://127.0.0.1:8080/s/7ClxqAWR'

return: 
>{
    "url": "长域名完整连接信息"
}

### 环境说明
需要安装 Node、NPM、Redis 等运行环境

>short-dns> node -v
v18.16.0
short-dns> npm -v
9.5.1
short-dns> redis-server --version
Redis server v=6.2.3 sha=00000000:0 malloc=libc bits=64 build=ee2cbeb9e7689ac7

### 项目运行
先将运行环境准备好
>安装依赖：npm install

>开发调试：npm run start

