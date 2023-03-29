import { MidwayConfig } from '@midwayjs/core';

export default {
  // use for cookie sign key, should change to your own and keep security
  keys: '1675769658866_9549',
  koa: {
    port: 7001,
  },
  view: {
    defaultViewEngine: 'nunjucks',
  },

  typeorm: {
    dataSource: {
      default: {
        /**
         * 单数据库实例
         */
        type: 'mysql',
        host: '39.98.171.103',
        port: 32006,
        username: 'root',
        password: '123456',
        database: 'dev',
        synchronize: true,     // 如果第一次使用，不存在表，有同步的需求可以写 true，注意会丢数据
        logging: false,

        // 配置实体模型
        // entities: [Photo],

        // 或者扫描形式
        entities: [
          '*/entity/*.entity{.ts,.js}'
        ]
      }
    }
  },
} as MidwayConfig;
