import {describe, expect, test} from '@jest/globals';

import { getShortLink, insertLongLink } from '../../db/sql'

import { translateFunc} from '../../db/index'
import {storedData} from '../store'

describe('sql管理', () => {
  test('根据短链，生成获取数据的sql', () => {
    expect(getShortLink(storedData.short_link)).toBe(`SELECT * FROM link_map_table WHERE short_link = '${translateFunc.encode(storedData.short_link)}'`)
  })

  test('向数据库中插入长链和短链', () => {
    const sql = insertLongLink(storedData.short_link, storedData.long_link)
    expect(sql).toBe(`INSERT INTO link_map_table (short_link,long_link) VALUES ('${translateFunc.encode(storedData.short_link)}', '${translateFunc.encode(storedData.long_link)}')`)
  })
})
