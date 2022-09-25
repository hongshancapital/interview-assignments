import request from "supertest";
import { app } from "../src/app";
import { reset, close } from "./helper";

beforeEach(reset);
afterAll(close);

const testEncode = async (url: string) => {
  const res = await request(app).post("/encode").send({ url });

  expect(res.headers["content-type"]).toMatch("application/json");
  expect(res.status).toBe(200);
  expect(typeof res.body.alias).toBe("string");
  expect(res.body.alias.length).toBe(8);
};

describe("1 测试 encode 接口 200", () => {
  it("1.1 正确编码常见 URL", async () => {
    await testEncode("http://example.com");
    await testEncode("https://example.com");
  });

  it("1.2 正确编码带 query 的 URL", async () => {
    await testEncode("https://example.com?a=1&b=2&c=3");
    await testEncode("https://example.com?a=%3B&b=%25&c=%5e");
  });

  it("1.3 正确编码带 hash 的 URL", async () => {
    await testEncode("https://example.com#ANCHOR_ID");
  });
});

describe("2. 测试 encode 接口 400", () => {
  it("2.1 url 参数为空", async () => {
    const res = await request(app).post("/encode").send({});

    expect(res.headers["content-type"]).toMatch("application/json");
    expect(res.status).toBe(400);
    expect(res.body.message).toBe("Parameter error");
  });

  it("2.2 url 缺少协议头", async () => {
    const res = await request(app).post("/encode").send({
      url: "www.example.com",
    });

    expect(res.headers["content-type"]).toMatch("application/json");
    expect(res.status).toBe(400);
    expect(res.body.message).toBe("Parameter error");
  });

  it("2.2 url 被编码过", async () => {
    const res = await request(app).post("/encode").send({
      url: "https%3A%2F%2Fwww.example.com",
    });

    expect(res.headers["content-type"]).toMatch("application/json");
    expect(res.status).toBe(400);
    expect(res.body.message).toBe("Parameter error");
  });
});
