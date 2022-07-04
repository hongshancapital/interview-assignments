import { Test, TestingModule } from '@nestjs/testing';
import { INestApplication } from '@nestjs/common';
import * as request from 'supertest';
import { AppModule } from './../src/app.module';
import * as URL from 'url';

describe('ShortLinkController (e2e)', () => {
  let app: INestApplication;

  beforeEach(async () => {
    const moduleFixture: TestingModule = await Test.createTestingModule({
      imports: [AppModule],
    }).compile();

    app = moduleFixture.createNestApplication();
    await app.init();
  });

  it('/ (POST)', async () => {
    const { statusCode } = await request(app.getHttpServer())
      .post('/')
      .send({ link: 'https://google.com' })
      .set('Content-type', 'application/x-www-form-urlencoded')
      .set('Accept', 'application/json');
    expect(statusCode === 201).toBeTruthy();
  });

  it('/ (POST) 400', async () => {
    const { statusCode } = await request(app.getHttpServer())
      .post('/')
      .send({ url: 'https://google.com' })
      .set('Content-type', 'application/x-www-form-urlencoded')
      .set('Accept', 'application/json');
    expect(statusCode === 400).toBeTruthy();
  });

  it('/:id (GET) 404', async () => {
    const { statusCode: statusCode1 } = await request(app.getHttpServer()).get(
      '/143829425435',
    );
    expect(statusCode1 === 404);

    const { statusCode: statusCode2 } = await request(app.getHttpServer()).get(
      '/#fds',
    );
    expect(statusCode2 === 404);
  });

  it('/:id (GET) 400', async () => {
    const { statusCode } = await request(app.getHttpServer()).get('/^dgf#');
    expect(statusCode === 404).toBeTruthy();
  });

  it('/:id (GET) 302', async () => {
    const link = 'https://google.com';
    const {
      statusCode,
      body: { link: shortLink },
    } = await request(app.getHttpServer())
      .post('/')
      .send({ link })
      .set('Content-type', 'application/x-www-form-urlencoded')
      .set('Accept', 'application/json');
    expect(statusCode === 201).toBeTruthy();
    expect(typeof shortLink === 'string').toBeTruthy();
    // 测试生成的短链接是否能重定向到link
    const gShortLink = URL.parse(shortLink).pathname;
    const { statusCode: getStatusCode, headers } = await request(
      app.getHttpServer(),
    ).get(gShortLink);
    expect(getStatusCode === 302).toBeTruthy();
    expect(headers && headers['location'] === link).toBeTruthy();
  });
});
