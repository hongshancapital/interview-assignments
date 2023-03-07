import request from "supertest";
import app from "../src/App";

before((done) => {
  app.on("initialized", () => {
    done();
  });
});
describe("router testing", () => {
  it("long url converts to short url: add", async () => {
    let server = request(app);
    let response = server.get(
      `/long2short?url=${encodeURIComponent("https://www.google.com/")}`
    );
    return response
      .expect("Content-Type", "application/json; charset=utf-8")
      .expect(200);
  });
  it("long url converts to short url: search", async () => {
    let server = request(app);
    let response = server.get(
      `/long2short?url=${encodeURIComponent("https://www.google.com/")}`
    );
    return response
      .expect("Content-Type", "application/json; charset=utf-8")
      .expect(200);
  });
  it("short url converts to long url: found", async () => {
    let server = request(app);
    let response = server.get(
      `/short2long?url=${encodeURIComponent("https://s.cn/2")}`
    );

    return response
      .expect("Content-Type", "application/json; charset=utf-8")
      .expect(200);
  });
  it("short url converts to long url: not found", async () => {
    let server = request(app);
    let response = server.get(
      `/short2long?url=${encodeURIComponent("https://s.cn/4")}`
    );

    return response
      .expect("Content-Type", "application/json; charset=utf-8")
      .expect(200);
  });
});
