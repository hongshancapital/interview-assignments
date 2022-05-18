### 3.2 接口文档

Test 环境
HOST： 127.0.0.1:3777
数据格式：Content-Type: application/json

- 鉴权：// TODO

- 通用响应格式
  |参数 | 类型 | 选择 | 说明
  | - | - | - | -
  code | string | 必选 | 响应码，000000： 为正常响应，否：其他
  msg | string | 可选 | 响应说明
  data | object | 条件 | 响应数据，在 code 为 000000 时必选，其中 data 的数据格式如下：
- 示例

```
{
    "code": "000000",
    "msg": "ok",
    "data": {
        "slink": "mw4aruuhs"
    }
}
```

#### 3.2.1. 查询已有配置(query)：

> GET /man/q
> 查询当前已经在系统配置的短链信息

- 输入参数：
  | 参数 | 类型 | 选择 | 说明
  |- | - | - | -
  appid | string, 32 | 必选 | 应用 id，提前分配
  slink | string, 8 | 必选 | 短链 id

- 响应数据：
  | 参数 | 类型 | 选择 | 说明
  |- | - | - | -
  oriUrl | string, 256 | 必选 | 对应的原始 url 地址
  slink | string, 8 | 必选 | 短链 id
- 示例

```
GET /man/q?appid=adfs&slink=d9JkdY HTTP/1.1
Host: 127.0.0.1:3777

{
    "code": "000000",
    "msg": "ok",
    "data": {
        "slink": "HmV1aMOiL",
        "oriUrl": "https://baidu.com"
    }
}

```

#### 3.2.2. 增加短链(add)：

> POST /man/a
> 增加

- 输入参数：
  | 参数 | 类型 | 选择 | 说明
  |- | - | - | -
  |appid | string, 32 | 必选 | 应用 id，提前分配
  |oriUrl | string, 256 | 必选 | url 地址

- 响应数据：
  | 参数 | 类型 | 选择 | 说明
  |- | - | - | -
  | slink | string, 8 | 必选 | 短链 id
- 示例

```
POST /man/a HTTP/1.1
Host: 127.0.0.1:3777
Content-Type: application/json

{
	"appid":"adfs",
	"oriUrl":"https://163.com"
}
```

```
{
    "code": "000000",
    "msg": "ok",
    "data": {
        "slink": "d9JkdY"
    }
}
```

#### 3.2.3. 修改短链(update)：

> POST /man/u
> 增加

- 输入参数：
  | 参数 | 类型 | 选择 | 说明
  |- | - | - | -
  |appid | string, 32 | 必选 | 应用 id，提前分配
  |oriUrl | string, 256 | 必选 | url 地址
  |slink | string, 8 | 必选 | 要修改的短链地址

- 响应数据：
  | 参数 | 类型 | 选择 | 说明
  |- | - | - | -
  | slink | string, 8 | 必选 | 短链 id
  |oriUrl | string, 256 | 必选 | url 地址
- 示例:

```
POST /man/u HTTP/1.1
Host: 127.0.0.1:3777
Content-Type: application/json

{
	"appid":"adfs",
	"oriUrl":"https://baidu.com",
	"slink": "q98"
}
```

```
{
    "code": "000000",
    "msg": "ok",
    "data": {
        "slink": "q98",
        "oriUrl": "https://baidu.com"
    }
}
```

#### 3.2.4. 页面跳转：

> POST /l/:slink
> 跳转

```
http://127.0.0.1:3777/l/q98

```
