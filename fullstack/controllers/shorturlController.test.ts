import request from 'supertest'
import app from '../app'

describe('testing ETF Controller', () => {
  test('get correct ETF', async () => {
    const res = await request(app).get('/short')
    expect(res.statusCode).toBe(200)
    expect(res.body).toEqual({ status: 'error', message: "Your access token is invalid" })
  })
})
