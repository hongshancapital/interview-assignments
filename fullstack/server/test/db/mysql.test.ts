import { describe, expect, test, beforeAll, afterAll } from '@jest/globals';
import { query } from '../../db/index'
import { getShortLink } from '../../db'

import { storedData } from '../store'
 
describe('mysql', () => {

  test('get shortLink from mysql', async () => {
    const [err, data] = await query(getShortLink(encodeURIComponent(storedData.short_link)))
    expect(data).toHaveLength(1)
    expect(data[0].long_link).toBe(encodeURIComponent(storedData.long_link))
  })

  test('excute error sql', async () => {
    const [err, data] = await query('error sql')
    expect(data).toBeNull()
  })
  
})