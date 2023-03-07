import { describe, expect, test, jest } from '@jest/globals';
import { query } from '../../db/index'
import { getShortLink } from '../../db'

import { storedData } from '../store'
 
describe('mysql', () => {
  const fn = jest.fn()
  test('get shortLink from mysql', async () => {
    const [err, data] = await query(getShortLink(encodeURIComponent(storedData.short_link)),fn)
    expect(data).toHaveLength(1)
    expect(data[0].long_link).toBe(encodeURIComponent(storedData.long_link))
  })

  test('execute error sql', async () => {
    const [err, data] = await query('error sql', fn)
    expect(data).toBeNull()
    expect(fn.mock.calls).toHaveLength(1)
  })
  
})