import {describe, expect, test} from '@jest/globals';
import axios from 'axios'
import mock from 'mockjs'
import parseShortLinkFromBody from '../utils/getShortLink'

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


// describe('自动化测试', () => {
  // test('测试随机生成链接', async () => {
  //   for (let i = 0; i < 100; i++) {
  //     const link = mock.Random.url('http');
  //     const { shortLink, longLink } = parseShortLinkFromBody({ longLink: link })
  //     const response = await axios.post(`http://localhost:3000/longLinkToShortLink`, {
  //           longLink: link
  //         }, {
  //           headers: { 'content-type': 'application/x-www-form-urlencoded' },
  //     });
  //     expect(response?.data?.shortLink).toBe(decodeURIComponent(shortLink))
  //   }
  // })

  // test('测试生成长链接', async () => {
  //   for (let i = 0; i < 10; i++){
      
  //     const link = mock.Random.url('http') + mock.mock({
  //       'regexp|1-200': /[a-z][A-Z][0-9]/
  //     });
  
  //     const { shortLink } = parseShortLinkFromBody({ longLink: link })
  //     const response = await axios.post(`http://localhost:3000/longLinkToShortLink`, {
  //           longLink: link
  //         }, {
  //           headers: { 'content-type': 'application/x-www-form-urlencoded' },
  //     });
  //     expect(response?.data?.shortLink).toBe(decodeURIComponent(shortLink))
  //   }
  // })
// })
