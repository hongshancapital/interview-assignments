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
