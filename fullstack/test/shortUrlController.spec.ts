import 'mocha'
import request from 'supertest'
import { expect } from 'chai'
const localServer = 'http://127.0.0.1:3000'

describe('短码服务测试', () => {
  it('POST /generate body为空', async () => {
    const response = await request(localServer)
      .post('/generate')
      .set('accept', 'application/json')
      .send()
    expect(response.headers['content-type']).to.match(/json/)
    expect(response.status).to.equal(200)
    expect(response.body.code).to.equal(10001)
  })
  it('POST /generate originalUrl为空字符串', async () => {
    const response = await request(localServer)
      .post('/generate')
      .set('accept', 'application/json')
      .send({
        originalUrl: '',
      })
    expect(response.headers['content-type']).to.match(/json/)
    expect(response.status).to.equal(200)
    expect(response.body.code).to.equal(10001)
  })
  it('POST /generate originalUrl为null', async () => {
    const response = await request(localServer)
      .post('/generate')
      .set('accept', 'application/json')
      .send({
        originalUrl: null,
      })
    expect(response.headers['content-type']).to.match(/json/)
    expect(response.status).to.equal(200)
    expect(response.body.code).to.equal(10001)
  })
  it('POST /generate originalUrl为undefined', async () => {
    const response = await request(`${localServer}`)
      .post('/generate')
      .set('accept', 'application/json')
      .send({
        originalUrl: undefined,
      })
    expect(response.headers['content-type']).to.match(/json/)
    expect(response.status).to.equal(200)
    expect(response.body.code).to.equal(10001)
  })
  it('POST /generate 非法url', async () => {
    const response = await request(`${localServer}`)
      .post('/generate')
      .set('accept', 'application/json')
      .send({
        originalUrl: 'baidu.com',
      })
    expect(response.headers['content-type']).to.match(/json/)
    expect(response.status).to.equal(200)
    expect(response.body.code).to.equal(10001)
  })
  it('POST /generate http常规url', async () => {
    const response = await request(`${localServer}`)
      .post('/generate')
      .set('accept', 'application/json')
      .send({
        originalUrl: 'http://baidu.com',
      })
    expect(response.headers['content-type']).to.match(/json/)
    expect(response.status).to.equal(200)
    expect(response.body.code).to.equal(200)
  })
  it('POST /generate https常规url', async () => {
    const response = await request(`${localServer}`)
      .post('/generate')
      .set('accept', 'application/json')
      .send({
        originalUrl: 'https://baidu.com',
      })
    expect(response.headers['content-type']).to.match(/json/)
    expect(response.status).to.equal(200)
    expect(response.body.code).to.equal(200)
    expect(response.body.data.isNew).to.equal(1)
    expect(response.body.data.shortUrlCode).to.be.a('string')
  })
  it('POST /generate 重复url', async () => {
    const repeatUrl = 'https://google.com'
    const responseFirst = await request(`${localServer}`)
      .post('/generate')
      .set('accept', 'application/json')
      .send({
        originalUrl: repeatUrl,
      })
    const responseSecond = await request(`${localServer}`)
      .post('/generate')
      .set('accept', 'application/json')
      .send({
        originalUrl: repeatUrl,
      })
    expect(responseFirst.headers['content-type']).to.match(/json/)
    expect(responseFirst.status).to.equal(200)
    expect(responseFirst.body.code).to.equal(200)
    expect(responseFirst.body.data.isNew).to.equal(1)
    expect(responseFirst.body.data.shortUrlCode).to.be.a('string')
    expect(responseSecond.headers['content-type']).to.match(/json/)
    expect(responseSecond.status).to.equal(200)
    expect(responseSecond.body.code).to.equal(200)
    expect(responseSecond.body.data.isNew).to.equal(0)
    expect(responseSecond.body.data.shortUrlCode).to.be.a('string')
  })
  it('GET /geturl 不存在的6位短码', async () => {
    const response = await request(`${localServer}`)
      .get('/geturl?code=aaaaaa')
      .set('accept', 'application/json')
      
    expect(response.headers['content-type']).to.match(/json/)
    expect(response.status).to.equal(200)
    expect(response.body.code).to.equal(10003)
  })
  it('GET /geturl 不存在的8位短码', async () => {
    const response = await request(`${localServer}`)
      .get('/geturl?code=bbbbbbbb')
      .set('accept', 'application/json')
      
    expect(response.headers['content-type']).to.match(/json/)
    expect(response.status).to.equal(200)
    expect(response.body.code).to.equal(10002)
  })
  it('GET /geturl 短码长度大于8', async () => {
    const response = await request(`${localServer}`)
      .get('/geturl?code=fafsfdsfsfdsfsfs')
      .set('accept', 'application/json')
      
    expect(response.headers['content-type']).to.match(/json/)
    expect(response.status).to.equal(200)
    expect(response.body.code).to.equal(10003)
  })
  it('GET /geturl 短码参数不传递', async () => {
    const response = await request(`${localServer}`)
      .get('/geturl')
      .set('accept', 'application/json')
      
    expect(response.headers['content-type']).to.match(/json/)
    expect(response.status).to.equal(200)
    expect(response.body.code).to.equal(10003)
  })
  it('GET /geturl 短码参数为空串', async () => {
    const response = await request(`${localServer}`)
      .get('/geturl?code=')
      .set('accept', 'application/json')
      
    expect(response.headers['content-type']).to.match(/json/)
    expect(response.status).to.equal(200)
    expect(response.body.code).to.equal(10003)
  })
  it('GET /geturl 短码参数非法', async () => {
    const response = await request(`${localServer}`)
      .get('/geturl?code=aaaaaaaa&code=bbbbbbbb')
      .set('accept', 'application/json')
      
    expect(response.headers['content-type']).to.match(/json/)
    expect(response.status).to.equal(200)
    expect(response.body.code).to.equal(10003)
  })
  // generate接口创建短码，可通过返回的短码通过geturl接口查询到正确的原始url
  it('GET /geturl 创建合法短码并查询', async () => {
    const originalUrl = 'http://fsfsdfsfsfsfs.com'
    const getShortUrlCode = await request(`${localServer}`)
      .post('/generate')
      .set('accept', 'application/json')
      .send({
        originalUrl: 'http://fsfsdfsfsfsfs.com',
      })
    const response = await request(`${localServer}`)
      .get(`/geturl?code=${getShortUrlCode.body.data.shortUrlCode}`)
      .set('accept', 'application/json')
      
    expect(response.headers['content-type']).to.match(/json/)
    expect(response.status).to.equal(200)
    expect(response.body.code).to.equal(200)
    expect(response.body.data.originalUrl).to.equal(originalUrl)
  })
  // generate接口反复创建同一网址的短码，短码始终为第一次创建的值
  // geturl在可通过该短码查询到正确的原始url
  it('GET /geturl 同一网址反复创建短码验证', async () => {
    const originalUrl = 'http://ttttttttttttt.sx'
    const firstGenerate = await request(`${localServer}`)
      .post('/generate')
      .set('accept', 'application/json')
      .send({
        originalUrl,
      })
    const firstGet = await request(`${localServer}`)
      .get(`/geturl?code=${firstGenerate.body.data.shortUrlCode}`)
      .set('accept', 'application/json')
    const secondGenerate = await request(`${localServer}`)
      .post('/generate')
      .set('accept', 'application/json')
      .send({
        originalUrl,
      })
    const secondGet = await request(`${localServer}`)
      .get(`/geturl?code=${secondGenerate.body.data.shortUrlCode}`)
      .set('accept', 'application/json')
    expect(firstGenerate.body.data.shortUrlCode).to.equal(secondGenerate.body.data.shortUrlCode)
    expect(firstGet.body.data.originalUrl).to.equal(originalUrl)
    expect(secondGet.body.data.originalUrl).to.equal(originalUrl)
  })
})
