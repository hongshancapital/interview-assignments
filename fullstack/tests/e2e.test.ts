import supertest from 'supertest'
import { App } from '../src/app'

const app = new App().start()

const validURL = 'http://www.google.com'

describe('POST /api/v1/short', () => {
  it('add an unvalid URL', async () => {
    const res = await supertest(app)
      .post('/api/v1/short')
      .send({
        longURL: 'unvalid url'
      })

      expect(res.statusCode).toBe(400)
      expect(res.body.code).not.toBe(200)
  })

  it('add valid URL', async () => {
    const res = await supertest(app)
      .post('/api/v1/short')
      .send({
        url: validURL
      })

      expect(res.statusCode).toBe(200)
      expect(res.body.code).toBe(200)
  })

  it('repeat add valid URL', async () => {
    const res = await supertest(app)
      .post('/api/v1/short')
      .send({
        url: 'http://www.google.com'
      })

      expect(res.statusCode).toBe(200)
      expect(res.body.code).toBe(200)
  })
})

describe('GET /api/v1/:shortURL', () => {

  it('shortURL is invalid', async () => {
    const res = await supertest(app).get('/api/v1/++++')

    expect(res.statusCode).toBe(400)
    expect(res.body.code).not.toBe(200)
  })

  it('shortURL is valid and exist', async () => {
    const shortRes = await supertest(app)
      .post('/api/v1/short')
      .send({
        url: validURL
      })
    const short = shortRes.body.data.shortURL
    const res = await supertest(app).get(`/api/v1/${short}`)

    expect(res.statusCode).toBe(200)
    expect(res.body.code).toBe(200)
    expect(res.body.data.longURL).toBe(validURL)
  })

  it('record not found', async () => {
    const res = await supertest(app).get('/api/v1/sff')
    expect(res.statusCode).toBe(200)
    expect(res.body.code).toBe(10404)
  })
})