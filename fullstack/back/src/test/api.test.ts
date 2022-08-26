import request from 'supertest'
import {Express} from 'express-serve-static-core' 
import {createServer} from '../util/server';
import '../db/mongo';
let server: Express

beforeAll(async () => {
  server = await createServer()
})
describe('url adjust POST /', () => {
    test("情况1: 短链接 + 无缓存", async () => {
        const res = await request(server)
                .post('/')
                .set('Content-Type', 'application/json')
                .send(JSON.stringify({ search: "http://short" }))
        expect(res.status).toEqual(200)
    })

    test("情况2: 长链接 + 无缓存", async () => {
        const res = await request(server)
                .post('/')
                .set('Content-Type', 'application/json')
                .send(JSON.stringify({ search: "http::/www.huang/iHK6CbniHK6CbniHK6CbniHK6CbniHK6Cbn" }))
        expect(res.status).toEqual(200)
    })

    test("情况3: 有缓存", async () => {
        const res = await request(server)
                .post('/')
                .set('Content-Type', 'application/json')
                .send(JSON.stringify({ search: "http::/www.huang/iHK6Cbni" }))
        expect(res.status).toEqual(200)
    })
})