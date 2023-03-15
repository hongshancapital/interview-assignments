import { Test, TestingModule } from '@nestjs/testing';

import { ConfigService } from './config.service';
import { UrlRepository } from './url.repository';
import { DbData } from '../db/db-data';
import { DB_SERVICE_TOKEN, DbService } from '../db/db.service';

import * as fs from 'node:fs';
import { join } from 'node:path';

describe('UrlRepository', () => {
  const persistFilePath = join(process.cwd(), 'test/__testUrlRepository__db.json');
  let app: TestingModule;

  beforeAll(async () => {
    const oldData = new DbData();
    oldData.urls.records[1] = { id: 1, code: 'temp1', originalUrl: 'https://example.com/1' };

    fs.writeFileSync(persistFilePath, JSON.stringify(oldData));
    app = await Test.createTestingModule({
      providers: [
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

  it('should work', async () => {
    const urlRepository = app.get<UrlRepository>(UrlRepository);
    expect(urlRepository.findByCode('temp1')).toBeDefined();
  });

  afterAll(async () => {
    await app.close();
    if (fs.existsSync(persistFilePath)) {
      fs.rmSync(persistFilePath);
    }
  });
});
