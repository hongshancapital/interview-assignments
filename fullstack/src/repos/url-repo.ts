import {IUrl} from "../../src/models/Url";
import {getRandomInt} from "../../src/declarations/functions";
import orm from "./mock-orm";

// **** Functions **** //

/**
 * Get one url.
 */
async function getOne(urlHash: string): Promise<IUrl | null> {
  const db = await orm.openDb();
  for (const url of db.urls) {
    if (url.urlHash === urlHash) {
      return url;
    }
  }
  return null;
}

/**
 * Get one url.
 */
async function getOneByShortUrl(shortUrl: string): Promise<IUrl | null> {
  const db = await orm.openDb();
  /** 如果需要改写为sql query，取决于orm框架，
  // 这里以squelize为例，假设模型为Url
  const url = await Url.findOne({ where: { shortUrl } });
  return url

  //如果是mongoos，写法也相同，
  // 如果是typeORM，需要创建实体，并注册到connection连接中
  import { Entity, PrimaryColumn } from "typeorm";

  @Entity(“short_url_map”)
  export class Url {
      @PrimaryColumn()
      id: number;

      lastName: string;
  }
  import { getConnection } from "typeorm";

  const url = await getConnection()
    .createQueryBuilder()
    .select("url")
    .from(Url, "url")
    .where("url.short_url = :shortUrl", { shortUrl })
    .getOne();

  中午时间短暂关系，这里只进行了mock处理，我想您要求改写的sqlquery就在上面
  */
  for (const url of db.urls) {
    if (url.shortUrl === shortUrl) {
      return url;
    }
  }
  return null;
}

/**
 * See if a url with the given id exists.
 */
async function persists(id: number): Promise<boolean> {
  const db = await orm.openDb();
  for (const url of db.urls) {
    if (url.id === id) {
      return true;
    }
  }
  return false;
}

/**
 * Get all urls.
 */
async function getAll(): Promise<IUrl[]> {
  const db = await orm.openDb();
  return db.urls;
}

/**
 * Add one url.
 */
async function add(url: IUrl): Promise<IUrl> {
  const db = await orm.openDb();
  url.id = getRandomInt();
  db.urls.push(url);
  orm.saveDb(db);
  return url;
}

/**
 * Update a url.
 */
async function update(url: IUrl): Promise<void> {
  const db = await orm.openDb();
  for (let i = 0; i < db.urls.length; i++) {
    if (db.urls[i].id === url.id) {
      db.urls[i] = url;
      return orm.saveDb(db);
    }
  }
}

/**
 * Delete one url.
 */
async function _delete(id: number): Promise<void> {
  const db = await orm.openDb();
  for (let i = 0; i < db.urls.length; i++) {
    if (db.urls[i].id === id) {
      db.urls.splice(i, 1);
      return orm.saveDb(db);
    }
  }
}

// **** Export default **** //

export default {
  getOne,
  getOneByShortUrl,
  persists,
  getAll,
  add,
  update,
  delete: _delete,
} as const;
