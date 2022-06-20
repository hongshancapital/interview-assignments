import * as chai from 'chai';
import chaiHttp = require('chai-http');

chai.use(chaiHttp);
const expect = chai.expect;

// This line allows to use http
process.env.NODE_TLS_REJECT_UNAUTHORIZED = '0';

const BASE_URL = `http://localhost`;
const CREATE_URL_PATH = '/v1/urls';

describe('shorten url tests', () => {
    const originalUrl = 'http://example.com/originalUrl';
    let shortUrl: string | undefined;

    it(`should generate a short url with a given url`, async () => {
        const response = await chai.request(BASE_URL)
            .post(CREATE_URL_PATH)
            .send({
               originalUrl: originalUrl,
            });
        expect(response.status).to.eq(201);
        expect(response.body.originalUrl).to.eq(originalUrl);
        expect(response.body.shortUrl).to.match(new RegExp(`^${BASE_URL}/[0-9a-zA-Z-_]{8}$`));
        shortUrl = response.body.shortUrl;
    });

    it(`should redirect to the original url by requesting the short url`, async () => {
        if (!shortUrl) {
            throw new Error(`short url should not be empty`);
        }
        const response = await chai.request('')
            .get(shortUrl)
            .redirects(0)
            .send();
        expect(response.status).to.eq(302);
        expect(response.header.location).to.eq(originalUrl);
    });
});
