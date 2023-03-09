import {describe, expect, test} from '@jest/globals';

import { getShortLink, insertLongLink } from '../../db/sql'

import { translateFunc} from '../../db/index'
import {storedData} from '../store'

describe('sql format', () => {
  test('getShortLink', () => {
    expect(getShortLink(storedData.short_link)).toBe(`SELECT * FROM link_map_table WHERE short_link = '${translateFunc.encode(storedData.short_link)}'`)
  })

  test('insertLongLink', () => {
    const sql = insertLongLink(storedData.short_link, storedData.long_link)
    expect(sql).toBe(`INSERT INTO link_map_table (short_link,long_link) VALUES ('${translateFunc.encode(storedData.short_link)}', '${translateFunc.encode(storedData.long_link)}')`)
  })
})
