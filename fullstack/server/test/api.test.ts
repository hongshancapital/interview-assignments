import {describe, expect, test} from '@jest/globals';
import axios from 'axios'

import {storedData} from './store'
describe('接口测试（api.test.ts）', () => {
  test('通过短链获取长链', async () => {
    const response = await axios.get(`http://localhost:3000/shortLinkToLongLink?shortLink=${storedData?.short_link}`);
    expect(response?.data?.long_link).toBe(storedData?.long_link)
  })

  test('通过长链生成短链', async () => {
    const response = await axios.post(`http://localhost:3000/longLinkToShortLink`, {
      longLink: storedData?.long_link
    }, {
      headers: { 'content-type': 'application/x-www-form-urlencoded' },
    });

    expect(response.data.short_link).toBe(storedData?.short_link)
  })
})

