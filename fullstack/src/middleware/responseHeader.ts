import { Request, Response, NextFunction } from 'express'

const responseHeader = (req: Request, res: Response, next: NextFunction) => {
  const { origin, Origin, referer, Referer } = req.headers

  // 若没有手动设置，则为通配符
  const allowOrigin = origin || Origin || referer || Referer || '*'

  // 允许请求源
  res.header('Access-Control-Allow-Origin', allowOrigin)
  // 允许头部字段
  res.header('Access-Control-Allow-Headers', 'Content-Type')
  // 允许公开的头部字段
  res.header('Access-Control-Expose-Headers', 'Content-Disposition')
  // 允许的请求方式
  res.header('Access-Control-Allow-Methods', 'PUT,POST,GET,DELETE,OPTIONS')
  // 允许携带cookie
  res.header('Access-Control-Allow-Credentials', 'true')

  // 预检返回204
  if (req.method == 'OPTIONS') {
    res.sendStatus(204)
  } else {
    next()
  }
}

export default responseHeader
