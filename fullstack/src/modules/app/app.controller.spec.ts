import { Test, TestingModule } from '@nestjs/testing';
import { INestApplication } from '@nestjs/common';
import * as request from 'supertest';

import { MainModule } from '../../main.module';

describe('AppController', () => {
    let app: INestApplication;
  
    beforeAll(async () => {
      const moduleRef = await Test.createTestingModule({
        imports: [
          MainModule
        ],
      })
      .compile();
  
      app = moduleRef.createNestApplication();
      await app.init();
    });
  
    it(`/GET shorturl`, () => {
      const s_url = '1'
      return request(app.getHttpServer())
        .get(`/${s_url}`)
        .expect(302)
        .expect(/Redirecting to/g)
    });
  
    it(`/GET shorturl error`, () => {
      const s_url2 = 'xxxxx'
      return request(app.getHttpServer())
        .get(`/${s_url2}`)
        .expect(404)
        .expect('404 Not Found')
    });
    afterAll(async () => {
      await app.close();
    });
  });