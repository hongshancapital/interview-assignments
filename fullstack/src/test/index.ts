// test.ts
import request from 'supertest';
import { expect } from 'chai';

import app from '../www';

describe('Shorten URL API', () => {
    it('should return short URL when long URL is provided', async () => {
        const res = await request(app)
            .post('/api/shorten')
            .send({ url: 'https://www.google.com/' });
        expect(res.status).to.equal(200);
        expect(res.body).to.have.property('shortUrl');
    });

    it('should redirect to long URL when short URL is provided', async () => {
        const res1 = await request(app)
            .post('/api/shorten')
            .send({ url: 'https://www.google.com/' });
        const res2 = await request(app).get(`/${res1.body.shortUrl}`);
        expect(res2.status).to.equal(302);
        expect(res2.header.location).to.equal('https://www.google.com/');
    });

    it('should return 404 when invalid short URL is provided', async () => {
        const res = await request(app).get('/invalid-short-url');
        expect(res.status).to.equal(404);
    });
});
