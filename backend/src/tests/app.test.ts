import request from "supertest";
import app from "../app";

import ResCode from "../common/RespCode";
import sequelize from "../model/db";
import RedisClient from "../model/RedisModel";
import ShortService from "../service/ShortService";

describe("POST /short/create", () => {
  const fetch = request(app);
  let lastTimestamp = Date.now();
  const params = {
    url: 'https://www.baidu.com',
    clock_back_url: 'https://www.sohu.com/'
  }

  const RedisAndDBExistData = {
    short_url: 'q6l5qE',
    original_url: 'https://www.google.com'
  }

  const OnlyDBExistData = {
    short_Url: 'AJ6jLO',
    original_url: 'https://cn.bing.com'
  }

  beforeAll(async () => {
    await ShortService.destroy();
    await RedisClient.flushall();
    await Promise.all([
      ShortService.findOrCreate(RedisAndDBExistData.short_url, RedisAndDBExistData.original_url),
      ShortService.findOrCreate(OnlyDBExistData.short_Url, OnlyDBExistData.original_url),
      RedisClient.set(RedisAndDBExistData.short_url, RedisAndDBExistData.original_url),
      RedisClient.set(RedisAndDBExistData.original_url, RedisAndDBExistData.short_url)
    ]);
  });

  test("send new url", async () => {
    const res = await fetch.post('/short/create').send({ ...params });
    expect(res.statusCode).toBe(200);
    expect(res.body.code).toBe(ResCode.Code.SUCCESS);
  });
  test("retry send same url", async () => {
    const res = await fetch.post('/short/create').send({ url: OnlyDBExistData.original_url });
    expect(res.statusCode).toBe(200);
    expect(res.body.code).toBe(ResCode.Code.SUCCESS);
    expect(res.body.data).toContain(OnlyDBExistData.short_Url);
  });
  test("send redis exist url", async () => {
    const res = await fetch.post("/short/create").send({ url: RedisAndDBExistData.original_url });
    expect(res.statusCode).toBe(200);
    expect(res.body.code).toBe(ResCode.Code.SUCCESS);
    expect(res.body.data).toContain(RedisAndDBExistData.short_url);
  });
  test("send empty object", async () => {
    const res = await fetch.post('/short/create').send({});
    expect(res.statusCode).toBe(200);
    expect(res.body.code).toBe(ResCode.Code.FAILED);
  });
  test("send number", async () => {
    const res = await fetch.post('/short/create').send({ url: 123 });
    expect(res.statusCode).toBe(200);
    expect(res.body.code).toBe(ResCode.Code.FAILED);
  });
  test("send too long url", async () => {
    let url = params.url + Array(1000).fill('a').join('');
    const res = await fetch.post('/short/create').send({ ...params });
    expect(res.statusCode).toBe(200);
    expect(res.body.code).toBe(ResCode.Code.SUCCESS);
  });

  test("get db exist token", async () => {
    const res = await fetch.get(`/${OnlyDBExistData.short_Url}`);
    expect(res.status).toBe(302);
    expect(res.headers.location).toBe(OnlyDBExistData.original_url);
  });
  test("get redis exist token", async () => {
    const res = await fetch.get(`/${RedisAndDBExistData.short_url}`);
    expect(res.status).toBe(302);
    expect(res.headers.location).toBe(RedisAndDBExistData.original_url)
  });

  test("error token", async () => {
    const res = await fetch.get(`/123`);
    expect(res.status).toBe(200);
    expect(res.body.code).toBe(ResCode.Code.FAILED);
  });

  test("token length more then 8", async () => {
    const res = await fetch.get(`/pbb5v888O13`);
    expect(res.status).toBe(200);
    expect(res.body.code).toBe(ResCode.Code.FAILED);
  });

  test("root router", async () => {
    const res = await fetch.get("");
    expect(res.status).toBe(200);
  });

  afterAll(async () => {
    await RedisClient.disconnect();
    await sequelize.close();
  })
});

