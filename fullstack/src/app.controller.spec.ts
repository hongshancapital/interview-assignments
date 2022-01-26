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
            getShortCode: () => {
              return Promise.resolve('code');
            },
            getLongUrl: () => {
              return Promise.resolve('url');
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

  it('URL get code', async () => {
    const code = await appController.short('url');
    expect(code).toBe('code');
  });
  it('code get URL', async () => {
    const url = await appController.long('url');
    expect(url).toBe('url');
  });
  it('redirect', async () => {
    const res = {
      redirect: jest.fn(),
    };
    await appController.redirect('code', res);
    expect(res.redirect.mock.calls.length).toBe(1);
  });
});
