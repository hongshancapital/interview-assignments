import request from "supertest";
import { app } from "../src/app";
import { getCacheService } from "../src/modules/cache/cache.service";
import { getPersistService } from "../src/modules/persist/persist.service";
import { reset, close } from "./helper";

beforeEach(reset);
afterAll(close);

async function testDecode(alias: string, url: string) {
  const res = await request(app).post("/decode").send({ alias });

  expect(res.headers["content-type"]).toMatch("application/json");
  expect(res.status).toBe(200);
  expect(res.body.url).toBe(url);
}

describe("1 测试 decode 接口 200", () => {
  it("1.1 命中缓存, 从缓存提取获取信息", async () => {
    const alias = "00000101";
    const url = "https://example.com/1";
    await getCacheService().set(alias, url);

    await testDecode(alias, url);
  });

  it("1.2 缓存失效, 从数据库获取信息", async () => {
    const alias = "00000201";
    const url = "https://example.com/2";
    await getPersistService().set(alias, url);

    await testDecode(alias, url);
  });

  it("1.3 完整链路", async () => {
    const url = "https://example.com";
    const encodeRes = await request(app).post("/encode").send({ url });
    const alias = encodeRes.body.alias;
    expect(typeof alias).toBe("string");

    const decodeRes = await request(app).post("/decode").send({ alias });
    expect(decodeRes.body.url).toBe(url);
  });
});

describe("2 测试 decode 接口 400", () => {
  it("2.1 alias 为空", async () => {
    const res = await request(app).post("/decode").send({});

    expect(res.headers["content-type"]).toMatch("application/json");
    expect(res.status).toBe(400);
    expect(res.body.message).toBe("Parameter error");
  });

  it("2.2 alias 长度不正确", async () => {
    const res = await request(app).post("/decode").send({ alias: "123456" });

    expect(res.headers["content-type"]).toMatch("application/json");
    expect(res.status).toBe(400);
    expect(res.body.message).toBe("Parameter error");
  });

  it("2.3 alias 编码不正确", async () => {
    const res = await request(app).post("/decode").send({ alias: "1234567!" });

    expect(res.headers["content-type"]).toMatch("application/json");
    expect(res.status).toBe(400);
    expect(res.body.message).toBe("Parameter error");
  });
});

describe("3 测试 decode 接口 404", () => {
  it("3.1 alias 不存在", async () => {
    const res = await request(app).post("/decode").send({ alias: "12345601" });

    expect(res.headers["content-type"]).toMatch("application/json");
    expect(res.status).toBe(404);
    expect(res.body.message).toBe("Not found");
  });

  it("3.2 alias 已经过期", async () => {
    const alias = "00000101";
    const url = "https://example.com/1";
    const service = getPersistService();

    const doc = new service.model({
      alias,
      url,
      expireAt: Date.now() - 10,
    });

    await doc.save();

    const res = await request(app).post("/decode").send({ alias });
    expect(res.headers["content-type"]).toMatch("application/json");
    expect(res.status).toBe(404);
    expect(res.body.message).toBe("Not found");
  });
});
