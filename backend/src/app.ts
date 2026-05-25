import Koa from 'koa'
// @ts-ignore
import cors from '@koa/cors'
import router from './router'
import logger from './util/logger'
import errorMiddleware from './middleware/errorHandler'
import './model/db'

const bodyParser = require('koa-bodyparser')
const PORT = '3000'
//全局异常捕获
process.on('uncaughtException', function (err) {
  logger.error(`UncaughtException : ${err && err.stack}`)
})
process.on('unhandledRejection', function (err: any) {
  logger.error(`unhandledRejection : ${err && err.stack}`)
})

logger.info('starting app')
const koaApp = new Koa()
koaApp.use(cors())
koaApp.use(errorMiddleware)
koaApp.use(
  bodyParser({
    jsonLimit: '10mb',
  })
)
koaApp.use(router.routes())
// @ts-ignore
koaApp.listen(PORT, '0.0.0.0', () => {
  logger.info(`listening port ${PORT}`)
})

koaApp.on('error', (err: any, ctx: any) => {
  logger.error(`server error ${err} ${ctx}`)
})
