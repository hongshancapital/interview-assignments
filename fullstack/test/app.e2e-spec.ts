import { Test, TestingModule } from '@nestjs/testing';
import { INestApplication } from '@nestjs/common';
import * as request from 'supertest';
import { ShortUrlModule } from '../src/short-url/short-url.module';
import { LongUrlDTO } from '../src/short-url/short-url.dto';

describe('AppController (e2e)', () => {
  let app: INestApplication;

  beforeEach(async () => {
    const moduleFixture: TestingModule = await Test.createTestingModule({
      imports: [ShortUrlModule],
    }).compile();

    app = moduleFixture.createNestApplication();
    await app.init();
  });

  it('/ (GET)', () => {
    return request(app.getHttpServer())
      .get('/1iFI')
      .expect(200)
      .expect('https://coolcao.com/e');
  });

  it('/ (POST)', () => {
    const dto = new LongUrlDTO('https://coolcao.com/d');
    return request(app.getHttpServer())
      .post('/')
      .set('Accept', 'application/json')
      .send(dto)
      .expect(201)
      .expect('1iED');
  });
});
