import request from "supertest";
import app from "./app";

const longUrl = "https://www.baidu.com";
const testCode = "q89sqXq";

describe("POST /api/generate - 生成短链接", () => {
  it("return status code 200", async () => {
    const res = await request(app).post("/api/generate").send({ longUrl });
    const urlCode = res.body.code;

    expect(urlCode).toHaveLength(7);

    expect(res.statusCode).toEqual(200);
  });

  it("return Some error  ", async () => {
    const res = await request(app).post("/api/generate");

    expect(res.statusCode).toEqual(200);

    expect(res.statusCode).toEqual(200);

    expect(res.text).toEqual("Some error");
  });
});

describe("GET /api/:code - 获取短链接地址", () => {
  it("get longUrl by code ", async () => {
    const res = await request(app).post("/api/generate").send({ longUrl });
    expect(res.statusCode).toEqual(200);
    const urlCode = res.body.code;

    const getRes = await request(app).get(`/api/${urlCode}`).send();

    expect(getRes.statusCode).toEqual(200);

    expect(getRes.text).toEqual(longUrl);
  });
});

describe("GET /:code - 短链接跳转", () => {
  it("kick tinyUrl ", async () => {
    const getRes = await request(app).get(`/${testCode}`).send();
    expect(getRes.statusCode).toEqual(302);
  });
});
