import Redis from "ioredis";
import {Cache} from "../cache";

describe("cache test", () => {
  const client = new Redis();
  const cache = new Cache(client);

  afterAll(async () => {
    await client.disconnect()
  })

  test('cache set', async () => {
    await cache.set("test1", "1", 1);
  });

  test('cache get', async () => {
    await client.setex('test2', 1, '1');
    const v = await cache.get('test2');
    expect(v).toBe('1');
  });
})

