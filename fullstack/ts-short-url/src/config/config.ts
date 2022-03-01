
export interface Config {
    appServerPort: number,  // 应用程序监听端口
    redisUrl: string,     // Redis服务地址
    blUse: boolean,      // 是否使用布隆过滤器
    cacheTime: number,    // 缓存时间,单位秒
    urlFilter: string,    // 布隆过滤标识
    dbName: string,      // 数据库库名
    dbUserName: string,  // 数据库用户名
    dbPassword: string,  // 数据库用户密码
    dbHost: string,      // 数据库地址
    dbPort: number,      // 数据库端口
    dbSSL: false,        // 是否使用SSL链接数据库
    shortUrlPre: string, // 短域名
    urlLen: number,       // 短域名ID长度
    retryNum: number      // 短域名尝试生成次数
  }

  const cfg: Config = {
    appServerPort:8080,
    redisUrl: "redis://127.0.0.1:6379",
    blUse: true,
    cacheTime: 3600,
    urlFilter: 'filter',
    dbName: 'short_url',
    dbUserName: 'su',
    dbPassword: 'su',
    dbHost: "127.0.0.1",
    dbPort: 3306,
    dbSSL: false,
    shortUrlPre: "http://s.cn/",
    urlLen:8,
    retryNum:5
  }
  
  export default cfg