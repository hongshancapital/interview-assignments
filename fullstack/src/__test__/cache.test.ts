import Redis from "ioredis";
import {Cache} from "../cache";
import {redis, close} from "../bootstrap";

describe("cache test", () => {
  const cache = new Cache(redis);

  afterAll(async () => {
    await close()
  })

  test('cache set', async () => {
    await cache.set("test1", "1", 1);
  });

  test('cache get', async () => {
    await redis.setex('test2', 1, '1');
    const v = await cache.get('test2');
    expect(v).toBe('1');
  });

  test('cache remove', async () => {
    await redis.set('test3', 1);
    await cache.remove('test3');
    const v = await redis.get('test3');
    expect(v).toBeNull();
  })
})

