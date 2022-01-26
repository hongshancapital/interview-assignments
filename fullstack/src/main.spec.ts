import { INestApplication } from '@nestjs/common';
import * as request from 'supertest';
import AppPromise from './main';

describe('Test Main', () => {
  let app: INestApplication;
  beforeAll(async () => {
    app = await AppPromise.then();
  });
  afterAll(() => {
    app && app.close();
  });
  it('app start', () => {
    return request(app.getHttpServer()).get('/swagger').expect(301);
  });
});
