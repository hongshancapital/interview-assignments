import logger from '../util/logger'
import { createResponse } from '../util/common'

/**
 * 全局错误捕获中间件
 * @param ctx
 * @param next
 */
export default async (ctx: any, next: any) => {
  try {
    await next()
  } catch (e: any) {
    logger.error(e.stack)
    ctx.set('Cache-Control', 'no-store')
    ctx.body = createResponse(null, -1, e.message)
  }
}
