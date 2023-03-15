import { Test } from '@nestjs/testing';

import { AppModule } from './app.module';

import * as fs from 'node:fs';
import * as process from 'process';

describe('AppModule', () => {
  it('should run', async () => {
    const persistFilePath = `${process.cwd()}/test/__testAppModule__db.json`;
    process.env.DB_PATH = persistFilePath;
    const app = await Test.createTestingModule({ imports: [AppModule] }).compile();
    delete process.env.DB_PATH;
    await app.close();
    if (fs.existsSync(persistFilePath)) {
      fs.rmSync(persistFilePath);
    }
  });
});
