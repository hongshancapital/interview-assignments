import { Test, TestingModule } from '@nestjs/testing';
import { RedisModule } from '@nestjs-modules/ioredis';
import { INestApplication } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import * as request from 'supertest';

import { AppModule } from './app.module';
import { AppController } from './app.controller';
import { ShorturlEntity } from '../shorturl/shorturl.entity';
import { RedisConfigModule } from '../config/redis.module';
import { DatabaseConfigModule } from '../config/database.module';

describe('AppController', () => {
    let app: INestApplication;
    let appController: AppController;
  
    beforeAll(async () => {
      const moduleRef = await Test.createTestingModule({
        imports: [
          RedisConfigModule,
          DatabaseConfigModule,
          TypeOrmModule.forFeature([ShorturlEntity]),
          AppModule
        ],
        controllers: [AppController]
      })
      .overrideProvider(AppController)
      .useValue(appController)
      .compile();
  
      app = moduleRef.createNestApplication();
      await app.init();
    });
  
    it(`/GET shorturl`, () => {
      const s_url = '8C1AAA5F'
      const url = 'http://localhost:3332/api/swagger/'
      return request(app.getHttpServer())
        .get(`/${s_url}`)
        .expect(302)
        .expect(/Redirecting to/g)
    });
  
    it(`/GET shorturl error`, () => {
      const s_url2 = '7C1AAAxx'
      return request(app.getHttpServer())
        .get(`/${s_url2}`)
        .expect(404)
        .expect('404 Not Found')
    });
  
    afterAll(async () => {
      await app.close();
    });
  });