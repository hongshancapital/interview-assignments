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
  
  const s_url = 'http://localhost:3332/1'
  const l_url = 'http://127.0.0.1:3332/api/swagger/#/Short%20URL/ShorturlController_getshort'

  it(`/GET /api/shorturl/getlong`, () => {
    return request(app.getHttpServer())
      .get(`/api/shorturl/getlong?s_url=${s_url}`)
      .set('Accept', 'application/json')
      .expect('Content-Type', /json/)
      .expect(200)
      .expect((res: any) => {
        expect(res.body.data).toBe(l_url);
      })
  });

  it(`/GET /api/shorturl/getshort`, () => {
    return request(app.getHttpServer())
      .get(`/api/shorturl/getshort?url=${encodeURIComponent(l_url)}`)
      .set('Accept', 'application/json')
      .expect('Content-Type', /json/)
      .expect(200)
      .expect((res: any) => {
        expect(res.body.data).toBe(s_url);
      })
  });

  // const random = Math.floor(Math.random()*10000);
  // it(`/Post /api/shorturls`, async () => {
  //   return await request(app.getHttpServer())
  //     .post(`/api/shorturls`)
  //     .send({
  //       url: l_url // +`&number=`+random
  //     })
  //     .set('Accept', 'application/json')
  //     .expect('Content-Type', /json/)
  //     .expect((res: any) => {
  //       console.log('res.body', res.body);
  //       expect(res.body.url).toBe(l_url) //+`&number=`+ random);
  //     })
  //     .expect(201)
  // });

  


  afterAll(async () => {
    await app.close();
  });
});