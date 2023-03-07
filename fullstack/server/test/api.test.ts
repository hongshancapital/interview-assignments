import {describe, expect, test} from '@jest/globals';
import axios from 'axios'

import {storedData} from './store'
describe('api test', () => {
  test('shortLinkToLongLink', async () => {
    const response = await axios.get(`http://localhost:3000/shortLinkToLongLink?shortLink=${storedData?.short_link}`);
    expect(response?.data?.long_link).toBe(storedData?.long_link)
  })

  test('longLinkToShortLink', async () => {
    const response = await axios.post(`http://localhost:3000/longLinkToShortLink`, {
      longLink: storedData?.long_link
    }, {
      headers: { 'content-type': 'application/x-www-form-urlencoded' },
    });

    expect(response.data.short_link).toBe(storedData?.short_link)
  })
})

