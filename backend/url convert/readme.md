# 需求分析

需设计 api 以实现长 url 与短 url 的互相转换, 其中短 url 的路径 (path) 长度不应超过 8 个字符.
# 整体思路

将用户输入的长 url 保存至 db, 以唯一 id (类型为数字) 区分这些 url.

在获取某个长 url 对应的短 url 时, 将 id 按照一定的计算规则转换为短 url 的 path 字符后整理返回.

在获取某个短 url 对应的长 url 时, 先将短 url path 转换为 id, 再由 id 到 db 中获取对应的长 url, 然后整理返回.

考虑到一些数据可能会被频繁请求, 因此为提高性能, 除了使用 db 存储数据外, 还需要将请求频繁的数据缓存起来.

实现上述功能所需的整体架构如下: 

![architecture](https://github.com/Chitzkoi1/hongshan-ck/assets/34273803/4e35daf5-512e-42df-9ef7-3b6720dd2e6a)


# 存储设计

基于以上设计思路, 实现本需求需要进行数据存储及缓存. 

选用 MySQL 作为数据存储的 db, 选用 Redis 作为缓存中间件.

MySQL 建表语句:
```sql
drop database if exists `short_url`;

CREATE DATABASE `short_url` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

use `short_url`;

create table origin_url (
  id bigint unsigned auto_increment comment '自增 id',
  url text not null comment '原 url',
  primary key (id),
  key (url(767))
) ROW_FORMAT=DYNAMIC charset=utf8mb4;
```

Redis 需要缓存两种数据, 如下:
- 短 url 到长 url 的映射: 其 key 格式为 `url:shortToLong:${path}`, 其中 path 表示短 url 的 path, 其值为完整的长 url;
- 长 url 到短 url 的映射: 其 key 格式为 `url:longToShort:${url}`, 其中 url 表示长 url 的完整值, 其值为短 url 的 path.
以上两种缓存数据的 key 均可设置 TTL 为 5 分钟.
# 实现细节

## 短 url path 的编码方式

将存储的长 url id 以 36 进制 (数字 + 小写字母) 的编码方式转换为短 url path. 这样处理的原因是: 
1. js 支持的进制转换最大到 36 进制, 这样的处理便于实现;
2. 使用 8 位 36 进制编码能够记录 36^8 = 2.8 万亿条数据, 这个量级已经足够庞大.

## 由长 url 获取短 url

先查询请求中的长 url 对应的短 url 是否在 Redis 缓存中: `get url:longToShort:${url}`, 如获取到则直接组装返回; 

若未获取到则查询长 url 对应的短 url 是否在 db 中有记录: `select id from origin_url where url = ?`;

若在 db 中查询到了 id 则将 id 转换为 path 后返回, 同时将长 url 及对应短 url path 的记录进行缓存: `set url:longToShort:${url} ${value}`;

若在 db 中未查询到长 url 对应的 id, 则将该数据写入 db: `insert ignore into origin_url(url) values(?)`, 该 sql 执行返回结果的 insertId 即为插入数据的 id. 将 id 转换为 path 后返回, 同时将长 url 及对应 path 的记录进行缓存 (Redis 命令同上).

综上, 由长 url 获取短 url 的操作在执行过程未发生错误的情况下应有对应结果返回; 若执行过程中的任一环节出现了错误, 除写 Redis 的操作外均应返回 HTTP 错误码 500.

流程图如下: 

![longToShort](https://github.com/Chitzkoi1/hongshan-ck/assets/34273803/3c3d37e7-f71d-466c-8b96-ce1d39cb77c5)


## 由短 url 获取长 url

先查询请求中的短 url path 对应的长 url 是否在 Redis 缓存中: `get url:shortToLong:${path}`, 如获取到则直接组装结果返回;

若未获取到则将短 url path 转换为 id, 由 id 从 db 中获取对应的长 url: `select url from origin_url where id = ?`;

若能够从 db 获取到长 url 数据则组装结果返回;

若未能够从 db 获取到对应的长 url, 则表明该短 url 无效. 应返回 HTTP 错误码 404; 此外若上述过程中的任一环节出现了错误, 则应返回 HTTP 错误码 500.

流程图如下:

![shortToLong](https://github.com/Chitzkoi1/hongshan-ck/assets/34273803/fe937aba-4201-4a2a-9063-cc203a4e0bb7)


# API 信息

## 由长 url 获取短 url

path: /longToShort

HTTP Method: POST

请求体格式: json

请求入参: 
- url: 长 url 地址, 字符串, 如: `https://www.tianyancha.com/`

响应格式: json

响应参数:
- result: 短 url 完整地址, 字符串, 如: `https://s.com/1`

## 由短 url 获取长 url

path: /shortToLong

HTTP Method: POST

请求体格式: json

请求参数: 
- url: 短 url 地址, 字符串, 如: `https://s.com/1`

响应格式: json

响应参数:
- result: 短 url 完整地址, 字符串, 如: `https://www.tianyancha.com/`

# 测试结果

## 单元测试

单元测试覆盖情况如下:

![coverage](https://github.com/Chitzkoi1/hongshan-ck/assets/34273803/c8f75c6b-7422-4d0d-9ccb-4221be5f3dd8)


说明: 对于 app.ts, 其未覆盖到的行包含的逻辑为 error handler.

## API 集成测试

接口所用的 HTTP 请求 Content-Type 均为 application/json.
### POST /longToShort

#### 用例 1

请求 body: 

```json
{
    "url": "https://www.tianyancha.com/"
}
```

响应 body:
```json
{
	"result": "https://s.com/1"
}
```
#### 用例 2

请求 body: 

```json
{
}
```

响应: 无 body, HTTP Code = 400

### POST /shortToLong

#### 用例 1

请求 body: 

```json
{
    "url": "https://s.com/1"
}
```

响应 body:
```json
{
	"result": "https://www.tianyancha.com/"
}
```

#### 用例 2

请求 body: 

```json
{
    "url": "https://s.com/123456789"
}
```

响应: 无 body, HTTP Code = 404
#### 用例 3

请求 body: 

```json
{
}
```

响应: 无 body, HTTP Code = 400
# 工程说明
## 工程结构

相较于长期维护的后端项目工程, 本次 assignment 使用的工程结构较为简单, 体现在以下方面:

- 对于各逻辑层均没有单独创建目录, 只创建了一个模块.
- MySQL 及 Redis 等配置信息没有使用单独的配置模块, 而是直接写在代码中.
- 没有使用成熟的日志收集工具 (如 winston), 所有需要输出的内容均使用 console.
- 没有单独定义业务错误码, 而是直接使用了 HTTP Code 来作为业务的错误码来使用.

以上设计的原因是, 考虑到需要实现的功能较为简单, 为避免工程结构变得臃肿, 实现的过程尽可能的去除了 "不必要" 的设计.
## 一些依赖

对于工程引入的一些依赖进行解释说明: 

- vitest: 单元测试模块, 兼容 jest 且相较于 jest 更加易用.
- supertest: 用于以类似于单元测试的方式或写法进行 API 测试.
- express-async-errors: 用于捕获服务执行过程中产生的异步异常, 避免因异步异常的抛出而直接导致进程崩溃.
