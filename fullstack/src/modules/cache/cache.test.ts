import Redis from "ioredis";
import { config } from "../../config";
import { CacheService } from "./cache.service";

describe("Cache Module", () => {
  const TTL = 1000;
  const KEY = "KEY";
  const VALUE = "VALUE";

  const model = new Redis(config.CACHE_REDIS_URI);
  const service = new CacheService(model, TTL);

  beforeEach(() => {
    model.flushall();
  });

  afterAll(() => {
    model.disconnect();
  });

  it("1. 写入缓存", async () => {
    await service.set(KEY, VALUE);

    const v = await model.get(KEY);
    const t = await model.pttl(KEY);
    expect(v).toBe(VALUE);
    expect(Math.abs(TTL - t) < 10);
  });

  it("2. 写入缓存, 自定义 TTL", async () => {
    await service.set(KEY, VALUE, TTL * 2);
    const v = await model.get(KEY);
    const t = await model.pttl(KEY);
    expect(v).toBe(VALUE);
    expect(Math.abs(TTL * 2 - t) < 10);
  });

  it("3. 读取缓存", async () => {
    model.set(KEY, VALUE);
    const value = await service.get(KEY);
    expect(value).toBe(VALUE);
  });
  it("4. 读取过期缓存", async () => {
    await service.set(KEY, VALUE, 100);
    await delay(200);
    const value = await service.get(KEY);
    expect(value).toBe(null);
  });
});

function delay(t: number) {
  return new Promise((resolve) => setTimeout(resolve, t));
}
