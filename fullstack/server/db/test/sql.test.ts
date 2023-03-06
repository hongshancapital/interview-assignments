import {describe, expect, test} from '@jest/globals';

import { getShortLink, insertLongLink}from '../sql'

describe('sql', () => {
  test('getShortLink', () => {
    const sql = getShortLink('https://www.baidu.com/860fc8d0')
    expect(sql).toBe("SELECT * FROM link_map_table WHERE short_link = 'https://www.baidu.com/860fc8d0'")
  })

  test('insertLongLink', () => {
    const sql = insertLongLink('https://www.baidu.com/860fc8d0', 'https://www.baidu.com/abcdef?aaa=bbb&ccc=dddeeecccss')
    expect(sql).toBe("INSERT INTO link_map_table (short_link,long_link) VALUES ('https://www.baidu.com/860fc8d0', 'https://www.baidu.com/abcdef?aaa=bbb&ccc=dddeeecccss' )")
  })
})
