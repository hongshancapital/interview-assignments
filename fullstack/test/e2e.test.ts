import supertest from "supertest";

import { app } from "../src/app";

beforeAll(() => {});

afterAll(async () => {});

describe("POST /api/shortlink", () => {
  it("bad URI no url para", async () => {
    return await supertest(app)
      .post("/api/shortlink")
      .send({
        origUrl: "",
      })
      .expect(400)
      .then((res) => {
        expect(res.body).toMatchObject({ msg: "Invalid URL" });
      });
  });

  it("should return shortLink OK", async () => {
    return await supertest(app)
      .post("/api/shortlink")
      .send({
        origUrl: "https://github.com/",
      })
      .expect(200)
      .then((res) => {
        expect(res.body).toMatchObject({
          urlId: expect.any(String),
        });
      });
  });
});

describe("GET /:urlId", () => {

  it("short url not exist return 400", async () => {
    return await supertest(app)
      .get("/r66c687f")
      .expect("Content-Type", /json/)
      .expect(404)
      .then((res) => {
        expect(res.body).toMatchObject({
          msg: "Not found",
        });
      });
  });
});
