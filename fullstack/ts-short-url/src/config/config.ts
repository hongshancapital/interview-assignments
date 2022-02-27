
export interface Config {
    server_port: number,  // 应用程序监听端口
    redisUrl: string,     // Redis服务地址
    bl_use: boolean,      // 是否使用布隆过滤器
    cacheTime: number,    // 缓存时间,单位秒
    urlFilter: string,    // 布隆过滤标识
    db_Type: string,      // 数据库类型，默认MYSQL
    db_name: string,      // 数据库库名
    db_username: string,  // 数据库用户名
    db_password: string,  // 数据库用户密码
    db_host: string,      // 数据库地址
    db_port: number,      // 数据库端口
    db_ssl: false,        // 是否使用SSL链接数据库
    shorturl_pre: string, // 短域名
    urllen: number,       // 短域名ID长度
    retrynum: number      // 短域名尝试生成次数
  }

  const cfgs: Config = {
    server_port:8080,
    redisUrl: "redis://192.168.99.100:6379",
    bl_use: true,
    cacheTime: 3600,
    urlFilter: 'filter',
    db_Type: 'mysql',
    db_name: 'short_url',
    db_username: 'su',
    db_password: 'su',
    db_host: "192.168.99.100",
    db_port: 3306,
    db_ssl: false,
    shorturl_pre: "http://s.cn/",
    urllen:8,
    retrynum:5
  }
  
  export default cfgs