import { createResponse, to62Str, validateUrl } from '../src/util/common'
import {
  EMPTY_GUARD,
  getLongByShortFromCache,
  getLongUrlByShort,
  getShortUrlByLong,
  shortenUrlAndSave,
} from '../src/service/url'
import ShortUrlMap from '../src/model/ShortUrlMap'

describe('测试10进制转62进制', () => {
  it('测试10进制转62进制', () => {
    expect(to62Str(10)).toBe('a')
    expect(to62Str(9)).toBe('9')
    expect(to62Str(62)).toBe('10')
    expect(to62Str(63)).toBe('11')
  })
})

describe('测试url校验', () => {
  it('能识别正确的url', () => {
    expect(validateUrl('http://www.baidu.com')).toBeTruthy()
    expect(validateUrl('http://www.baidu.com/abc')).toBeTruthy()
  })

  it('能校验出错误的url', () => {
    expect(validateUrl('baidu.com')).toBeFalsy()
    expect(validateUrl('123')).toBeFalsy()
    expect(validateUrl(Array(2000).fill('a').join(''))).toBeFalsy()
  })
})

describe('测试url转换', () => {
  const longUrl = 'http://localhost:3000/url/stableFunc?a=1'
  beforeAll(async () => {
    await ShortUrlMap.destroy({
      where: {
        long_url: longUrl,
      },
    })
  })
  it('测试长 url 转短 url，持久化成功', async () => {
    const shortPath = await shortenUrlAndSave(longUrl)
    expect(shortPath).toBe('2LHv64')

    //映射关系已存库
    const dbShortPath = await getShortUrlByLong(longUrl)
    expect(dbShortPath).toBe(shortPath)

    const exist = await ShortUrlMap.findOne({
      where: {
        short_url: shortPath,
      },
    })
    //存库成功
    expect(exist).toBeTruthy()

    //能从短网址查询到原始长网址
    const dbLongPath = await getLongUrlByShort(shortPath!)
    expect(dbLongPath).toBe(longUrl)

    //最近的查询会保存在缓存
    const cacheLongUrl = getLongByShortFromCache(shortPath!)
    expect(cacheLongUrl).toBe(longUrl)
  })

  it('通过无效的短网址来查询，会得到无效值', async () => {
    const result = await getLongUrlByShort('11111')
    expect(result).toBe('')
  })

  describe('测试响应结果', () => {
    it('测试响应', () => {
      const res = createResponse(1, 0, 'success')
      // @ts-ignore
      expect(res.data).toBe(1)
      expect(res.status).toBe(0)
      expect(res.msg).toBe('success')
    })
  })
})
