import { Test, TestingModule } from '@nestjs/testing';
import { INestApplication } from '@nestjs/common';
import * as request from 'supertest';
import { AppModule } from './../src/app.module';

describe('AppController (e2e)', () => {
  let app: INestApplication;

  beforeAll(async () => {
    const moduleFixture: TestingModule = await Test.createTestingModule({
      imports: [AppModule],
    }).compile();

    app = moduleFixture.createNestApplication();
    await app.init();
  });

  it('/getShortURL', async () => {
    const response = await request(app.getHttpServer()).get('/getShortURL?url=https://zhuanlan.zhihu.com/p/433118672');
    expect(response.status).toEqual(200);
    expect(response.body.data).toMatch(/^[a-zA-Z0-9]{1,8}$/g);
  });

  it('/getOriginURL', async () => {
    const originURL = 'https://zhuanlan.zhihu.com/p/433118673';
    const responseShort = await request(app.getHttpServer()).get(`/getShortURL?url=${originURL}`);
    const shortURL = responseShort.body.data;
    const response = await request(app.getHttpServer()).get(`/getOriginURL?url=${shortURL}`);
    expect(response.status).toEqual(200);
    expect(response.body.data).toBe(originURL);
  });

  afterAll(async () => {
    await app.close();
  });
});
