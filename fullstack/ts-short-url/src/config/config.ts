
export interface Config {
    server_port: number,
    redisUrl: string,
    bl_use: boolean,
    cacheTime: number,
    urlFilter: string,
    bfbyte: number,
    bfHashCnt: number,
    db_Type: string,
    db_name: string,
    db_username: string,
    db_password: string,
    db_host: string,
    db_port: number,
    db_ssl: false,
    shorturl_pre: string,
    urllen: number,
    retrynum: number
  }

  const cfgs: Config = {
    server_port:8080,
    redisUrl: "redis://192.168.99.100:6379",
    bl_use: true,
    cacheTime: 1000,
    urlFilter: 'filter',
    bfbyte: 32*256,
    bfHashCnt: 16,
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