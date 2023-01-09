import express, { Request, Response, NextFunction }  from 'express'
import morgan from 'morgan'
import  { initDatabase } from './db/connection'
import { generateShortUrl, responseShortUrl } from './controller/shortUrlController'
import 'express-async-errors'

const port = 3000
const app = express()
app.use(express.json())
app.use(morgan('combined'))
app.post('/generate', generateShortUrl)
app.get('/geturl', responseShortUrl)
app.use(function (err: Error, req: Request, res: Response, next: NextFunction) {
  res.status(500)
  res.json({ error: err.message })
  next(err)
})

initDatabase().then(() => {
  console.log('数据库初始化成功。')
  app.listen(port, () => {
    console.log(`服务已启动，监听端口为 ${port}`)
  })
}).catch(err => {
  console.error('服务启动失败。数据库初始化失败:', err)
})
