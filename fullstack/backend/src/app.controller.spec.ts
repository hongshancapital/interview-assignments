import { Test, TestingModule } from '@nestjs/testing';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { TypeOrmSQLITETestingModule, testDatasetSeed } from './testUtils';
import { Request } from 'supertest';

describe('AppController', () => {
  let appController: AppController;

  beforeEach(async () => {
    const app: TestingModule = await Test.createTestingModule({
      imports: [...TypeOrmSQLITETestingModule()],
      controllers: [AppController],
      providers: [AppService],
    }).compile();

    appController = app.get<AppController>(AppController);
    await testDatasetSeed();
  });

  const url = 'http://google.com';
  const slug = 'abcdefgh';
  const raw = 'https://www.google.com';

  it('能够返回缩短后的url', async () => {
    expect(await appController.getShortenedURL({ url })).toMatch(
      /^(https|http):\/\/.+\/.{8}$/,
    );
  });

  it('可以反查原地址', async () => {
    expect(await appController.getFullURL(slug)).toBe(raw);
  });
});
