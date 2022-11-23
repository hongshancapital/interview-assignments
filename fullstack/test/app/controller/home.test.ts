import assert from 'assert';
import { app } from 'egg-mock/bootstrap';

describe('test/app/controller/home.test.ts', () => {

  it('Get and check the short-link-key', async () => {
    const url = 'https://localhost:1234/xxxx?radmon=' + Math.random();
    const shortLinkRes = await app.httpRequest().get('/short-link/create?url=' + url).expect(200);
    assert(shortLinkRes.body.status === true);

    const key = shortLinkRes.body.data.key;
    assert(!!key);
    assert(typeof key === 'string');
    assert(key.length === 8);

    const redirectRes = await app.httpRequest().get('/' + key).expect(302);
    assert(redirectRes.text === `Redirecting to <a href=\"${url}\">${url}</a>.`);
  });
});
