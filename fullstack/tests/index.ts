import request from "supertest";
import app from "../src";
let conn!: any;
describe("router testing", () => {
  it("long url converts to short url", async () => {
    let server = request(app);
    let response = server.get(
      `/long2short?url=${encodeURIComponent("https://www.baidu.com/")}`,
      () => {
        console.log("in callback");
      }
    );
    console.log(response);
    return response
      .expect("Content-Type", "application/json; charset=utf-8")
      .expect(200);
  });
  it("short url converts to long url", async () => {
    let server = request("http://localhost:3500");
    let response = server.get(
      `/short2long?url=${encodeURIComponent("https://s.cn/2")}`
    );

    return response
      .expect("Content-Type", "application/json; charset=utf-8")
      .expect(200);
  });
});
