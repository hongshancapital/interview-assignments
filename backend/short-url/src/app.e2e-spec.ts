import { Test, TestingModule } from '@nestjs/testing';
import { INestApplication } from '@nestjs/common';
import * as request from 'supertest';
import { AppModule } from './app.module';

describe('AppController (e2e)', () => {
  let app: INestApplication;

  beforeEach(async () => {
    const moduleFixture: TestingModule = await Test.createTestingModule({
      imports: [AppModule],
    }).compile();

    app = moduleFixture.createNestApplication();
    await app.init();
    app.get
  });

  it('/ (GET)', () => {
    return request(app.getHttpServer())
      .get('/')
      .expect(200)
      .expect('Hello World!');
  });

  it('根据原始url获取短url', async () => {
    const res1 = await request(app.getHttpServer())
      .post('/url/2short')
      .send({
        "shortUrl": "https://short.url/1"
      });
    const res2 = await request(app.getHttpServer())
      .post('/url/2short')
      .send({
        "shortUrl": "https://short.url/1"
      });
    expect(res1.body).toEqual(res2.body)
  });

  it('根据短url获取原始url', async () => {
    const shortResultRes = await request(app.getHttpServer())
      .post('/url/2short')
      .send({
        originUrl: "https://www.baidu.com/page/2"
      });

    const originResultRes = await request(app.getHttpServer())
      .post('/url/2origin')
      .send({ shortUrl: shortResultRes.body.shortUrl });

    expect(originResultRes.body.originUrl).toBe('https://www.baidu.com/page/2')
  });
});
