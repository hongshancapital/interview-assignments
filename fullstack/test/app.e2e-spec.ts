import { INestApplication, ValidationPipe } from '@nestjs/common';
import { FastifyAdapter, NestFastifyApplication } from '@nestjs/platform-fastify';
import { Test, TestingModule } from '@nestjs/testing';
import * as request from 'supertest';

import { AppModule } from '../src/app.module';

import * as fs from 'node:fs';

describe('AppController (e2e)', () => {
  const persistFilePath = `${process.cwd()}/test/__e2e__testAppController__db.json`;
  let app: INestApplication;
  const testUrl1 = 'https://test.example.com/a';
  let testUrl2 = testUrl1;
  for (let i = 2048; i--; ) {
    testUrl2 += Number(Math.floor(Math.random()) * 36).toString(36);
  }
  let shortUrl1: string;

  beforeAll(async () => {
    process.env.DB_PATH = persistFilePath;
    const moduleFixture: TestingModule = await Test.createTestingModule({
      imports: [AppModule],
    }).compile();
    delete process.env.DB_PATH;

    app = moduleFixture.createNestApplication<NestFastifyApplication>(new FastifyAdapter());
    app.enableShutdownHooks();
    app.enableCors();
    app.useGlobalPipes(new ValidationPipe());
    await app.init();
    await app.getHttpAdapter().getInstance().ready();
  });

  describe('/getShortUrl (GET)', () => {
    it('should return short url', async () => {
      const res = await request(app.getHttpServer()).get(`/getShortUrl?originalUrl=${testUrl1}`);
      expect(res.status).toEqual(200);
      shortUrl1 = res.body.shortUrl;
      expect(shortUrl1).toMatch(/^https:\/\/example\.com\/[a-zA-Z][0-9a-zA-Z]{7}$/);
    });
    it('should work with long url', async () => {
      const res = await request(app.getHttpServer()).get(`/getShortUrl?originalUrl=${testUrl2}`);
      expect(res.status).toEqual(200);
      expect(res.body.shortUrl).toMatch(/^https:\/\/example\.com\/[a-zA-Z][0-9a-zA-Z]{7}$/);
    });
    it('should return 400 with empty param', () => {
      return request(app.getHttpServer()).get(`/getShortUrl`).expect(400);
    });
    it('should return 400 with invalid original url', () => {
      return request(app.getHttpServer()).get(`/getShortUrl?originalUrl=aabb`).expect(400);
    });
    it('should return the same result with the same input', async () => {
      const res = await request(app.getHttpServer()).get(`/getShortUrl?originalUrl=${testUrl1}`);
      expect(res.status).toEqual(200);
      expect(res.body.shortUrl).toBe(shortUrl1);
    });
  });

  describe('/getOriginalUrl (GET)', () => {
    it('should return original url', async () => {
      const res = await request(app.getHttpServer()).get(`/getOriginalUrl?shortUrl=${shortUrl1}`);
      expect(res.status).toEqual(200);
      expect(res.body.originalUrl).toBe(testUrl1);
    });
    it('should return 400 with empty param', () => {
      return request(app.getHttpServer()).get(`/getOriginalUrl`).expect(400);
    });
    it('should return 400 with invalid short url', () => {
      return request(app.getHttpServer()).get(`/getOriginalUrl?shortUrl=aabb`).expect(400);
    });
    it('should return 400 with short url that not exist', () => {
      return request(app.getHttpServer()).get(`/getOriginalUrl?shortUrl=http://other.com/a123456u`).expect(400);
    });
  });

  afterAll(async () => {
    await app.close();
    if (fs.existsSync(persistFilePath)) {
      fs.rmSync(persistFilePath);
    }
  });
});
