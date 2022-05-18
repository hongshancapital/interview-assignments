// 对api接口测试需要使用supertest，使用expert断言，supertest是一个非常好的适用于node的模拟http请求的库
import request from 'supertest'
// 引入产生请求的程序入口app.js
import server from './app'

export default request(server)
