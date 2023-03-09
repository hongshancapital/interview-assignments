import { Injectable } from '@nestjs/common';

import { join, dirname } from 'node:path';
import * as process from 'process';

@Injectable()
export class ConfigService {
  urlPrefix = 'https://example.com/';
  dbPersistPath = '';

  constructor() {
    if (process.env.DB_PATH) {
      this.dbPersistPath = process.env.DB_PATH;
    } else {
      this.dbPersistPath = join(dirname(require.main.filename), 'db.json');
    }
  }
}
