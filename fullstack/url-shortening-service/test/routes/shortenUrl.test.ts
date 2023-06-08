import app from '../../src/app';
import * as mockDb from '../mock-db';
import supertest from 'supertest';

const request = supertest(app);

describe('Test request and response for the shortenUrl API', () => {
    beforeAll(async () => {
        await mockDb.connect();
    });

    afterEach(async () => {
        await mockDb.clearDatabase();
    });

    afterAll(async () => {
        await mockDb.closeDatabase();
    });

    test('Request shorten url API with the original url', async () => {
        const requestBody = {
            originalUrl: 'https://www.google.com'
        };
        const res = await request.post('/shortenUrl').send(requestBody);
        const body = res.body;

        expect(res.statusCode).toBe(200);

        expect(body).toHaveProperty('shortUrl');
        expect(body).toHaveProperty('urlCode');
        expect(body).toHaveProperty('originalUrl');
        expect(body).toHaveProperty('createDate');

        expect(body.originalUrl).toEqual(requestBody.originalUrl);
    });

    test('Request shorten url API with the duplicated original url', async () => {
        const requestBody = {
            originalUrl: 'https://www.google.com'
        };
        const resFirstTime = await request.post('/shortenUrl').send(requestBody);
        const bodyFirstTime = resFirstTime.body;
        expect(resFirstTime.statusCode).toBe(200);

        // Request with the duplicated original url
        const resSecondTime = await request.post('/shortenUrl').send(requestBody);
        const bodySecondTime = resSecondTime.body;
        expect(resSecondTime.statusCode).toBe(200);

        expect(bodySecondTime).toEqual(bodyFirstTime);
    });

    test('Request shorten url API without the original url', async () => {
        const requestBody = {
            otherProperty: 'otherProperty'
        };
        const res = await request.post('/shortenUrl').send(requestBody);
        const body = res.body;

        expect(res.statusCode).toBe(400);
        expect(body).toBe('originalUrl is a required field');
    });
})