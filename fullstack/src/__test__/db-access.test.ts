import {DbAccess} from "../db-access";
import {mysqlPool, close} from "../bootstrap";
const Buffer = require('safer-buffer').Buffer

describe("db-access test", () => {
  const dbAccess = new DbAccess(mysqlPool);

  afterAll(async () => {
    await close()
  })

  beforeEach(async () => {
    return new Promise((resolve, reject) => {
      mysqlPool.query('DELETE FROM short_url_info', (err, results) => {
        err ? reject(err) : resolve(results)
      })
    })
  });

  test('get url hash', async () => {
    const getHash = dbAccess['getUrlHash']
    const hash = getHash("https://www.baidu.com");
    expect(hash).toBeTruthy()
  });

  test('insert info', async () => {
    const id = await dbAccess.save("https://www.baidu.com");
    expect(id).toBeTruthy()
  });

  test('get url by id', async () => {
    const e = "https://www.baidu.com";
    const id = await dbAccess.save(e);
    const url = await dbAccess.getUrlById(id);
    expect(url).toEqual(e)
  })

  test('get url by id not exists', async () => {
    const url = await dbAccess.getUrlById(0);
    expect(url).toBeNull()
  })

  test('get id by url', async () => {
    const url = "https://www.baidu.com";
    const newId = await dbAccess.save(url);
    const r = await dbAccess.getIdByUrl(url);
    expect(r).toBeTruthy()
  });

  test('sql exception', async () => {
    await expect(() => dbAccess.save(Buffer.alloc(10000))).rejects.toThrow()
  });
})

