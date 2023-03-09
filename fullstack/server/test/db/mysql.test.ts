import { describe, expect, test, jest } from '@jest/globals';
import { getValue, setValues } from '../../db/index'
import { getShortLink ,insertLongLink} from '../../db'
import parseShortLinkFromBody from '../../utils/parseShortLinkFromBody'

import { storedData } from '../store'
 
describe('数据库', () => {
  const fn = jest.fn()
  describe('获取数据操作', () => {
    test('获取数据操作-成功', async () => {
      const data = await getValue(getShortLink(storedData.short_link), storedData.short_link, fn)
      expect(data.long_link).toBe(storedData.long_link)
    })

    test('获取数据操作-失败', async () => {
      const short_link = 'https://www.baidu.com/aaaaaaa'
      const data = await getValue(getShortLink(short_link), short_link, fn)
      expect(data).toBeNull();
    })
  })

  test('存储数据操作', async () => {
    const long_link = `https://www.baidu.com/aa?aaa=bbb&ccc=jfiodsjfiosjfiosdjfdsiojfiosjfsoidjfsdiojfiosdjfiosdjfiosdjfoidsjf*7897892^&^&*%*&&^*^dddeeecccsss&bbb=${new Date().getTime()}`
    const { short_link } = parseShortLinkFromBody({ longLink: long_link })
    
    const fn = jest.fn();
    const data = await setValues(insertLongLink(short_link, long_link), short_link, long_link, fn)
    console.log(data, 'data')
    expect(data.long_link).toBe(long_link);
    expect(data.short_link).toBe(short_link);
  })
})