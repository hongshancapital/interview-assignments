import request from "supertest";

import { app } from "../src/index";

// 随机字符串
function randomString(len: number): string {
  const charSet: string =
    "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  let randomString: string = "";
  for (let i = 0; i < len; i++) {
    const randomPoz: number = Math.floor(Math.random() * charSet.length);
    randomString += charSet.substring(randomPoz, randomPoz + 1);
  }
  return randomString;
}

describe("Test index.ts", () => {
  test("test save api with null", async () => {
    const res = await request(app).get("/save/");
    expect(res.statusCode).toEqual(404);
  });

  const longUrl: string = randomString(20);
  let shortUrl: string;

  test("test save api fisrt time", async () => {
    const res = await request(app).get("/save/" + longUrl);
    expect(res.statusCode).toEqual(201);
    shortUrl = res.text;
  });

  test("test save api second time", async () => {
    const res = await request(app).get("/save/" + longUrl);
    expect(res.statusCode).toEqual(208);
    expect(res.text).toEqual(shortUrl);
  });

  test("test load api", async () => {
    const res = await request(app).get("/load/" + shortUrl);
    expect(res.statusCode).toEqual(200);
    expect(res.text).toEqual(longUrl);
  });

  test("test load api with random string", async () => {
    const res = await request(app).get("/load/" + randomString(20));
    expect(res.statusCode).toEqual(404);
  });
});
