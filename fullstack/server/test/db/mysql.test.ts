import { describe, expect, test, jest } from '@jest/globals';
import { getValue, setValues } from '../../db/index'
import { getShortLink } from '../../db'

import { storedData } from '../store'
 
describe('mysql', () => {
  const fn = jest.fn()
  test('get shortLink from mysql', async () => {
    const data = await getValue(getShortLink(storedData.short_link), storedData.short_link, fn)
    // expect(data).toHaveLength(1)
    expect(data.long_link).toBe(storedData.long_link)
  })
})