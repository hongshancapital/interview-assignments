/**
 * testServer.ts
 * @authors lizilong
 * @description 测试服务文件
 */

import request from 'supertest'
import server from './app'

export default request(server)
