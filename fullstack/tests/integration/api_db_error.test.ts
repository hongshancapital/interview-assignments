import app from "../../app";
import request from "supertest";

jest.mock("../../database/mysql", () => {
  return {
    findShortenedURLByOrignal: jest
      .fn()
      .mockResolvedValueOnce("Failed to get the shortened URL from db.")
      .mockResolvedValue(""),
    findOrignalURLByShort: jest
      .fn()
      .mockResolvedValue("Failed to get the orignal URL from db."),
    findMaxIdInTinyURL: jest
      .fn()
      .mockResolvedValueOnce("Failed to get the max id of table tiny_table.")
      .mockResolvedValue(1),
    insertTinyURL: jest
      .fn()
      .mockResolvedValue("Failed to insert the shortened URL into db."),
    dbConnect: jest.fn().mockResolvedValue(""),
    dbInit: jest.fn().mockResolvedValue(""),
  };
});

describe("Integration test", () => {
  describe("Test for api, <POST /shorten>", () => {
    const requestURL = "/shorten";

    test("findShortenedURLByOrignal failed", async () => {
      let res = await request(app)
        .post(requestURL)
        .set("Content-Type", "application/json")
        .send({ url: "https://www.baidu.com" });
      expect(res.statusCode).toBe(500);
      expect(res).toHaveProperty("text");
      expect(res.text).toMatch("Failed to get the shortened URL from db.");
    });

    test("findMaxIdInTinyURL failed", async () => {
      let res = await request(app)
        .post(requestURL)
        .set("Content-Type", "application/json")
        .send({ url: "https://www.baidu.com" });
      expect(res.statusCode).toBe(500);
      expect(res).toHaveProperty("text");
      expect(res.text).toMatch("Failed to get the max id of table tiny_table.");
    });

    test("insertTinyURL failed", async () => {
      let res = await request(app)
        .post(requestURL)
        .set("Content-Type", "application/json")
        .send({ url: "https://www.baidu.com" });
      expect(res.statusCode).toBe(500);
      expect(res).toHaveProperty("text");
      expect(res.text).toMatch("Failed to insert the shortened URL into db.");
    });
  });

  describe("Test for api, <POST /unshorten>", () => {
    const requestURL = "/unshorten";

    test("Normal request", async () => {
      let res = await request(app)
        .post(requestURL)
        .set("Content-Type", "application/json")
        .send({ url: "https://gitly.io/1" });
      expect(res.statusCode).toBe(500);
      expect(res).toHaveProperty("text");
      expect(res.text).toMatch("Failed to get the orignal URL from db.");
    });
  });
});
