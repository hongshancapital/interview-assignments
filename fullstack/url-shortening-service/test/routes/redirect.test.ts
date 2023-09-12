import app from '../../src/app';
import * as mockDb from '../mock-db';
import supertest from 'supertest';

const request = supertest(app);

describe('Test request and response for the redirect API', () => {
    beforeAll(async () => {
        await mockDb.connect();
    });

    afterEach(async () => {
        await mockDb.clearDatabase();
    });

    afterAll(async () => {
        await mockDb.closeDatabase();
    });

    test('Request with the url code which not exists', async () => {
        const res = await request.get('/not_exists').send();
        const body = res.body;

        expect(res.statusCode).toBe(404);
        expect(body).toBe('No URL Found');
    });

    test('Request with the url code which exist', async () => {
        const requestBody = {
            originalUrl: 'https://www.google.com'
        };
        const resShortenUrl = await request.post('/shortenUrl').send(requestBody);
        const bodyShortenUrl = resShortenUrl.body;
        expect(resShortenUrl.statusCode).toBe(200);

        // Request with the url code
        const resRedirect = await request.get(`/${bodyShortenUrl.urlCode}`).send();
        expect(resRedirect.statusCode).toBe(302);
    });
})