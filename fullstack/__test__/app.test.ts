import request from "supertest";
import {app} from "../src/app";
import {controller, mysqlPool, redis, close} from "../src/bootstrap";

describe("app test", () => {

  afterAll(async () => {
    await close()
  })

  test("get success", async () => {
    const url = "https://www.baidu.com";
    const shortUrl = await controller.saveUrl(url);
    const res = await request(app).get("/" + shortUrl).send();
    expect(res.status).toBe(302);
    expect(res.headers['location']).toBe(url);
  })

  test("get bad url test", async () => {
    const res = await request(app).get("/").send();
    expect(res.status).toBe(404);
  })

  test("get not found test", async () => {
    const res = await request(app).get("/abc").send();
    expect(res.status).toBe(404);
  })

  test("set test", async () => {
    const url = "https://www.baidu.com";
    const res = await request(app).post("/set?url=" + encodeURIComponent(url)).send();
    expect(res.status).toBe(200);
    expect(typeof res.body.shortUrl).toBe("string")
  })

  test("set error", async () => {
    const url = "";
    const res = await request(app).post("/set?url=" + encodeURIComponent(url)).send();
    expect(res.status).toBe(400);
    expect(typeof res.body.message).toBe("string")
  })
})
