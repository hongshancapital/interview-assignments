import { Test } from '@nestjs/testing';
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
    describe('/GET shorturl', () => {
      it(`/GET shorturl`, () => {
        const url = '1'
        return request(app.getHttpServer())
          .get(`/${url}`)
          .expect(302)
          .expect(/Redirecting to/g)
      });
    });
  
    describe('/GET shorturl error', () => {
      it(`/GET shorturl error`, () => {
        const url2 = 'xxxxx'
        return request(app.getHttpServer())
          .get(`/${url2}`)
          .expect(404)
          .expect('404 Not Found')
      });
    });

    afterAll(async () => {
      await app.close();
    });
  });