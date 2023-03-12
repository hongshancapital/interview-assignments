/*
 * @Author: zhangyan
 * @Date: 2023-03-10 15:53:28
 * @LastEditTime: 2023-03-10 18:13:23
 * @LastEditors: zhangyan
 * @FilePath: /fullstack/test/index.spec.ts
 * @Description: 接口测试
 */

import supertest from "supertest";
import app from "../app";
const request = supertest(app);

describe("supertest", () => {
  describe("GET /get_url", () => {
    it("test get_url miss param", async () => {
      const res = await request.get("/get_url");
      expect(res.statusCode).toBe(200);
      const json = JSON.parse(res.text);
      expect(json.error_code).toBe(10000);
    });

    it("test get_url incorrect token", async () => {
      const res = await request.get("/get_url?token=12345");
      expect(res.statusCode).toBe(200);
      const json = JSON.parse(res.text);
      expect(json.error_code).toBe(10002);
    });
  });

  describe("POST /set_url", () => {
    it("test set_url miss param", async () => {
      const res = await request.post("/set_url");
      expect(res.statusCode).toBe(200);
      const json = JSON.parse(res.text);
      expect(json.error_code).toBe(10000);
    });

    it("test set_url incorrect url", async () => {
      const res = await request
        .post("/set_url")
        .type("json")
        .send({ url: "http" });
      expect(res.statusCode).toBe(200);
      const json = JSON.parse(res.text);
      expect(json.error_code).toBe(10003);
    });
    it("test set_url success", async () => {
      const res = await request
        .post("/set_url")
        .type("json")
        .send({ url: "http://www.12121121.com" });
      expect(res.statusCode).toBe(200);
      const json = JSON.parse(res.text);
      expect(json.error_code).toBe(0);
    });
  });

  describe("/token/:id", () => {
    it("test /token/:id", async () => {
      const res = await request.get("/token/12345");
      expect(res.statusCode).toBe(200);
      expect(res.text.length).toBeGreaterThan(0);
    });
  });

});
