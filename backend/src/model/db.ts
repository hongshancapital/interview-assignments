import Sequelize from 'sequelize'
import { dbConfig } from '../config/db'
import logger from '../util/logger'

const env = process.env.RUNTIME_ENV || 'dev'

// @ts-ignore
const config = dbConfig[env] || dbConfig.dev

// @ts-ignore
const db = new Sequelize(config.database, config.username, config.password, {
  ...config,
  retry: {
    match: [
      /ETIMEDOUT/,
      /EHOSTUNREACH/,
      /ECONNRESET/,
      /ECONNREFUSED/,
      /ETIMEDOUT/,
      /ESOCKETTIMEDOUT/,
      /EHOSTUNREACH/,
      /EPIPE/,
      /EAI_AGAIN/,
      /SequelizeConnectionError/,
      /SequelizeConnectionRefusedError/,
      /SequelizeHostNotFoundError/,
      /SequelizeHostNotReachableError/,
      /SequelizeInvalidConnectionError/,
      /SequelizeConnectionTimedOutError/,
    ],
    max: 5,
  },
  dialectOptions: {
    // useUTC: false,
    dateStrings: true,
    typeCast(field: any, next: any) {
      // for reading from database
      if (field.type === 'DATETIME') {
        return field.string()
      }
      return next()
    },
    connectTimeout: 20000, // default is 10s which causes occasional ETIMEDOUT errors (see https://stackoverflow.com/a/52465919/491553)
  },
  timezone: '+08:00',
  pool: {
    max: 30,
    min: 0,
    acquire: 30000,
    idle: 10000,
  },
})

db.authenticate()
  .then(() => {
    logger.info('Connection has been established successfully.')
  })
  .catch((err: any) => {
    logger.error(`Unable to connect to the database:${err.message + err.stack}`)
  })

export default db
