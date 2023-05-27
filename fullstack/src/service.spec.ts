import {
  generateShortUrl,
  parseShortUrl,
  shortenUrl,
  ShortUrlRecord,
} from "./service";

let cache: any = {};
let records: ShortUrlRecord[] = [];

jest.mock("./common", () => {
  return {
    // 简单 mock 下数据访问方法来做单元，真实场景可以使用 sqlite3
    queryDataBase: jest.fn(async (sql: string, values: any) => {
      if (sql.indexOf("where full_url_key = ?") !== -1) {
        return records.filter((i) => i.fullKey === values);
      }
      if (sql.indexOf("where short_url_key = ?") !== -1) {
        return records.filter((i) => i.shortKey === values);
      }
      if (sql.indexOf("insert") !== -1) {
        records.push({
          url: values[0],
          shortKey: values[1],
          fullKey: values[2],
        });
      }
      return [];
    }),

    cacheEx: jest.fn(async function cacheEx(
      key: string,
      value: string,
      seconds: number = 0
    ) {
      cache[key] = value;
    }),

    getFromCache: jest.fn(async (key: string) => {
      return cache[key];
    }),
  };
});

describe("short url service", () => {
  beforeEach(() => {
    records = [];
    cache = {};
  });

  test("shorten url", async () => {
    // create new
    const url1 = "https://example.com/1";
    const ret = await shortenUrl(url1);
    expect(ret.length).toBe(8);
    expect(records.length).toBe(1);

    // create existed
    const ret2 = await shortenUrl(url1);
    expect(ret2).toBe(ret);
    expect(records.length).toBe(1);

    // conflict
    const url2 = "https://example.com/2";
    const url2Info = generateShortUrl(url2);
    records[0].shortKey = url2Info.shortKey;
    const ret3 = await shortenUrl(url2);
    expect(ret3).toBe(url2Info.fullKey);
  });

  test("parse short url", async () => {
    const url = "https://example.com/1";
    const ret = await parseShortUrl(url);
    expect(ret).toBe(null);

    const key = await shortenUrl(url);
    const parsedUrl = await parseShortUrl(key);
    expect(parsedUrl).toBe(url);

    // from cache
    cache[key] = "url-from-cache";
    const parsedUrl2 = await parseShortUrl(key);
    expect(parsedUrl2).toBe("url-from-cache");

    // cache expired
    cache[key] = "";
    records[0].url = "url-from-db";
    const parsedUrl3 = await parseShortUrl(key);
    expect(parsedUrl3).toBe("url-from-db");
  });
});
