import {createPool} from "mysql";
import {DbAccess} from "../db-access";

describe("db-access test", () => {
  const pool = createPool({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'short_url'
  })
  const dbAccess = new DbAccess(pool);

  afterAll(async () => {
    pool.end();
  })

  afterEach(async () => {
    return new Promise((resolve, reject) => {
      pool.query('DELETE FROM short_url_info', (err, results) => {
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

  test('get id by url', async () => {
    const url = "https://www.baidu.com";
    const newId = await dbAccess.save(url);
    const r = await dbAccess.getIdByUrl(url);
    expect(r).toBeTruthy()
  });
})

