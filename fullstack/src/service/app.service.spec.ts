import { Test, TestingModule } from '@nestjs/testing';

import { AppService } from './app.service';
import { ConfigService } from './config.service';
import { UrlRepository } from './url.repository';
import { DB_SERVICE_TOKEN, DbService } from '../db/db.service';

import { createHash } from 'node:crypto';
import * as fs from 'node:fs';
import { join } from 'node:path';

describe('AppService', () => {
  const persistFilePath = join(process.cwd(), 'test/__testAppService__db.json');
  let app: TestingModule;

  beforeAll(async () => {
    app = await Test.createTestingModule({
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
  });

  it('should create unique code', async () => {
    const urlRepository = app.get<UrlRepository>(UrlRepository);
    let hackTimes = 5;
    const oriFunc = urlRepository.isCodeExist;
    urlRepository.isCodeExist = (code: string): boolean => {
      if (hackTimes) {
        hackTimes--;

        return true;
      } else {
        return oriFunc.call(urlRepository, code);
      }
    };
    const appService = app.get<AppService>(AppService);
    const originalUrl = 'http://example.com/a';
    const code = appService.getOrCreateCode(originalUrl);
    expect(hackTimes).toBe(0);
    const firstLetterBase = 'abcdefghijklmnopqrstuvwsyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    const firstLetterLen = firstLetterBase.length;
    const pool = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    const poolLength = pool.length;
    const buffer = createHash('sha1').update(originalUrl).digest();
    let start = buffer.readUInt32LE();
    let prefix = firstLetterBase[start % firstLetterLen];
    start = Math.floor(start / firstLetterLen);
    prefix += pool[start % poolLength];

    expect(code.replace('https://example.com/', '').startsWith(prefix)).toBeTruthy();
  });

  afterAll(async () => {
    await app.close();
    if (fs.existsSync(persistFilePath)) {
      fs.rmSync(persistFilePath);
    }
  });
});
