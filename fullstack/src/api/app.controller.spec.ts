import { Test, TestingModule } from '@nestjs/testing';

import { AppController } from './app.controller';
import { DB_SERVICE_TOKEN, DbService } from '../db/db.service';
import { AppService } from '../service/app.service';
import { ConfigService } from '../service/config.service';
import { UrlRepository } from '../service/url.repository';

import * as fs from 'node:fs';
import { join } from 'node:path';
import * as process from 'process';

describe('AppController', () => {
  const persistFilePath = join(process.cwd(), 'test/__testAppController__db.json');
  let app: TestingModule;
  let appController: AppController;

  beforeAll(async () => {
    if (fs.existsSync(persistFilePath)) {
      fs.rmSync(persistFilePath);
    }

    app = await Test.createTestingModule({
      controllers: [AppController],
      providers: [
        AppService,
        UrlRepository,
        {
          provide: DB_SERVICE_TOKEN,
          useFactory: async (config: ConfigService) => {
            const dbSrv = new DbService(config);
            await dbSrv.init();

            return dbSrv;
          },
          inject: [ConfigService],
        },
      ],
    })
      .useMocker((token) => {
        if (token === ConfigService) {
          const config = new ConfigService();
          config.dbPersistPath = persistFilePath;

          return config;
        }
      })
      .compile();
    appController = app.get<AppController>(AppController);
  });
  const testUrl1 = 'https://test.example.com/a';
  let testUrl2 = testUrl1;
  for (let i = 2048; i--; ) {
    testUrl2 += Number(Math.floor(Math.random()) * 36).toString(36);
  }
  let shortUrl1: string;

  describe('getShortUrl', () => {
    it('should return short url that matches rule', () => {
      const result = appController.getShortUrl({ originalUrl: testUrl1 });
      shortUrl1 = result?.shortUrl;
      expect(shortUrl1).toMatch(/^https:\/\/example\.com\/[a-zA-Z][0-9a-zA-Z]{7}$/);
    });
    it('should return the same result when original url is the same', () => {
      const result = appController.getShortUrl({ originalUrl: testUrl1 });
      expect(result?.shortUrl).toBe(shortUrl1);
    });
    it('should support long url and return unique result for each url', () => {
      const result = appController.getShortUrl({ originalUrl: testUrl2 });
      expect(result?.shortUrl).toBeDefined();
      expect(result?.shortUrl).not.toBe(shortUrl1);
    });
  });

  describe('getOriginalUrl', () => {
    it('should return original url', () => {
      const result = appController.getOriginalUrl({ shortUrl: shortUrl1 });
      expect(result?.originalUrl).toBe(testUrl1);
    });
    it('should return throw error when shor url not exists', () => {
      expect(() => {
        appController.getOriginalUrl({ shortUrl: 'https://example.com/3' });
      }).toThrowError();
    });
  });

  afterAll(async () => {
    await app.close();
    if (fs.existsSync(persistFilePath)) {
      fs.rmSync(persistFilePath);
    }
  });
});
