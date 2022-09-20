import * as log4js from 'log4js'
import * as path from 'path'
import globalConfig from '../config'
log4js.configure({
  appenders: {
    console: {
      type: 'console'
    },
    file: {
      type: 'file',
      filename: path.resolve(__dirname, `../../logs/iyo-server.log`),
      maxLogSize: 1024 * 1024 * 10, // 10M
      backups: 5
    }
  },
  categories: {
    default: {
      appenders: globalConfig.log.appenders,
      level: globalConfig.log.level
    }
  },
  pm2: true
})
const logger = log4js.getLogger()
export default logger