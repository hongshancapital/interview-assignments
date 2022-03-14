# 项目简介
基于 midway + redis + mysql 搭建的短链服务，对外提供生成短链及获取长链接口


# 整体设计

### 架构图
![](https://withnate-1259201219.cos.ap-shanghai.myqcloud.com/pics/deploy_region.jpg)

- 每个集群包含前端负载均衡(nginx)、urlserver集群、redis、mysql
- 根据需要在多个可用区部署多个这样的集群，设置不同的短链接域名,如 `https://a.cn` 、 `https://b.cn`，提高系统的容量
- 使用校验码，拦截无效请求
- 使用布隆过滤器过滤拦截一定不在 DB 的请求，拦截大量无效请求
- 使用redis缓存，降低db压力

### sql
参见文件 docs/init.sql

### 短链生成算法

短链生成算法参考自 https://leetcode-cn.com/circle/discuss/EkCOT9/
基于 62 进制的字符串与数据库 ID 的转换

此算法存在的风险，可以根据遍历短链获取所有的数据；解决办法，前面7位是根据id生成的，最后一位为根据七位生成的校验码，与身份证最后一位类似，`salt`配置在config文件中。
单个集群最多生成 62 的 7 次方条数据，可以部署多集群提高系统容量。

### 长链接生成短链接时序

![](https://withnate-1259201219.cos.ap-shanghai.myqcloud.com/pics/long.jpg)

### 短链接读取长链接时序
![](https://withnate-1259201219.cos.ap-shanghai.myqcloud.com/pics/short.jpg)

### 单元测试 & 集成测试
![](https://withnate-1259201219.cos.ap-shanghai.myqcloud.com/pics/cov.jpg)

# 快速开发


### midway

如需进一步了解，参见 [midway 文档][midway]。

### 本地开发

配置本地`redis` 参见 https://github.com/RedisBloom/RedisBloom , redis需要集成布隆过滤器插件

需要将`src/config/config*.ts` 中的mysql和redis修改为本地调试的配置

```bash
$ npm i
$ npm run dev
$ open http://localhost:7001/
```

`vscode debug` 参见 http://www.midwayjs.org/docs/debugger#%E5%9C%A8-vscode-%E4%B8%AD%E8%B0%83%E8%AF%95


### 内置指令

- 使用 `npm run lint` 来做代码风格检查。
- 使用 `npm test` 来执行单元测试。

# 使用示例

### 基于长链生成短链

```js
curl -X POST -d "url=http://www.baidu.com" http://127.0.0.1:7001/api/createByLongUrl

// 返回数据

{ "code":0,
  "msg":"ok",
  "data":{
    "url":"https://withnate.cn/o9"
  }
}
```

### 基于短链换回长链

```js
curl http://127.0.0.1:7001/api/getByShortUrl?url=https://withnate.cn/l

// 返回数据

{
  "code": 0,
  "msg": "ok",
  "data": {
    "url": "http://www.baidu.com"
  }
}
```

## TODO

- 添加ip来源限制，避免某个ip大量请求
- 删除功能待开发
