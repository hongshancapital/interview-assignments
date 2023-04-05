import { Test, TestingModule } from '@nestjs/testing';
import { INestApplication } from '@nestjs/common';
import request from 'supertest';
import { AppModule } from '../src/app.module';
import { ERROR } from '../src/constants'


describe('AppController (e2e)', () => {
  let app: INestApplication, server;

  beforeAll(async () => {
    const moduleFixture: TestingModule = await Test.createTestingModule({
      imports: [AppModule],
    }).compile();
    app = moduleFixture.createNestApplication();
    await app.init();
    server = app.getHttpServer()
  });

  it('/shortUrl (POST) 测试生成shortUrl 并根据shortUrl 换取longUrl', async () => {
    const payload1 = { longUrl: 'http://a.com' }
    const res1 = await request(server)
      .post('/shortUrl')
      .send(payload1)

    const shortUrl = res1.body.data
    expect(shortUrl !== undefined)

    const res2 = await request(server)
      .get(`/shortUrl?shortUrl=${shortUrl}`)

    console.log('res2', res2.body.data)
    expect(res2.body.data === payload1.longUrl)

    const payload2 = { shortUrl }

    // 测试完成删除该映射避免脏数据
    await request(server)
      .delete('/shortUrl')
      .send(payload2)
      .expect(200)
  });

  it('/shortUrl (GET) 测试未传shortUrl 换取longUrl', async () => {
    const res1 = await request(server)
      .get('/shortUrl')

    expect(res1.body.code).toEqual(ERROR.INVALID_SHORTURL.code)
  });

  it('/longUrl (POST) 测试未传longUrl 换取shortUrl', async () => {
    const payload1 = {}
    const res1 = await request(server)
      .post('/shortUrl')
      .send(payload1)
    // console.log()
    expect(res1.body.code).toEqual(ERROR.INVALID_LONGURL.code)
  });

  it('/shortUrl (GET) 测试非法shortUrl 换取longUrl（hostname不一致）', async () => {
    const fakeShortUrl = "http://notexist/fake" // 本地应该为127.0.0.1
    const res1 = await request(server)
      .get(`/shortUrl?shortUrl=${fakeShortUrl}`)
    console.log(res1.body.code)
    expect(res1.body.code).toEqual(ERROR.INVALID_SHORTURL.code)
  });
});
