import * as http from "http";
import { Db } from "mongodb";
import * as request from "supertest";
import app, { connection } from "./app";

describe("app should work", () => {
  let server: http.Server;
  const port = 3400;
  const baseUrl = `http://localhost:${port}/`;
  let db: Db;
  beforeAll(async () => {
    server = app.listen(port);
    db = await connection.then((connection) =>
      connection.db(process.env.DB_NAME)
    );
  });

  afterAll(() => {
    server?.close();
    connection.then((connection) => connection.close());
  });

  it("should invalid url work", async () => {
    const res = await request(baseUrl).post("shortlink").send({
      url: "localhost",
    });
    expect(res.text).toEqual("Invalid url");
  });

  it("should invalid short url work", async () => {
    const res = await request(baseUrl).get("233");
    expect(res.text).toEqual("Invalid short url");
  });

  it("should 404 work", async () => {
    const res = await request(baseUrl).post("404");
    expect(res.text).toEqual("404 Not Found");
  });

  it("should generate short url work", async () => {
    // generate
    const { text } = await request(baseUrl).post("shortlink").send({
      url: "http://localhost",
    });
    const shortUrl = text.replace(/^\//g, "");
    expect(shortUrl.match(/^[0-9A-Za-z-_]+/g)?.[0]).toHaveLength(8);
    const item = await db.collection("links").findOne({ short_url: shortUrl });
    expect(item?.short_url).toEqual(shortUrl);

    // search
    {
      const res = await request(baseUrl).get(shortUrl);
      expect(res.text).toEqual("http://localhost");
    }
  });
});
