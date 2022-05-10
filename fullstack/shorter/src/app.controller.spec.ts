import { CACHE_MODULE_OPTIONS } from '@nestjs/common';
import { Test, TestingModule } from '@nestjs/testing';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { IdfactoryModule } from './idfactory/idfactory.module';
import { KvstoreModule } from './kvstore/kvstore.module';

describe('AppController', () => {
  let appController: AppController;

  beforeEach(async () => {
    const app: TestingModule = await Test.createTestingModule({
      imports: [IdfactoryModule, KvstoreModule],
      controllers: [AppController],
      providers: [AppService],
    }).overrideProvider(CACHE_MODULE_OPTIONS).useValue({}).compile();

    appController = app.get<AppController>(AppController);
  });

  it('should return short url', async () => {
    const response = await appController.getShortURL('test');
    expect(response.data).toMatch(/^[a-zA-Z0-9]{1,8}$/g);
  });
});
