import { Test } from '@nestjs/testing';
import { INestApplication } from '@nestjs/common';
import * as request from 'supertest';

import { MainModule } from '../../main.module';

describe('ShorturlController', () => {
  let app: INestApplication;

  beforeAll(async () => {
    const moduleRef = await Test.createTestingModule({
      imports: [
        MainModule,
      ],
    }).compile();

    app = moduleRef.createNestApplication();
    await app.init();
  });
  
  const s_url = 'http://localhost:3332/5'
  const l_url = 'http://127.0.0.1:3332/api/swagger/#/Short%20URL/ShorturlController_getshort'

  describe('/GET /api/getlong', () => {
    it(`/GET /api/getlong`, () => {
      return request(app.getHttpServer())
        .get(`/api/getlong?url=${s_url}`)
        .set('Accept', 'application/json')
        .expect('Content-Type', /json/)
        .expect(200)
        .expect((res: any) => {
          expect(res.body.data).toBe(l_url);
        })
    });
  });

  describe('/GET /api/getshort', () => {
    it(`/GET /api/getshort`, () => {
      return request(app.getHttpServer())
        .get(`/api/getshort?url=${encodeURIComponent(l_url)}`)
        .set('Accept', 'application/json')
        .expect('Content-Type', /json/)
        .expect(200)
        .expect((res: any) => {
          expect(res.body.data).toBe(s_url);
        })
    });
  });

  afterAll(async () => {
    await app.close();
  });
});