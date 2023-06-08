import app from '../../src/app';
import * as mockDb from '../mock-db';
import supertest from 'supertest';

const request = supertest(app);

describe('Test request and response for the getUrl API', () => {
    beforeAll(async () => {
        await mockDb.connect();
    });

    afterEach(async () => {
        await mockDb.clearDatabase();
    });

    afterAll(async () => {
        await mockDb.closeDatabase();
    });

    test('Request without any parameter', async () => {
        const res = await request.get('/getUrl').send();
        const body = res.body;

        expect(res.statusCode).toBe(404);
        expect(body).toBe('No URL Found');
    });

    test('Request with the url code not exists', async () => {
        const res = await request.get('/getUrl/not_exists').send();
        const body = res.body;

        expect(res.statusCode).toBe(404);
        expect(body).toBe('No URL Found');
    });

    test('Request with the url code', async () => {
        const requestBody = {
            originalUrl: 'https://www.google.com'
        };
        const resShortenUrl = await request.post('/shortenUrl').send(requestBody);
        const bodyShortenUrl = resShortenUrl.body;
        expect(resShortenUrl.statusCode).toBe(200);

        // Request with the url code
        const resGetUrl = await request.get(`/getUrl/${bodyShortenUrl.urlCode}`).send();
        const bodyGetUrl = resGetUrl.body;

        expect(resGetUrl.statusCode).toBe(200);
        expect(bodyGetUrl).toHaveProperty('shortUrl');
        expect(bodyGetUrl).toHaveProperty('urlCode');
        expect(bodyGetUrl).toHaveProperty('originalUrl');
        expect(bodyGetUrl).toHaveProperty('createDate');

        expect(bodyGetUrl.originalUrl).toEqual(requestBody.originalUrl);
    });
})