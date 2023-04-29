import { Test, TestingModule } from '@nestjs/testing';
import { INestApplication } from '@nestjs/common';
import { AppModule } from './app.module';
import * as request from 'supertest';
import { StatusCode } from './short-url/model/status_code';

describe('AppController', () => {
    let app: INestApplication;

    beforeAll(async () => {
        const moduleFixture: TestingModule = await Test.createTestingModule({
            imports: [AppModule],
        }).compile();

        app = moduleFixture.createNestApplication();
        await app.init();
    });

    afterAll(async () => {
        await app.close();
    });

    describe('/create-short-url', () => {
        it('should return Params is null if long url is null', async () => {
            const longUrl = '';
            const response = await request(app.getHttpServer())
                .post('/api/short-url/create-short-url')
                .send({ longUrl })

            expect(response.body.statusCode).toEqual(StatusCode.ParamsIsNull);
        });

        it('should return invalid long url if long url is valid format', async () => {
            const longUrl = 'fdsafasfm';
            const response = await request(app.getHttpServer())
                .post('/api/short-url/create-short-url')
                .send({ longUrl })

            expect(response.body.statusCode).toEqual(StatusCode.ParamsInvalid);
        });

        it('should create a short url from a long url', async () => {
            const longUrl = 'https://www.nestjs.com';
            const response = await request(app.getHttpServer())
                .post('/api/short-url/create-short-url')
                .send({ longUrl })
                .expect(201);

            expect(response.body.statusCode).toEqual(StatusCode.Success);
            expect(response.body.result).toMatch(/https:\/\/short-url\.com\/[a-zA-Z0-9]{8}/);
        });
    });

    describe('/get-long-url', () => {
        it('should return the long url associated with a given short url', async () => {
            const longUrl = 'https://www.nestjs.com';
            const { body } = await request(app.getHttpServer())
                .post('/api/short-url/create-short-url')
                .send({ longUrl });

            const response = await request(app.getHttpServer())
                .get('/api/short-url/get-long-url')
                .query({ shortUrl: body.result })
                .expect(200);

            expect(response.body.statusCode).toEqual(StatusCode.Success);
            expect(response.body.result).toEqual(longUrl);
        });

        it('should return "Not found" if no long url is associated with the given short url', async () => {
            const shortUrl = 'www.hello.com';
            const response = await request(app.getHttpServer())
                .get('/api/short-url/get-long-url')
                .query({ shortUrl })
                .expect(200);

            expect(response.body.statusCode).toEqual(StatusCode.NotFound);
        });
    });
});
