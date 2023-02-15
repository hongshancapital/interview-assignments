import { Test } from '@nestjs/testing';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { INestApplication } from '@nestjs/common';

describe('AppController', () => {
  let app: INestApplication;
  let appController: AppController;
  beforeEach(async () => {
    const module = await Test.createTestingModule({
      imports: [],
      controllers: [AppController],
      providers: [
        {
          provide: AppService,
          useValue: {
            getShortUrl: () => {
              return Promise.resolve('8s84b+CE');
            },
            getFullUrl: () => {
              return Promise.resolve('www.baidu.com');
            },
          },
        },
      ],
    }).compile();
    app = module.createNestApplication();
    await app.init();

    appController = app.get(AppController);
  });
  afterAll(() => {
    app.close();
  });

  it('Full_url get short', async () => {
    const code = await appController.getShortUrl('www.baidu.com');
    expect(code).toBe('8s84b+CE');
  });
  it('Short_url get full', async () => {
    const url = await appController.getFullUrl('8s84b+CE');
    expect(url).toBe('www.baidu.com');
  });
});