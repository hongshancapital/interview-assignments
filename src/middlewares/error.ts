import { Middleware, Context } from 'koa'

export default (opts?): Middleware => {
  return async (ctx, next) => {
    try {
      await next()
    } catch (e) {
      switch (e.status) {
        case 401:
          ctx.body = { error: e.originalError ? e.originalError.message : e.message }
          break
        default:
          ctx.body = e.stack || e.message
      }
    }
  }
}
