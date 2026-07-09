import resCodes from "./resCode.config";

const configs: {[key: string]: any} = {
  generator: {
    machineId: process.env.MACHINE_ID,
    batchCount: 500
  },
  resCodes: resCodes,
  shortLinktHost: "https://scdt.sl/",
  redis: {
    port: 6379, 
    host: "localhost",
    keyPrefix: "shortLink:", // 添加key的统一项目前缀
  },
  sequelize: {
    database: "short_url",
    username: "root",
    password: "root",
    host: 'localhost',
    dialect: 'mysql'
  },
  REDIS_TTL: 30 * 24 * 3600,
  LRU_MISS_TTL: 15 * 60 * 1000,
}

export default configs;