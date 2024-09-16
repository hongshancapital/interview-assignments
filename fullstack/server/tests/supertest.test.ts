import request from 'supertest'
import createApp from '../src/app'



describe('supertest', () => {
  it('GET /long2short', async () => {
    const app = await createApp()
    await request(app)
      .get('/long2short?url=http://example.com/xyz')
      .expect(200)
      .expect(res => {
        expect(res.body.success).toBe(true)
        expect(res.body.message).toBe('ok')
      })
  })

  it('GET /long2short query exists', async () => {
    const app = await createApp()
    await request(app)
      .get('/long2short?url=http://example.com/abc')
      .expect(200, {
        success: true,
        message: 'ok',
        data: { short: '0t7lfuZ8' }
      })
  })

  it('GET /short2long', async () => {
    const app = await createApp()
    await request(app)
      .get('/short2long?short=0t7lfuZ8')
      .expect(200, {
        success: true,
        message: 'ok',
        data: { url: 'http://example.com/abc' }
      })
  })
})
