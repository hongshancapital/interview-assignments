const path = require('path')
const express = require('express')
const bodyParser = require('body-parser')
const cookieParser = require('cookie-parser')
const favicon = require('serve-favicon')
const cors = require('cors')

const app = express()
app.disable('x-powered-by')
app.disable('etag')

app.use(cors())
app.options("*", cors()) // todo 跨域处理
app.use(bodyParser.json({ limit: '2mb' }))
app.use(bodyParser.urlencoded({
  limit: '2mb',
  extended: true
}))
app.use(cookieParser())

// 网站的logo
// app.use(favicon(path.join(__dirname, '../../client/static/images/ai-mid.png')))
require('../middlewares')(app)

// 设置静态文件目录，有NGINX代理情况下，应该优先用NGINX
// app.use(express.static(path.join(__dirname, '../../client/dist'), {
//   maxAge: 1000
// }))

const server = require('http').createServer(app)
server.timeout = 180 * 1000

server.listen(Config.port, Config.address, function (err) {
  if (err) {
    console.error(err)
  }
})

_.assign(spaces.network, {
  express,
  app,
  server
})

function Server() {
}

Server.prototype.onRunning = function() {
  console.log(`Server running at port ${Config.port}`)
}

module.exports = Server
