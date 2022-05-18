/**
 * app.ts
 * @authors lizilong
 * @description 服务主文件
 */
import express from 'express'
import bodyParser from 'body-parser'
import connectDB from './config/db'

const app = express()

// 设置后Post接口方可获取到Body中的参数
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: false }))

// 连接MongoDB
connectDB()

// 设置端口
app.set('port', process.env.PORT || 3000)

// 统一的路由分发
var routes = require('./routes/index')
routes(app)

// 启动服务的时候监听端口号
app.listen(app.get('port'), () => {
	console.log('Server listening on port:', app.get('port'))
})

export default app
