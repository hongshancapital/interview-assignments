import { Test, TestingModule } from '@nestjs/testing';

import { DbData } from './db-data';
import { DB_SERVICE_TOKEN, DbService } from './db.service';
import { ConfigService } from '../service/config.service';

import * as fs from 'node:fs';
import { join } from 'node:path';

describe('DbService', () => {
  const cwdPath = process.cwd();

  const createApp = async (dbPath: string): Promise<TestingModule> => {
    return await Test.createTestingModule({
      providers: [
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
          config.dbPersistPath = dbPath;

          return config;
        }
      })
      .compile();
  };

  it('should work with invalid json file', async () => {
    const dbPath1 = join(cwdPath, 'test/__testDbService1__db.json');
    let app = await createApp(dbPath1);
    expect(app.get<DbService>(DB_SERVICE_TOKEN)).toBeDefined();
    await app.close();
    fs.rmSync(dbPath1);

    const dbPath2 = join(cwdPath, 'test/__testDbService2__db.json');
    fs.closeSync(fs.openSync(dbPath2, 'w'));
    app = await createApp(dbPath2);
    expect(app.get<DbService>(DB_SERVICE_TOKEN)).toBeDefined();
    await app.close();
    fs.rmSync(dbPath2);
  });

  it('should auto save every 5min', async () => {
    jest.useFakeTimers();
    const dbPath3 = join(cwdPath, 'test/__testDbService3__db.json');
    const app = await createApp(dbPath3);
    const dbService = app.get<DbService>(DB_SERVICE_TOKEN);
    dbService.data = new DbData();
    jest.advanceTimersByTime(300000);

    jest.useRealTimers();
    await new Promise((r) => setTimeout(r, 200));
    const content = fs.readFileSync(dbPath3, 'utf8');
    expect(content?.length).toBeGreaterThan(0);
    await app.close();
    fs.rmSync(dbPath3);
  });
});
