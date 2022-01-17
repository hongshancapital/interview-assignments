import * as express from 'express'
import config from './config'
import api from './controller/index'
import * as bodyParser from 'body-parser'
import * as expressSession from 'express-session'
import errorHandle from './middleware/errorHandle'
import logger from './utils/logger'
import notFound from './middleware/notFound'
import * as cookieParser from 'cookie-parser'
import './data/pool'
import * as http from 'http'
import * as ConnectRedis from 'connect-redis'
import redisClient from './utils/redis'
import globalConfig from './config'
(async (): Promise<void> => {
  const app = express()
  const client = await redisClient.getClient(globalConfig.redis.default)

  // 跨域设置
  app.use(function (req, res, next) {
    res.header('Access-Control-Allow-Origin', req.headers.origin || '*')
    res.header('Access-Control-Allow-Headers', req.headers['access-control-request-headers'])
    res.header('Access-Control-Allow-Credentials', 'true')
    res.header('Access-Control-Allow-Methods', req.method)//设置方法
    if (req.method === 'OPTIONS') {
      logger.info('got request in cross origin(OPTIONS): ', req.path)
      res.sendStatus(204)
    } else {
      logger.info('got request in cross origin: ', req.path)
      next()
    }
  })

  app.use(cookieParser())
  const RedisStore = ConnectRedis(expressSession)
  const session = expressSession({
    store: new RedisStore({
      client: client.client,
      prefix: 'node-session:'
    }),
    resave: false,
    saveUninitialized: false,
    secret: 'nodejs'
  })
  app.use(session)
  app.use(bodyParser.urlencoded({
    extended: false
  }))
  app.use(bodyParser.json())

  app.use(config.prefix.api, api)
  app.use(errorHandle)
  app.use(notFound)

  logger.info('socket服务开始初始化')
  const server = new http.Server(app)
  logger.info('socket服务初始化完成')
  server.listen(config.port, () => {
    logger.info(`listen on port ${config.port};\nclick http://localhost:${config.port} to visit server;`)
  })
})()