import { Middleware } from 'koa'
import jwt from 'koa-jwt'
import config from 'config'

export default (opts?): Middleware => {
  return jwt({
    secret: process.env.jwtSecret ?? config.get<string>('jwtSecret'),
    getToken: (ctx: any) => ctx.header.authorization
  }).unless({
    path: [
      /^\/[^/]*\/?$/,
      /test\/(?!auth)/
    ]
  })
}
