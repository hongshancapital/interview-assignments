import { Test, TestingModule } from '@nestjs/testing';
import { INestApplication, ValidationPipe } from '@nestjs/common';
import * as request from 'supertest';
import { AppModule } from '../src/modules/app.module';
import { Response } from '../src/interceptor/response';
import { ErrFilter } from '../src/interceptor/err';

describe('ShortModule (e2e)', () => {
  let app: INestApplication;
  let testUrl = 'https://www.baidu.com';

  beforeEach(async () => {
    const moduleFixture: TestingModule = await Test.createTestingModule({
      imports: [AppModule],
    }).compile();

    app = moduleFixture.createNestApplication();
    app.useGlobalPipes(new ValidationPipe());
    app.useGlobalInterceptors(new Response());
    app.useGlobalFilters(new ErrFilter());
    await app.init();
  });

  it('success', async () => {
    let response = await request(app.getHttpServer())
      .post('/short/upload')
      .set('Content-Type', 'application/json; charset=UTF-8')
      .send({
        url: testUrl,
      });
    expect(response.body.statusCode).toEqual(200);
    let url = response.body.data.url;
    response = await request(app.getHttpServer())
      .get(new URL(url).pathname)
      .set('Content-Type', 'application/json; charset=UTF-8')
      .send();
    expect(response.body.statusCode).toEqual(200);
    expect(response.body.data.url).toEqual(testUrl);
  });

  it('fail upload', async () => {
    let response = await request(app.getHttpServer())
      .post('/short/upload')
      .set('Content-Type', 'application/json; charset=UTF-8')
      .send({
        url: '',
      });
    expect(response.body.statusCode).toEqual(400);
  });

  it('fail get', async () => {
    let code = 'fail';
    let response = await request(app.getHttpServer())
      .get(`/short/${code}`)
      .set('Content-Type', 'application/json; charset=UTF-8')
      .send();
    expect(response.body.statusCode).toEqual(400);
  });

  afterEach(async () => {
    await app.close();
  });
});
