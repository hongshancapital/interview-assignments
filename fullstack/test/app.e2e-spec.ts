import { Test, TestingModule } from '@nestjs/testing';
import { INestApplication } from '@nestjs/common';
import * as request from 'supertest';
import { AppModule } from './../src/app.module';
import { AppService } from '../src/app.service';

describe('App (e2e)', () => {
  let app: INestApplication;
  let code: string;
  const url = 'http://some.path.com';
  beforeAll(async () => {
    const module: TestingModule = await Test.createTestingModule({
      imports: [AppModule],
    }).compile();

    app = module.createNestApplication();
    await app.init();
  });

  afterAll(() => {
    app.close();
  });

  it('/short (GET)', async () => {
    const res = await request(app.getHttpServer())
      .get(`/short?url=${url}`)
      .expect(200);

    code = res.text;
    expect(code.length).toBe(6);
  });

  it('/r (GET)', async () => {
    return request(app.getHttpServer()).get(`/r/${code}`).expect(302);
  });

  it('/long (GET)', async () => {
    const res = await request(app.getHttpServer())
      .get(`/long?code=${code}`)
      .expect(200);
    expect(res.text).toBe(url);

    // 测试完成，清理生成的测试数据，并校验
    const appService = app.get<AppService>(AppService);
    const effected = await appService.removeShortCode(code);
    expect(effected).toBe(1);
  });
});
