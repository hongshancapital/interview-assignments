import { app } from 'egg-mock/bootstrap';
import assert from 'assert';

const testUrl = 'http://interview/assignments.com';
let shortUrl = '';
let shortId = '';

describe('test/app/controller/siteController.test.ts', async () => {
    before(async () => {
        app.ready();
    });

    it('shoud get /api/encodeUrl', async () => {
        const url = '/api/encodeUrl?url=' + testUrl;
        const result = await app.httpRequest().get(url).expect(200);
        const data = JSON.parse(result.text);
        assert(data.code === 0);
        shortUrl = data.data;
        shortId = shortUrl.split('/').pop() || '';
        assert(shortId && shortId.length === 4);
    });

    it('shoude get again /api/encodeUrl', async () => {
        const url = '/api/encodeUrl?url=' + shortUrl;
        const result = await app.httpRequest().get(url).expect(200);
        const data = JSON.parse(result.text);
        assert(data.code === 0);
        const dataInfo = data.data;
        assert(dataInfo === shortUrl);
    });

    it('should get decodeUrl /:id', async () => {
        const url = '/' + shortId;
        const result = await app.httpRequest().get(url).expect(200);
        const data = JSON.parse(result.text);
        assert(data.code === 0);
        const dataInfo = data.data;
        assert(dataInfo === testUrl);
    });
});
