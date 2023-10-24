# 说明

* 为了面试，我写了3中db的方式：memory/sqlite/redis
* 为了方便测试，SQL用的SQLite内存，Redis用的RedisMemoryServer
* 没有使用任何成熟的Framework，比如：nest，typeorm等等
* 因为是 fullstack 所以把 react 的部分也随意写了

## 数据说明

```ts
type UrlStoreData = {
  id: number          // 唯一id
  short: string       // 短链
  url: string         // 原始链接
  createTime: number  // 创建时间
  refreshTime: number // 刷新时间
}
```

* refreshTime用于冷热备份，但是这里因为作业，没有实现
* 实际情况还可以增加各种统计字段

## 算法

算法用的最简单的 number to base62，还可以使用hash之类的

