// 主数据库
const RedisMainDbConnect = {
  url: "localhost",  
  port: "6379", 
  password: process.env.REDIS_PASSWORD,
  db: 1  
}
const PREFIX = "slink|"
const EXP = 7 * 24 * 60 * 60 // 缓存有效期，一周，
const SHORT_EXP = 8 * 60 * 60 // 短缓存，如果查询不存在，临时缓存，防止重复调用

export default { RedisMainDbConnect, PREFIX, EXP, SHORT_EXP }
