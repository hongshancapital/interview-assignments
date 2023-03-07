import {describe, expect, test} from '@jest/globals';
import parseShortLinkFromBody from '../getShortLink'
describe('aaaa', () => {
  const body = {
    longLink: 'https://www.baidu.com/abcde?aaa=bbb&ccc=dddeeecccsss',
  }

  const shortLink = encodeURIComponent('https://www.baidu.com/c4dde095')
  test('aaa', () => {
    expect(parseShortLinkFromBody(body).shortLink).toBe(shortLink)
    expect(parseShortLinkFromBody(body).longLink).toBe(encodeURIComponent(body.longLink))
  })
})