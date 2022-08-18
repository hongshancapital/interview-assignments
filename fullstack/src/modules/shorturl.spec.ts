import { RESPONSE_CODE } from '../consts/response-code'
import ShortUrl, { generateShortId } from './shorturl'

describe('Module ShortUrl Test', () => {
  test('generate id', () => {
    expect(generateShortId().length).toEqual(8)
  })

  test('encode new record', async () => {
    const res = await ShortUrl.encode(Date.now() + '')
    expect(res.code).toEqual(RESPONSE_CODE.SUCCESS)
  })
})
