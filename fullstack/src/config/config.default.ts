import { MidwayConfig } from '@midwayjs/core';

export default {
  // use for cookie sign key, should change to your own and keep security
  keys: 'urlserver',
  koa: {
    port: 7001,
  },
  sequelize: {
    options: {
      dialect: 'mysql',
      database: 'url_d1',
      username: 'test',
      password: '123456',
      host: '127.0.0.1',
      port: 3306,
      encrypt: false,
    },
    sync: false,
  },
  redis: {
    client: {
      port: 6379, // Redis port
      host: '127.0.0.1', // Redis host
      // password: 'auth',
      // db: 0,
    },
  },
  salt: { sha: 'urlserver_v1' },
} as MidwayConfig;
