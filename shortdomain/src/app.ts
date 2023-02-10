import { Request, Response, NextFunction } from 'express'
import express from 'express'
import linkRouter from './routes/shortlink'
import resp from './libs/res'
import code from './config/code'

const app = express()

app.use(express.json())
app.use(express.urlencoded({ extended: true }))
app.use((req: Request, res: Response, next: NextFunction) => {
    res.setHeader('Content-Type', 'application/json')
    next()
})
app.use('/links', linkRouter)

// catch 404
app.use((req: Request, res: Response) => {
    resp.jsonFail(res, 'not found', code.NOT_FOUND)
})

// catch error
app.use((err: any, req: Request, res: Response, next: NextFunction) => {
    resp.jsonFail(res, err.message || '系统错误', err.code || code.SYS_ERR)
})

export default app
