import express, { Application, Response, Request, NextFunction } from "express"
import cookieParse from "cookie-parser"
import router from "./routes/index"
import * as uuid from "uuid"
const app: Application = express()
declare module "express-serve-static-core" {
  interface Request {
    reqId?: string
  }
  interface Response {
    reqId?: string
  }
}

app.use(express.json())
app.use(express.urlencoded({ extended: false }))
app.use(cookieParse())
app.use(function (req: Request, res, next) {
  const reqId = uuid.v1()
  req.reqId = reqId
  res.reqId = reqId
  next()
})


app.use("/l", router.linkRouter) // 短链跳转

app.use("/man", router.setRouter) // 管理类接口

app.use(function (req, res, next) {
  res.status(404).send("URL NOT FOUND")
})
app.use(function (err: Error, req: Request, res: Response, next: NextFunction) {
  console.error(err.stack)
  res.status(500).send("Something broke!")
})

export default app
