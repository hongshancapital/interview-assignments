
## 设计思路

一、由于我们不关心长url对应的shortkey是什么。我们可以利用mysql唯一索引特性，设计一个kgs服务.在mysql数据库中预先生成一定数量的shortkey，同时将这些shortkey，使用redis的set数据类型存储起来，同时检查redis中的shortkey数量是否在某个比例，低于比例就让mysql生成shortkey，继续存储到redis中。

二、生成短域名的时候，直接从redis获取shortkey。然后将数据库中对应的shortKey那条记录的url更新上。





#### 域名接口服务

```
npm run start 
```

#### GKS服务

```
npm run task
```

