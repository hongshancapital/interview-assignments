import log4js from 'log4js'

log4js.configure({
  appenders: {
    output: {
      type: 'file',
      filename: 'logs/common.log',
      maxLogSize: '10M', //  K, M, G
      backups: 0,
      compress: false,

      layout: {
        type: 'pattern',
        pattern: '[%d{yyyy-MM-dd hh:mm:ss}] [%p] %f{1} line-%l: %m',
      },
    },
  },
  categories: {
    default: {
      appenders: ['output'],
      level: 'debug',
      enableCallStack: true,
    },
  },
})

export const logger = log4js.getLogger()
