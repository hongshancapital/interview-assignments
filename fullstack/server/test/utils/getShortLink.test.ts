import {describe, expect, test, jest} from '@jest/globals';
import parseShortLinkFromBody from '../../utils/parseShortLinkFromBody'
import { body } from '../store'

describe('utils', () => {
  const shortLink = 'https://www.baidu.com/c4dde095'
  test('getShortLink', () => {
    expect(parseShortLinkFromBody(body).short_link).toBe(shortLink)
    expect(parseShortLinkFromBody(body).long_link).toBe(body.longLink)
  })
})