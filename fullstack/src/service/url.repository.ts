import { Inject, Injectable } from '@nestjs/common';

import { DB_SERVICE_TOKEN, DbService } from '../db/db.service';
import { Url } from '../db/url.entity';

@Injectable()
export class UrlRepository {
  private originalUrl: Map<string, Url> = new Map();
  private codeMap: Map<string, Url> = new Map();

  constructor(@Inject(DB_SERVICE_TOKEN) private db: DbService) {
    for (const item of Object.values(db.data.urls.records)) {
      this.originalUrl.set(item.originalUrl, item);
      this.codeMap.set(item.code, item);
    }
  }

  isCodeExist(code: string): boolean {
    return this.codeMap.has(code);
  }

  findByCode(url: string): Url | undefined {
    return this.clone(this.codeMap.get(url));
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  findByOriginalUrl(url: string, _prefix: string, _hash: string): Url | undefined {
    // TODO: find original url from data source split by prefix, then by indexed hash, finally by url
    return this.clone(this.originalUrl.get(url));
  }

  add(entity: Url): Url {
    const item = this.clone(entity);
    item.id = this.db.data.urls.nextId++;
    this.records[item.id] = item;
    this.originalUrl.set(item.originalUrl, item);
    this.codeMap.set(item.code, item);
    entity.id = item.id;

    return entity;
  }

  private get records(): { [key: number]: Url } {
    return this.db.data.urls.records;
  }

  private clone<T>(instance: T): T {
    if (instance === null || instance === undefined) {
      return instance;
    }
    const copy = new (instance.constructor as { new (): T })();
    Object.assign(copy, instance);

    return copy;
  }
}
