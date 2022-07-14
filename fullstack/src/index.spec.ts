import supertest from 'supertest'
import { RESPONSE_CODE } from './consts/response-code'
import { server } from './index'

const test_url = 'http://foo/bar'

describe('Encode API tests', () => {
  test('without param', async () => {
    await supertest(server).get('/shorturl/encode').expect({
      code: RESPONSE_CODE.ERROR_PARAMS_EMPTY,
      message: '参数不能为空',
    })
  })
  test('param over max length', async () => {
    await supertest(server)
      .get('/shorturl/encode')
      .query({ url_long: new Array(241).fill('a').join('') })
      .expect({
        code: RESPONSE_CODE.ERROR_LENGTH,
        message: '参数长度超长',
      })
  })
  test('success query', async () => {
    const res = await supertest(server)
      .get('/shorturl/encode')
      .query({ url_long: new Array(239).fill('a').join('') })
    expect(res.body.code).toEqual(RESPONSE_CODE.SUCCESS)
    expect(res.body.data['url_short'].length).toEqual(8)
  })
  test('duplicate query result same', async () => {
    const res1 = await supertest(server)
      .get('/shorturl/encode')
      .query({ url_long: test_url })
    const res2 = await supertest(server)
      .get('/shorturl/encode')
      .query({ url_long: test_url })
    expect(res1.body).toEqual(res2.body)
  })
})

describe('Decode API tests', () => {
  test('without param', async () => {
    await supertest(server).get('/shorturl/decode').expect({
      code: RESPONSE_CODE.ERROR_PARAMS_EMPTY,
      message: '参数不能为空',
    })
  })

  test('param length over', async () => {
    await supertest(server)
      .get('/shorturl/decode')
      .query({ url_short: new Array(9).fill('a').join('') })
      .expect({
        code: RESPONSE_CODE.ERROR_LENGTH,
        message: '参数长度不匹配',
      })
  })

  test('param length less', async () => {
    await supertest(server)
      .get('/shorturl/decode')
      .query({ url_short: new Array(7).fill('a').join('') })
      .expect({
        code: RESPONSE_CODE.ERROR_LENGTH,
        message: '参数长度不匹配',
      })
  })

  test('no result', async () => {
    const res = await supertest(server)
      .get('/shorturl/decode')
      .query({ url_short: 'aaaaaaaa' })
    expect(res.body.code).toEqual(RESPONSE_CODE.ERROR_NO_MATCHED)
  })

  test('query success', async () => {
    const encodeRes = await supertest(server)
      .get('/shorturl/encode')
      .query({ url_long: test_url })
    const test_short_url = encodeRes.body.data.url_short
    const res = await supertest(server)
      .get('/shorturl/decode')
      .query({ url_short: test_short_url })
    expect(res.body.code).toEqual(RESPONSE_CODE.SUCCESS)
    expect(res.body.data.url_long).toEqual(test_url)
  })
})

afterAll(() => {
  server.close()
})
