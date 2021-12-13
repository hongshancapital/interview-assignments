import supertest from 'supertest'
import app, { initialize } from '../../src/app'
import mongoClient from '../../src/components/database'
jest.mock('../../src/components/bloomFilter')
const createApi = (longUrl?: string) =>
  supertest(app)
    .post('/api/shortUrl/create')
    .set('Content-Type', 'application/json')
    .send({ longUrl })

const getLongUrlApi = (shortUrl?: string) =>
  supertest(app).get(`/api/shortUrl/getLongUrl?shortUrl=${shortUrl}`)

beforeAll(async () => {
  await initialize()
})
afterAll(async () => {
  mongoClient?.close()
})

describe('shorturl create', () => {
  test('invalid longUrl input return 400', async () => {
    const res = await createApi()
    expect(res.statusCode).toEqual(400)
  })

  test('base create url', async () => {
    const res = await createApi(`http://longUrl/1`)
    expect(res.statusCode).toEqual(200)
    expect(res.body).toHaveProperty('shortUrl')
  })
})

describe('shorturl getLongUrl', () => {
  test('invalid shortUrl input should return statusCode 400', async () => {
    const res = await getLongUrlApi('')
    expect(res.statusCode).toEqual(400)
  })
  test('not exists shortUrl should return statusCode 404', async () => {
    const res = await getLongUrlApi('http://longUrl/not-exists')
    expect(res.statusCode).toEqual(404)
  })

  test('create and getLongUrl ', async () => {
    const longUrl = `http://longUrl/create-and-getLongUrl`
    const res = await createApi(longUrl)
    const { shortUrl } = res.body
    const res1 = await getLongUrlApi(shortUrl)
    expect(res1.body.longUrl).toEqual(longUrl)
  })
})
