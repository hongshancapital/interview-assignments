import { OnApplicationShutdown } from '@nestjs/common';
import { interval, Subscription } from 'rxjs';

import { DbData } from './db-data';
import { ConfigService } from '../service/config.service';

export const DB_SERVICE_TOKEN = 'DB_SERVICE';

export class DbService implements OnApplicationShutdown {
  private _db: any;
  private _autoSaveHandler?: Subscription;

  constructor(private readonly config: ConfigService) {}

  async init() {
    // for test. import multiple times in one process may cause core dump
    if (!globalThis['lowModule']) {
      globalThis['lowModule'] = await import('lowdb');
      globalThis['lowNodeModule'] = await import('lowdb/node');
    }
    const lowModule = globalThis['lowModule'];
    const lowNodeModule = globalThis['lowNodeModule'];

    const adapter = new lowNodeModule.JSONFile(this.config.dbPersistPath);
    this._db = new lowModule.Low(adapter);
    try {
      await this._db.read();
    } catch {
      // ignore
    }
    if (!this._db.data) {
      this._db.data = new DbData();
    }

    this._autoSaveHandler = interval(300000).subscribe(() => {
      this._db.write();
    });
  }

  get data(): DbData {
    return this._db.data;
  }

  set data(value) {
    this._db.data = value;
  }

  async onApplicationShutdown() {
    await this._db.write();
    if (this._autoSaveHandler) {
      this._autoSaveHandler.unsubscribe();
    }
  }
}
