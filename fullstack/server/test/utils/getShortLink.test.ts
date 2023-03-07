import {describe, expect, test} from '@jest/globals';
import parseShortLinkFromBody from '../../utils/getShortLink'
import { body } from '../store'

describe('utils', () => {


  const shortLink = encodeURIComponent('https://www.baidu.com/c4dde095')
  test('getShortLink', () => {
    expect(parseShortLinkFromBody(body).shortLink).toBe(shortLink)
    expect(parseShortLinkFromBody(body).longLink).toBe(encodeURIComponent(body.longLink))
  })
})