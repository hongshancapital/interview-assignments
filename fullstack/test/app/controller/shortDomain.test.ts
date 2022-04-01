import * as assert from 'assert';
import {app} from 'egg-mock/bootstrap';

describe('test/app/controller/shortDomain.test.ts', () => {
  let shorturl = '';

  it('should GET /save/long/url', async () => {
    const str = 'https://www.bilibili.com/video/BV1TS4y1S75Q?spm_id_from=444.41.0.0%27';

    const result = await app.httpRequest().get('/save/long/url' + `?oriUrl=${str}`).expect(200);
    console.log(result);
    assert(result.body.code === 200);
    shorturl = result.body.data.shortUrl;
  });

  it('should GET /get/long/url/info', async () => {
    console.log('shorturl:' + shorturl);
    const result = await app.httpRequest().get('/get/long/url/info' + `?oriUrl=${shorturl}`).expect(200);
    assert(result.body.code === 200);
    console.log('oriUrl:' + result.body.data.oriUrl);
    assert(result.body.data.oriUrl !== '');
  });
});
