import request from "supertest";
import app from "../src/index";
describe("router testing", () => {
  it("long url converts to short url", (done) => {
    let response = request(app).get(
      `/long2short?url=${encodeURIComponent("https://www.baidu.com/")}`
    );
    response
      .expect("Content-Type", "application/json")
      .expect(200)
      .end((err, res) => {
        if (err) throw err;
        console.log(res.text);
        done();
      });
  });
  it("short url converts to long url", (done) => {
    request(app)
      .get(`short2long?url=${encodeURIComponent("https://s.cn/2")}`)
      .expect("Content-Type", "application/json")
      .expect(200)
      .end((err, res) => {
        if (err) throw err;
        console.log(res);
        done();
      });
  });
});
