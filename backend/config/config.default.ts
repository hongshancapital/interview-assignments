/**
 * @file 默认配置文件（环境配置优化此配置）
 * @author zengbaoqing<misterapptracy@gmail.com>
 */
import { Context } from 'egg';
import baseError from '../app/core/base/baseError';

export default {
  cluster: {
    listen: {
      port: 8001,
    },
  },
  keys: 'LKHLKLHKLH^&*453699!!@@##&&MMMMMM&l=)',
  logger: {
    dir: './logs/short-url',
    consoleLevel: 'INFO',
  },
  onerror: {
    json(err: any, ctx: Context) {
      // 在此处定义针对所有响应类型的错误处理方法
      // 注意，定义了 config.all 之后，其他错误处理方法不会再生效
      // 所有其他错误都status 1005 返回
      // 其他的返回状态码不要覆盖1005
      ctx.body = JSON.stringify(baseError.serverError(err.message || '系统错误'));
      ctx.status = 200;
    },
    html(err: any, ctx: Context) {
      // html hander
      ctx.body = JSON.stringify(baseError.serverError(err.message || '系统错误'));
      ctx.status = 200;
    },
  },
  // middleware: [],
  security: {
    xframe: {
      enable: false,
    },
    csrf: {
      enable: false,
    },
  },
  multipart: {
    fieldSize: 2 * 1024 * 1024,
    fields: 30,
  },
  bodyParser: {
    jsonLimit: '500mb',
    formLimit: '500mb',
    enable: true,
    encoding: 'utf8',
    strict: true,
    queryString: {
      arrayLimit: 5000,
      depth: 20,
      parameterLimit: 5000,
    },
  },
  // redis配置
  redis: {
    client: {
      port: 6379,
      host: '127.0.0.1',
      password: '',
      db: 0,
    },
  },
  // mysql 配置
  sequelize: {
    dialect: 'mysql',
    database: 'short_url',
    username: 'root',
    password: '',
    host: '127.0.0.1',
    port: 3306,
    pool: {
      max: 100,
      min: 0,
      idle: 2000,
    },
    define: {
      engine: 'InnoDB',
      timestamps: false,
      createdAt: 'createTime',
      updatedAt: 'updateTime',
      charset: 'utf8mb4',
    },
  },
};
