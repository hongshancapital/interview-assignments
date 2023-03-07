import {describe, expect, test} from '@jest/globals';
import axios from 'axios'
import qs from 'qs';

import {storedData} from './store'
describe('api test', () => {
  test('shortLinkToLongLink', async () => {
    const response = await axios.get(`http://localhost:3000/shortLinkToLongLink?shortUrl=${storedData?.short_link}`);
    expect(response?.data?.long_link).toBe(storedData?.long_link)
  })

  test('longLinkToShortLink', async () => {

    const response: any = await axios({
      method: 'post',
      url: 'http://localhost:3000/longLinkToShortLink',
      headers: { 'content-type': 'application/x-www-form-urlencoded' },
      data:  {
        longLink: storedData?.long_link
      },
    });
    // const response = await axios.post(`http://localhost:3000/longLinkToShortLink`, {
    //   longLink: storedData?.long_link
    // });

    expect(response.data.short_link).toBe(storedData?.short_link)

  })
})
