const fs = require('fs');
const path = require('path');
import { EggAppConfig, EggAppInfo, PowerPartial } from 'egg';
import database from './database';

export default (appInfo: EggAppInfo): any => {
  const config = {
    middleware: [
      'errorHandler',
      'authority',
    ],
    static: {
      prefix: '/public/',
      dir: path.join(appInfo.baseDir, 'public'),
    },
    security: {
      csrf: {
        enable: false,
      },
    },
    session: {
      key: 'MAPI_SESSION',
      maxAge: 24 * 3600 * 1000,
      httpOnly: true,
      encrypt: true,
    },
  };

  const bizConfig = {
    siteFile: {
      '/favicon.ico': fs.readFileSync(path.join(appInfo.baseDir, 'icon', 'favicon.ico')),
    },
    customLoader: {
      dao: {
        directory: 'app/dao',
        // 如果是 ctx 则使用 loadToContext
        inject: 'ctx',
        // 是否加载框架和插件的目录
        loadunit: false,
      },
      redis: {
        directory: 'app/redis',
        inject: 'app',
        // 是否加载框架和插件的目录
        loadunit: false,
      },
    },
    sequelize: {
      pool: {
        max: 5,
        min: 0,
        idle: 10000,
      },
      define: {
        timestamps: false,
        paranoid: false,
        freezeTableName: true,
        underscored: true,
      },
      timezone: '+08:00',
      ...database.development,
    },
    redisConfig: {
      host: '127.0.0.1',
      port: 6379,
    },
  };


  return {
    ...config,
    ...bizConfig,
  } as PowerPartial<EggAppConfig>;
};
