import request from "supertest";
import {app} from "../src/app";
import {service, mysqlPool, close} from "../src/bootstrap";

describe("app test", () => {

  beforeEach(async () => {
    return new Promise((resolve, reject) => {
      mysqlPool.query('DELETE FROM short_url_info', (err, results) => {
        err ? reject(err) : resolve(results)
      })
    })
  })

  afterAll(async () => {
    await close()
  })

  test("get success", async () => {
    const url = "https://www.baidu.com";
    const shortUrl = await service.saveUrl(url);
    const res = await request(app).get("/" + shortUrl).send();
    expect(res.status).toBe(302);
    expect(res.headers['location']).toBe(url);
  })

  test("get bad url test", async () => {
    const res = await request(app).get("/").send();
    expect(res.status).toBe(404);
    const res1 = await request(app).get("/123#$").send();
    expect(res1.status).toBe(404);
  })

  test("get not found test", async () => {
    const res = await request(app).get("/abcde").send();
    expect(res.status).toBe(404);
  })

  test("set test", async () => {
    const url = "https://www.baidu.com";
    const res = await request(app).post("/set?url=" + encodeURIComponent(url)).send();
    expect(res.status).toBe(200);
    expect(typeof res.body.shortUrl).toBe("string")
  })

  test("set error - empty url", async () => {
    const url = "";
    const res = await request(app).post("/set?url=" + encodeURIComponent(url)).send();
    expect(res.status).toBe(400);
    expect(typeof res.body.message).toBe("string")
  })

  test("set error - bad url", async () => {
    const url = "abc://def.com";
    const res = await request(app).post("/set?url=" + encodeURIComponent(url)).send();
    expect(res.status).toBe(400);
    expect(typeof res.body.message).toBe("string")
  })

  test("set error - max length", async () => {
    const baseUrl = 'https://baidu.com/'
    const url = "https://baidu.com/" + ('a'.repeat(5000 - baseUrl.length));
    const res = await request(app).post("/set?url=" + encodeURIComponent(url)).send();
    expect(res.status).toBe(200);
    expect(typeof res.body.shortUrl).toBe("string")

    const url2 = "https://baidu.com/" + ('a'.repeat(5001 - baseUrl.length));
    const res2 = await request(app).post("/set?url=" + encodeURIComponent(url2)).send();
    expect(res2.status).toBe(400);
    expect(typeof res2.body.message).toBe("string")
  })
})
