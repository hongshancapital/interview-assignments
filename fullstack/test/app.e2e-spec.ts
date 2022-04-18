import { Test, TestingModule } from '@nestjs/testing';
import { INestApplication } from '@nestjs/common';
import * as request from 'supertest';
import { AppModule } from './../src/app.module';

describe('AppController (e2e)', () => {
  let app: INestApplication;

  beforeEach(async () => {
    const moduleFixture: TestingModule = await Test.createTestingModule({
      imports: [AppModule],
    }).compile();

    app = moduleFixture.createNestApplication();
    await app.init();
  });

  it('redirect short url (GET)', () => {
    return request(app.getHttpServer())
      .get('/r/LjWS')
      .expect(302)
  });

  it('save long url (POST)', () => {
    return request(app.getHttpServer())
      .post('/l2s')
      .send({ longUrl: 'https://www.google.com' })
      .expect(201)
  })
});
