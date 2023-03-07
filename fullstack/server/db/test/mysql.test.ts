import { describe, expect, test, beforeAll, afterAll } from '@jest/globals';
import { query } from '../index'
import parseShortLinkFromBody from '../../utils/getShortLink'
import {getShortLink} from '../sql'

describe('mysql', () => {

  const storedData = {
    short_link: 'https://www.baidu.com/c4dde095',
    long_link: 'https://www.baidu.com/abcdeaaa=bbb&ccc=dddeeecccsss'
  }

  const newData = {
    longLink: 'https://www.baidu.com/abcdeaaa=bbb&ccc=dddeeecccsss',
  }

  test('getShortLink', async () => {
    const [err, data] = await query(getShortLink(storedData.short_link))
    expect(data).toHaveLength(1)
    expect(data[0].long_link).toBe(storedData.long_link)
  })
  
})