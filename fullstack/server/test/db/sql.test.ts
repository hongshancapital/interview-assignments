import {describe, expect, test} from '@jest/globals';

import { getShortLink, insertLongLink } from '../../db/sql'

import {storedData} from '../store'

describe('sql format', () => {
  test('getShortLink', () => {
    expect(getShortLink(storedData.short_link)).toBe(`SELECT * FROM link_map_table WHERE short_link = '${storedData.short_link}'`)
  })

  test('insertLongLink', () => {
    const sql = insertLongLink(storedData.short_link, storedData.long_link)
    expect(sql).toBe(`INSERT INTO link_map_table (short_link,long_link) VALUES ('${storedData.short_link}', '${storedData.long_link}')`)
  })
})
