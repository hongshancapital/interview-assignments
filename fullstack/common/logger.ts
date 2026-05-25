import * as log4js from 'log4js';
export const logger = log4js.getLogger();
export const reqLog = log4js.getLogger('reqLog');
const level = process.env.LOG_LEVEL ? process.env.LOG_LEVEL : 'debug';
log4js.configure({
  appenders: {
    // 控制台输入
    console: { type: 'console' },
  },
  categories: {
    // 默认日志
    default: {
      appenders: ['console'],
      level: level,
    },
    reqLog: {
      appenders: ['console'],
      level: level,
    },
  },
});
