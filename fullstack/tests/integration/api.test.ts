import app from "../../app";
import request from "supertest";
import mysqlFuncs from "../../database/mysql";

describe("Integration test", () => {
  describe("Test for api, <POST /shorten>", () => {
    beforeAll((done) => {
      mysqlFuncs
        .dbConnect()
        .then(() => mysqlFuncs.dbInit())
        .then(() => mysqlFuncs.emptyTinyURL())
        .then(() => done());
    });

    afterAll((done) => {
      mysqlFuncs.closeDbConnection().then(() => {
        done();
      });
    });

    const requestURL = "/shorten";

    test("Normal request", async () => {
      let res = await request(app)
        .post(requestURL)
        .set("Content-Type", "application/json")
        .send({ url: "https://www.baidu.com" });
      expect(res.statusCode).toBe(200);
      res = JSON.parse(res.text);
      expect(res).toHaveProperty("success", true);
      expect(res).toHaveProperty("url", "https://gitly.io/1");
      expect(res).toHaveProperty("msg", "shortened url created");
    });

    test("Normal request, but the url has been shortened", async () => {
      let res = await request(app)
        .post(requestURL)
        .set("Content-Type", "application/json")
        .send({ url: "https://www.baidu.com" });
      expect(res.statusCode).toBe(200);
      res = JSON.parse(res.text);
      expect(res).toHaveProperty("success", false);
      expect(res).toHaveProperty("url", "https://gitly.io/1");
      expect(res).toHaveProperty("msg", "original url has been created before");
    });

    test("Missing param in request body", async () => {
      let res = await request(app)
        .post(requestURL)
        .set("Content-Type", "application/json")
        .send({});
      expect(res.statusCode).toBe(400);
      res = JSON.parse(res.text);
      expect(res).toHaveProperty(
        "msg",
        "please offer an url in your request body",
      );
    });

    test("Invalid URL in request body", async () => {
      let res = await request(app)
        .post(requestURL)
        .set("Content-Type", "application/json")
        .send({ url: "ww.baidu.com" });
      expect(res.statusCode).toBe(400);
      res = JSON.parse(res.text);
      expect(res).toHaveProperty("msg", "invalid url");
    });
  });

  describe("Test for api, <POST /unshorten>", () => {
    beforeAll((done) => {
      mysqlFuncs
        .dbConnect()
        .then(() => mysqlFuncs.dbInit())
        .then(() => mysqlFuncs.emptyTinyURL())
        .then(() =>
          mysqlFuncs.insertTinyURL(
            "https://gitly.io/1",
            "https://www.baidu.com",
          ),
        )
        .then(() => done());
    });

    afterAll((done) => {
      mysqlFuncs.closeDbConnection().then(() => {
        done();
      });
    });

    const requestURL = "/unshorten";

    test("Normal request", async () => {
      let res = await request(app)
        .post(requestURL)
        .set("Content-Type", "application/json")
        .send({ url: "https://gitly.io/1" });
      expect(res.statusCode).toBe(200);
      res = JSON.parse(res.text);
      expect(res).toHaveProperty("success", true);
      expect(res).toHaveProperty("url", "https://www.baidu.com");
      expect(res).toHaveProperty("msg", "orignal url found");
    });

    test("Normal request, but the shortened one do not exist", async () => {
      let res = await request(app)
        .post(requestURL)
        .set("Content-Type", "application/json")
        .send({ url: "https://gitly.io/2" });
      expect(res.statusCode).toBe(200);
      res = JSON.parse(res.text);
      expect(res).toHaveProperty("success", false);
      expect(res).toHaveProperty("url", "https://gitly.io/2");
      expect(res).toHaveProperty("msg", "orignal url not found");
    });

    test("Missing param in request body", async () => {
      let res = await request(app)
        .post(requestURL)
        .set("Content-Type", "application/json")
        .send({});
      expect(res.statusCode).toBe(400);
      res = JSON.parse(res.text);
      expect(res).toHaveProperty(
        "msg",
        "please offer an url in your request body",
      );
    });

    test("Invalid URL in request body", async () => {
      let res = await request(app)
        .post(requestURL)
        .set("Content-Type", "application/json")
        .send({ url: "gitly.io/1" });
      expect(res.statusCode).toBe(400);
      res = JSON.parse(res.text);
      expect(res).toHaveProperty("msg", "invalid url");
    });
  });
});
