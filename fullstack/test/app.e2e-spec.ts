import { Test, TestingModule } from '@nestjs/testing';
import { INestApplication } from '@nestjs/common';
import * as request from 'supertest';
import { AppModule } from './../src/app.module';
import { AppController } from './../src/app.controller';
import { AppService } from './../src/app.service';

describe('AppController (e2e) POST', () => {
  let appController: AppController;
  let appService: AppService;

  let app: INestApplication;

  beforeEach(async () => {
    jest.restoreAllMocks();

    const moduleFixture: TestingModule = await Test.createTestingModule({
      imports: [AppModule],
    })
      .overrideProvider(AppService)
      .useValue(appService)
      .compile();

    app = moduleFixture.createNestApplication();
    await app.init();
  });

  afterAll(async () => {
    await app.close();
  });

  it(`/POST /api/link`, (done) => {
    return request(app.getHttpServer())
      .post('/api/link')
      .send({
        link: 'https://docs.nestjs.com/',
      })
      .expect(201)
      .end((error, response) => {
        if (error) {
          return done.fail(error);
        }
        expect(response.body).not.toBeNull();
        done();
      });
  });
});

describe('AppController (e2e) GET', () => {
  let appController: AppController;
  let appService: AppService;

  let app: INestApplication;

  beforeEach(async () => {
    jest.restoreAllMocks();

    const moduleFixture: TestingModule = await Test.createTestingModule({
      imports: [AppModule],
    })
      .overrideProvider(AppService)
      .useValue(appService)
      .compile();

    app = moduleFixture.createNestApplication();
    await app.init();
  });

  afterAll(async () => {
    await app.close();
  });

  it(`/GET /api/link`, (done) => {
    return request(app.getHttpServer())
      .get('/link')
      .expect(404)
      .end((error, response) => {
        if (error) {
          return done.fail(error);
        }
        expect(response.body).not.toBeNull();
        done();
      });
  });
});
