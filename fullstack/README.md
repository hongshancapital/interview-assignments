<!--
 * @Author: zhangyan
 * @Date: 2023-03-10 20:55:33
 * @LastEditTime: 2023-03-12 03:33:06
 * @LastEditors: zhangyan
 * @FilePath: /fullstack/readme.md
 * @Description: readme
-->
# 基于 nodejs + express的短网址系统Demo演示
## 说明
### 本系统实现了一个最基本功能的短网址系统演示，提供两个接口，分别来获取短链接和生成短链接，加入了redis缓存来提高接口性能

---
## 系统架构
![系统架构](/系统架构.png)

 1. 提供2个接口，分别是获取短链接 get_url，和生成短链接 set_url
 2. 使用redis缓存，这里简单起见对整个response进行数据缓存
 3. 业务层提供2个基本流程，获取短链接和生成短链接（已实现），常见短网址系统还有后台管理，黑名单管理，数据统计，日志等其他功能（未实现）
 4. 数据层提供对数据库的操作(已实现)，根据访问流量和并发还会有事务处理，缓存处理等（未实现）
 5. 数据库这里简单使用mongoDb
 6. 运行环境简单使用PM2常驻运行

 ---
 ## 表设计
![表设计](/表设计.png)

nodejs里用以下Schema生成表
```javascript
new Schema({
   id:{ type: ObjectId, required: true, unique: true }
   full_url: { type: String, required: true },
   create_time: { type: String, required: true },
   token: { type: String, required: true }
})
```
因为是demo演示，简单设计4个字段，主键id自增且唯一(本demo因为不会利用自增id来生成token，简单起见采用默认的objectId类型)，其余三个字段分别存储网址，创建时间和对应的token
(常见还有最后访问时间，过期时间，访问次数等未添加)

---
## 单元测试
![单元测试](/单元测试.png)

单测使用jest + supertest，分别对逻辑，数据库读写和接口访问进行测试


---
## API

- GET http://{url}/get_url?token=xxxxx
  - @param：{ token } 生成短链接的token码
  - @return：![接口示例](/接口示例.png)

- POST http://{url}/set_url
  - @param： { url } 需要生成短链接的网址
  - @return：![接口示例](/接口示例.png)

---
## 运行

- 安装 MongoDb
- 安装 redis
- yarn install
- yarn start

---
## 其他

- http://{url}/token/xxxx
  - 通过url直接访问，如果token存在会直接跳转到目标网站

